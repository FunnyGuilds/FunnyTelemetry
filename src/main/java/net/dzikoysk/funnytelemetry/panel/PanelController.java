package net.dzikoysk.funnytelemetry.panel;

import net.dzikoysk.funnytelemetry.panel.stats.CachedStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/panel/")
public class PanelController
{
    private final CachedStatsService cachedStatsService;

    @Autowired
    public PanelController(final CachedStatsService cachedStatsService)
    {
        this.cachedStatsService = cachedStatsService;
    }

    @RequestMapping("/")
    public String index(final Model model)
    {
        model.addAttribute("stats", this.cachedStatsService.getStats());

        return "panel/index";
    }

    @RequestMapping("/no-access")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String noAccess()
    {
        return "panel/no-access";
    }

    @ModelAttribute("tab")
    public String attributeTab()
    {
        return "dashboard";
    }
}
