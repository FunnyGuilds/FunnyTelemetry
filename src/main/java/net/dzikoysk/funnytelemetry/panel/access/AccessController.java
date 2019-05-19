package net.dzikoysk.funnytelemetry.panel.access;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/panel/access")
public class AccessController
{
    @RequestMapping("/")
    public String index()
    {
        return "panel/not-implemented";
    }

    @ModelAttribute("tab")
    public String attributeTab()
    {
        return "access";
    }
}
