package net.dzikoysk.funnytelemetry;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController
{
    @RequestMapping("/")
    public String index()
    {
        return "no siema";
    }
}
