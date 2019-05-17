package net.dzikoysk.funnytelemetry.funnybin.api;

import javax.servlet.http.HttpServletRequest;

import net.dzikoysk.funnytelemetry.funnybin.FunnyBinService;
import net.dzikoysk.funnytelemetry.funnybin.Paste;
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
    private final FunnyBinService funnyBinService;

    @Value("${funnytelemetry.main}")
    private String mainUrl;

    @Autowired
    public FunnyBinApiController(FunnyBinService funnyBinService)
    {
        this.funnyBinService = funnyBinService;
    }

    @PostMapping(value = "/post", consumes = "application/yaml", produces = "application/json")
    public ApiResponse post(@RequestBody final String body, final HttpServletRequest request)
    {
        final Paste paste = this.funnyBinService.submitPaste(body, request.getRemoteAddr());

        return new ApiResponse(this.mainUrl + "/funnybin/paste/" + paste.getUniqueId());
    }
}
