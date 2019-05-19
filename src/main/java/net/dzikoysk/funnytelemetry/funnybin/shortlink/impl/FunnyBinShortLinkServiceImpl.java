package net.dzikoysk.funnytelemetry.funnybin.shortlink.impl;

import java.util.Optional;

import net.dzikoysk.funnytelemetry.funnybin.PasteRepository;
import net.dzikoysk.funnytelemetry.funnybin.shortlink.FunnyBinShortLinkService;
import net.dzikoysk.funnytelemetry.shortlink.impl.ShortLinkServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FunnyBinShortLinkServiceImpl extends ShortLinkServiceImpl implements FunnyBinShortLinkService
{
    @Value("${funnytelemetry.short-link.paste}")
    private String path;

    @Value("${funnytelemetry.main}")
    private String mainUrl;

    private final PasteRepository funnybinRepository;

    @Autowired
    public FunnyBinShortLinkServiceImpl(final PasteRepository funnybinRepository)
    {
        this.funnybinRepository = funnybinRepository;
    }

    @Override
    public String getShortLinkPath()
    {
        return this.path;
    }

    @Override
    public boolean isCodeUsed(final String string)
    {
        return this.funnybinRepository.findByShortLink(string).isPresent();
    }

    @Override
    public int getLinkLength()
    {
        return 8;
    }

    @Override
    public Optional<String> resolveFullLink(final String code)
    {
        return this.funnybinRepository
            .findByShortLink(code)
            .map(paste -> this.mainUrl + "/panel/funnybin/paste/" + paste.getUniqueId());
    }
}
