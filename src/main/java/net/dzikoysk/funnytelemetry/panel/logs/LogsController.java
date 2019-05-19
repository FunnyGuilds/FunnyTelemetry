package net.dzikoysk.funnytelemetry.panel.logs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/panel/logs")
public class LogsController
{
    @RequestMapping("/")
    public String index()
    {
        return "panel/not-implemented";
    }

    @RequestMapping("/mine")
    public String mine()
    {
        return "panel/not-implemented";
    }

    @ModelAttribute("tab")
    public String attributeTab()
    {
        return "logs";
    }
}
