package net.dzikoysk.funnytelemetry.panel;

import java.security.Principal;

import net.dzikoysk.funnytelemetry.github.GithubApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/panel/")
public class PanelController
{
    private final GithubApiService githubApiService;

    @Autowired
    public PanelController(final GithubApiService githubApiService)
    {
        this.githubApiService = githubApiService;
    }

    @RequestMapping("/")
    public String index()
    {
        return "panel/index";
    }

    @RequestMapping("/logged")
    public String index(final Model model, final Principal principal)
    {
        model.addAttribute("organizations", this.githubApiService.getUsersOrganizations(principal.getName()));
        return "panel/logged-in";
    }

    @RequestMapping("/no-access")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String noAccess()
    {
        return "panel/no-access";
    }
}
