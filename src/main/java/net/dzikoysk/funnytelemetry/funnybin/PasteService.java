package net.dzikoysk.funnytelemetry.funnybin;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PasteService
{
    Collection<Paste> getAllPastes();

    Page<Paste> getPastes(Pageable pageable);

    Page<Paste> getPastesHidden(Pageable pageable);

    Paste submitPaste(String content, String submitterIp, PasteType pasteType, String tag);

    Optional<Paste> findPaste(UUID uuid);

    void hide(Paste paste);

    PasteBundle createBundle(List<Paste> pastes, String submitterIp);

    Page<PasteBundle> getBundles(Pageable pageable);

    Page<PasteBundle> getBundlesHidden(Pageable pageable);

    void hide(PasteBundle bundle);

    Optional<PasteBundle> findBundle(UUID uuid);

    List<Paste> getPastesAfter(Date date, String ip);

    List<PasteBundle> getBundlesAfter(Date date, String ip);
}
