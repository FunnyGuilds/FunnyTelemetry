package net.dzikoysk.funnytelemetry.funnybin.impl;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import net.dzikoysk.funnytelemetry.funnybin.Paste;
import net.dzikoysk.funnytelemetry.funnybin.PasteRepository;
import net.dzikoysk.funnytelemetry.funnybin.PasteService;
import net.dzikoysk.funnytelemetry.funnybin.shortlink.FunnyBinShortLinkService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PasteServiceImpl implements PasteService
{
    private final PasteRepository          funnybinRepository;
    private final FunnyBinShortLinkService shortLinkService;

    public PasteServiceImpl(final PasteRepository funnybinRepository, final FunnyBinShortLinkService shortLinkService)
    {
        this.funnybinRepository = funnybinRepository;
        this.shortLinkService = shortLinkService;
    }

    @Override
    public Collection<Paste> getAllPastes()
    {
        return this.funnybinRepository.findAll();
    }

    @Override
    public Page<Paste> getPastes(final Pageable pageable)
    {
        return this.funnybinRepository.findAllByHideOrderBySubmitDateDescIdDesc(pageable, false);
    }

    @Override
    public Page<Paste> getPastesHidden(final Pageable pageable)
    {
        return this.funnybinRepository.findAllByOrderBySubmitDateDescIdDesc(pageable);
    }

    @Override
    public Paste submitPaste(final String content, final String submitterIp)
    {
        // TODO bans
        final Paste paste = new Paste(UUID.randomUUID(), content, submitterIp, new Date(), this.shortLinkService.generateShortCode(), false);
        this.funnybinRepository.save(paste);
        return paste;
    }

    @Override
    public Optional<Paste> findPaste(final UUID uuid)
    {
        return this.funnybinRepository.findByUniqueId(uuid);
    }

    @Override
    public void deletePaste(Paste paste)
    {
        this.funnybinRepository.delete(paste);
    }

    @Override
    public void hide(final Paste paste)
    {
        paste.setHide(true);
        this.funnybinRepository.save(paste);
    }
}
