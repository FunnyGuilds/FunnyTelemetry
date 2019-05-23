package net.dzikoysk.funnytelemetry.funnybin.shortlink.impl;

import java.util.Optional;

import net.dzikoysk.funnytelemetry.funnybin.impl.PasteBundleRepository;
import net.dzikoysk.funnytelemetry.funnybin.shortlink.FunnyBinBundleShortLinkService;
import net.dzikoysk.funnytelemetry.shortlink.impl.ShortLinkServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FunnyBinBundleShortLinkServiceImpl extends ShortLinkServiceImpl implements FunnyBinBundleShortLinkService
{
    @Value("${funnytelemetry.short-link.bundle}")
    private String path;

    @Value("${funnytelemetry.main}")
    private String mainUrl;

    private final PasteBundleRepository funnybinRepository;

    @Autowired
    public FunnyBinBundleShortLinkServiceImpl(final PasteBundleRepository funnybinRepository)
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
            .map(bundle -> this.mainUrl + "/panel/funnybin/bundle/" + bundle.getUniqueId());
    }
}
