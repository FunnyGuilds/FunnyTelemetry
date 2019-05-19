package net.dzikoysk.funnytelemetry.panel.bans;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/panel/bans")
public class BansController
{
    @RequestMapping("/")
    public String index()
    {
        return "panel/not-implemented";
    }

    @ModelAttribute("tab")
    public String attributeTab()
    {
        return "bans";
    }
}
