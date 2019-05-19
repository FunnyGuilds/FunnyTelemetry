package net.dzikoysk.funnytelemetry.github.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import net.dzikoysk.funnytelemetry.github.GithubApiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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
        final HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "FunnyTelemetry");

        final String uri =
            UriComponentsBuilder.fromHttpUrl("https://api.github.com/users/" + username + "/orgs")
                                .queryParam("client_id", this.clientId)
                                .queryParam("client_secret", this.clientSecret)
                                .toUriString();

        final ResponseEntity<List<Organizations>> entity;

        try
        {
            entity = this.restTemplate.exchange(
                uri,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>()
                {
                }
            );
        }
        catch (final HttpClientErrorException.Forbidden e)
        {
            throw new IllegalArgumentException("Invalid call for users organizations, rate limited");
        }

        if (! entity.getStatusCode().is2xxSuccessful())
        {
            throw new IllegalArgumentException("Invalid call for users organizations, code " + entity.getStatusCodeValue());
        }

        return Objects.requireNonNull(entity.getBody()).stream().map(Organizations::getLogin).collect(Collectors.toList());
    }
}
