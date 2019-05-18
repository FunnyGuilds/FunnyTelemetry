package net.dzikoysk.funnytelemetry.panel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum AccessLevel
{
    NO_ACCESS(Collections.singletonList("ROLE_NONE")),
    USER_ACCESS(Collections.singletonList("ROLE_USER")),
    ADMIN_ACCESS(Arrays.asList("ROLE_USER", "ROLE_ADMIN"));

    private final List<String> roles;

    AccessLevel(final List<String> roles)
    {
        this.roles = roles;
    }

    public List<String> getRoles()
    {
        return this.roles;
    }
}
