package net.dzikoysk.funnytelemetry.funnybin.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import net.dzikoysk.funnytelemetry.funnybin.Paste;
import net.dzikoysk.funnytelemetry.funnybin.PasteBundle;
import net.dzikoysk.funnytelemetry.funnybin.PasteService;
import net.dzikoysk.funnytelemetry.funnybin.PasteType;
import net.dzikoysk.funnytelemetry.funnybin.shortlink.FunnyBinBundleShortLinkService;
import net.dzikoysk.funnytelemetry.funnybin.shortlink.FunnyBinPasteShortLinkService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PasteServiceImpl implements PasteService
{
    private final PasteRepository                pasteRepository;
    private final PasteBundleRepository          bundleRepository;
    private final FunnyBinPasteShortLinkService  pasteShortLinkService;
    private final FunnyBinBundleShortLinkService bundleShortLinkService;

    public PasteServiceImpl(final PasteRepository pasteRepository, final PasteBundleRepository bundleRepository, final FunnyBinPasteShortLinkService pasteShortLinkService,
                            final FunnyBinBundleShortLinkService bundleShortLinkService)
    {
        this.pasteRepository = pasteRepository;
        this.bundleRepository = bundleRepository;
        this.pasteShortLinkService = pasteShortLinkService;
        this.bundleShortLinkService = bundleShortLinkService;
    }

    @Override
    public Collection<Paste> getAllPastes()
    {
        return this.pasteRepository.findAll();
    }

    @Override
    public Page<Paste> getPastes(final Pageable pageable)
    {
        return this.pasteRepository.findAllByHideOrderBySubmitDateDescIdDesc(pageable, false);
    }

    @Override
    public Page<Paste> getPastesHidden(final Pageable pageable)
    {
        return this.pasteRepository.findAllByOrderBySubmitDateDescIdDesc(pageable);
    }

    @Override
    public Paste submitPaste(final String content, final String submitterIp, final PasteType pasteType, final String tag)
    {
        return this.pasteRepository.save(new Paste(UUID.randomUUID(), content, submitterIp, new Date(), this.pasteShortLinkService.generateShortCode(), false, pasteType, tag, Collections.emptyList()));
    }

    @Override
    public Optional<Paste> findPaste(final UUID uuid)
    {
        return this.pasteRepository.findByUniqueId(uuid);
    }

    @Override
    public void hide(final Paste paste)
    {
        paste.setHide(true);
        this.pasteRepository.save(paste);
    }

    @Override
    public PasteBundle createBundle(final List<Paste> pastes, final String submitter)
    {
        return this.bundleRepository.save(new PasteBundle(UUID.randomUUID(), submitter, new Date(), this.bundleShortLinkService.generateShortCode(), pastes, false));
    }

    @Override
    public Page<PasteBundle> getBundles(final Pageable pageable)
    {
        return this.bundleRepository.findAllByHideOrderBySubmitDateDescIdDesc(pageable, false);
    }

    @Override
    public Page<PasteBundle> getBundlesHidden(final Pageable pageable)
    {
        return this.bundleRepository.findAllByOrderBySubmitDateDescIdDesc(pageable);
    }

    @Override
    public void hide(final PasteBundle bundle)
    {
        bundle.getPastes().forEach(paste -> paste.setHide(true));
        bundle.setHide(true);
        this.bundleRepository.save(bundle);
    }

    @Override
    public Optional<PasteBundle> findBundle(final UUID uuid)
    {
        return this.bundleRepository.findByUniqueId(uuid);
    }

    @Override
    public List<Paste> getPastesAfter(final Date date, final String ip)
    {
        return this.pasteRepository.findAllBySubmitterAndSubmitDateAfter(ip, date);
    }

    @Override
    public List<PasteBundle> getBundlesAfter(final Date date, final String ip)
    {
        return this.bundleRepository.findAllBySubmitterAndSubmitDateAfter(ip, date);
    }
}
