package net.dzikoysk.funnytelemetry.funnybin;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface FunnyBinService
{
    Collection<Paste> getAllPastes();

    Paste submitPaste(String content, String submitterIp);

    Optional<Paste> findPaste(UUID uuid);

    void deletePaste(Paste paste);
}
