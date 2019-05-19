package net.dzikoysk.funnytelemetry.funnybin;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PasteService
{
    Collection<Paste> getAllPastes();

    Page<Paste> getPastes(Pageable pageable);

    Page<Paste> getPastesHidden(Pageable pageable);

    Paste submitPaste(String content, String submitterIp);

    Optional<Paste> findPaste(UUID uuid);

    void deletePaste(Paste paste);

    void hide(Paste paste);
}
