package net.dzikoysk.funnytelemetry.panel;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/panel/")
public class PanelController
{
    @RequestMapping("/")
    public String index()
    {
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
