package net.dzikoysk.funnytelemetry;

import java.security.Principal;
import java.util.List;

import net.dzikoysk.funnytelemetry.github.GithubApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController
{
    private final GithubApiService githubApiService;

    @Autowired
    public TestController(final GithubApiService githubApiService)
    {
        this.githubApiService = githubApiService;
    }

    @RequestMapping("/")
    public String index()
    {
        return "no siema";
    }

    @RequestMapping("/user")
    public Principal user(final Principal principal)
    {
        return principal;
    }

    @RequestMapping("/repos")
    public List<String> repos(final Principal principal)
    {
        return this.githubApiService.getUsersOrganizations(principal.getName());
    }
}
