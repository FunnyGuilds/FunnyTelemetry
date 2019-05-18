package net.dzikoysk.funnytelemetry;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController
{
    @RequestMapping("/")
    public String index(final Principal principal)
    {
        return "index";
    }
}
