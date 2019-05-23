package net.dzikoysk.funnytelemetry.funnybin.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import net.dzikoysk.funnytelemetry.ban.BanService;
import net.dzikoysk.funnytelemetry.funnybin.Paste;
import net.dzikoysk.funnytelemetry.funnybin.PasteBundle;
import net.dzikoysk.funnytelemetry.funnybin.PasteService;
import net.dzikoysk.funnytelemetry.funnybin.PasteType;
import net.dzikoysk.funnytelemetry.funnybin.exception.PasteInvalidRequest;
import net.dzikoysk.funnytelemetry.funnybin.exception.PasteNotFoundException;
import net.dzikoysk.funnytelemetry.funnybin.exception.PasteTooLargeException;
import net.dzikoysk.funnytelemetry.funnybin.shortlink.FunnyBinBundleShortLinkService;
import net.dzikoysk.funnytelemetry.funnybin.shortlink.FunnyBinPasteShortLinkService;
import net.dzikoysk.funnytelemetry.ratelimit.RateLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/funnybin/api/")
public class FunnyBinApiController
{
    private final PasteService                   pasteService;
    private final FunnyBinPasteShortLinkService  funnyBinPasteShortLinkService;
    private final FunnyBinBundleShortLinkService funnyBinBundleShortLinkService;
    private final BanService                     banService;
    private final RateLimitService               rateLimitService;

    @Value("${funnytelemetry.main}")
    private String mainUrl;

    @Value("${funnytelemetry.limits.file}")
    private int fileSizeLimit;

    @Autowired
    public FunnyBinApiController(final PasteService pasteService, final FunnyBinPasteShortLinkService funnyBinPasteShortLinkService, final FunnyBinBundleShortLinkService funnyBinBundleShortLinkService,
                                 final BanService banService, final RateLimitService rateLimitService)
    {
        this.pasteService = pasteService;
        this.funnyBinPasteShortLinkService = funnyBinPasteShortLinkService;
        this.funnyBinBundleShortLinkService = funnyBinBundleShortLinkService;
        this.banService = banService;
        this.rateLimitService = rateLimitService;
    }

    @PostMapping(value = "/post", consumes = "text/plain", produces = "application/json")
    public ApiResponse post(
        @RequestBody(required = false) final String body,
        @RequestParam(value = "type", defaultValue = "OTHER") final PasteType pasteType,
        @RequestParam(value = "tag", defaultValue = "") final String tag,
        final HttpServletRequest request
    )
    {
        this.banService.ensureNotBanned(request.getRemoteAddr());

        if (body != null && this.fileSizeLimit != - 1 && body.length() > this.fileSizeLimit)
        {
            throw new PasteTooLargeException("Paste is too large. (Maximum allowed: " + this.fileSizeLimit + " bytes)");
        }

        this.rateLimitService.ensureNotRateLimited(request.getRemoteAddr());

        final Paste paste = this.pasteService.submitPaste(StringUtils.isEmpty(body) ? "" : body, request.getRemoteAddr(), pasteType, tag);

        return new ApiResponse(
            this.mainUrl + "/panel/funnybin/paste/" + paste.getUniqueId(),
            this.funnyBinPasteShortLinkService.getShortLink(paste.getShortLink()),
            paste.getUniqueId().toString()
        );
    }

    @PostMapping(value = "/bundle/post", produces = "application/json")
    public ApiResponse post(
        @RequestParam(value = "paste") final List<String> pastes,
        final HttpServletRequest request
    )
    {
        this.banService.ensureNotBanned(request.getRemoteAddr());

        if (pastes.size() < 2)
        {
            throw new PasteInvalidRequest("Bundle must contain at least 2 pastes");
        }

        final List<Paste> pasteList = new ArrayList<>(pastes.size());
        for (final String paste : pastes)
        {
            final Optional<Paste> optional = this.pasteService.findPaste(UUID.fromString(paste));
            if (optional.isEmpty() || optional.get().isHide())
            {
                throw new PasteNotFoundException("Paste with id: " + paste + " not found");
            }
            pasteList.add(optional.get());
        }

        this.rateLimitService.ensureNotRateLimited(request.getRemoteAddr());
        final PasteBundle bundle = this.pasteService.createBundle(pasteList, request.getRemoteAddr());

        return new ApiResponse(
            this.mainUrl + "/panel/funnybin/bundle/" + bundle.getUniqueId(),
            this.funnyBinBundleShortLinkService.getShortLink(bundle.getShortLink()),
            bundle.getUniqueId().toString()
        );
    }
}
