package net.dzikoysk.funnytelemetry.funnybin.api;

import javax.servlet.http.HttpServletRequest;

import net.dzikoysk.funnytelemetry.funnybin.Paste;
import net.dzikoysk.funnytelemetry.funnybin.PasteService;
import net.dzikoysk.funnytelemetry.funnybin.shortlink.FunnyBinShortLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/funnybin/api/")
public class FunnyBinApiController
{
    private final PasteService             pasteService;
    private final FunnyBinShortLinkService funnyBinShortLinkService;

    @Value("${funnytelemetry.main}")
    private String mainUrl;

    @Autowired
    public FunnyBinApiController(PasteService pasteService, final FunnyBinShortLinkService funnyBinShortLinkService)
    {
        this.pasteService = pasteService;
        this.funnyBinShortLinkService = funnyBinShortLinkService;
    }

    @PostMapping(value = "/post", consumes = "application/yaml", produces = "application/json")
    public ApiResponse post(@RequestBody final String body, final HttpServletRequest request)
    {
        final Paste paste = this.pasteService.submitPaste(body, request.getRemoteAddr());

        return new ApiResponse(
            this.mainUrl + "/panel/funnybin/paste/" + paste.getUniqueId(),
            this.funnyBinShortLinkService.getShortLink(paste.getShortLink())
        );
    }
}
