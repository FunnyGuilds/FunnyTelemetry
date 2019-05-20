package net.dzikoysk.funnytelemetry.panel.logs;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/panel/logs")
public class LogsController
{
    private final LogService logService;

    public LogsController(final LogService logService)
    {
        this.logService = logService;
    }

    @RequestMapping("/")
    public String index(
        final Model model,
        @PageableDefault final Pageable pageable,
        @RequestParam(value = "filter", defaultValue = "") final String filter
    )
    {
        this.getFiltered(model, pageable, filter, null);
        return "panel/logs/index";
    }

    @RequestMapping("/mine")
    public String mine(
        final Model model,
        @PageableDefault final Pageable pageable,
        @RequestParam(value = "filter", defaultValue = "") final String filter,
        final Principal principal
    )
    {
        this.getFiltered(model, pageable, filter, principal.getName());
        return "panel/logs/index";
    }

    @ModelAttribute("tab")
    public String attributeTab()
    {
        return "logs";
    }

    private void getFiltered(final Model model, final Pageable pageable, String filter, final String lockUser)
    {
        if (StringUtils.isEmpty(filter))
        {
            filter = "none";
        }

        final List<String> parts = Arrays.asList(filter.split(","));
        final Optional<String> ip = parts.stream().filter(element -> element.startsWith("ip:")).findFirst().map(element -> element.substring(3));
        final Optional<String> user = parts.stream().filter(element -> element.startsWith("user:")).findFirst().map(element -> element.substring(5));

        model.addAttribute("filter", filter);
        model.addAttribute("lockUser", lockUser != null);
        model.addAttribute("filterIp", ip.orElse(null));
        model.addAttribute("filterUser", user.orElse(null));
        model.addAttribute("logs", this.logService.getLogs(pageable, ip.orElse(null), lockUser != null ? lockUser : user.orElse(null)));
        model.addAttribute("logService", this.logService);
    }
}
