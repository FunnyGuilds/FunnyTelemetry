package net.dzikoysk.funnytelemetry.github.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import net.dzikoysk.funnytelemetry.github.GithubApiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GithubApiServiceImpl implements GithubApiService
{
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${github.client-id}")
    private String clientId;

    @Value("${github.client-secret}")
    private String clientSecret;

    @Override
    public List<String> getUsersOrganizations(final String username)
    {
        final String url = "https://api.github.com/users/" + username + "/orgs";
        final Map<String, String> parameters = Map.of("client_id", this.clientId, "client_secret", this.clientSecret);

        final ResponseEntity<List<Organizations>> entity = this.restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<>()
            {
            },
            parameters
        );

        if (! entity.getStatusCode().is2xxSuccessful())
        {
            throw new IllegalArgumentException("Invalid call for users organizations, code " + entity.getStatusCodeValue());
        }

        return Objects.requireNonNull(entity.getBody()).stream().map(Organizations::getLogin).collect(Collectors.toList());
    }
}
