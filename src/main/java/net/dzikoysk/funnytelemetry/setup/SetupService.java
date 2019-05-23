package net.dzikoysk.funnytelemetry.setup;

import java.security.Principal;

public interface SetupService
{
    boolean isSetupAvailable();

    boolean validatePassword(String password);

    void setupUser(Principal user);
}
