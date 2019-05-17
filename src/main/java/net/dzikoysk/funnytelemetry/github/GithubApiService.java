package net.dzikoysk.funnytelemetry.github;

import java.util.List;

public interface GithubApiService
{
    List<String> getUsersOrganizations(String username);
}
