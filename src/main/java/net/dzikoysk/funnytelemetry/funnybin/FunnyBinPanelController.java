package net.dzikoysk.funnytelemetry.funnybin;

import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import net.dzikoysk.funnytelemetry.funnybin.exception.PasteNotFoundException;
import net.dzikoysk.funnytelemetry.funnybin.shortlink.FunnyBinShortLinkService;
import net.dzikoysk.funnytelemetry.shortlink.ShortLinkService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/panel/funnybin/")
public class FunnyBinPanelController
{
    private final PasteService             pasteService;
    private final FunnyBinShortLinkService shortLinkService;

    public FunnyBinPanelController(final PasteService pasteService, final FunnyBinShortLinkService shortLinkService)
    {
        this.pasteService = pasteService;
        this.shortLinkService = shortLinkService;
    }

    @RequestMapping("/pastes")
    public String pastes(final Model model, @PageableDefault final Pageable pageable)
    {
        return this.pastes(model, false, pageable);
    }

    @RequestMapping("/pastes/hidden")
    public String pastesHidden(final Model model, @PageableDefault final Pageable pageable)
    {
        return this.pastes(model, true, pageable);
    }

    public String pastes(final Model model, final boolean hidden, final Pageable pageable)
    {
        final Page<Paste> page = hidden ? this.pasteService.getPastesHidden(pageable) : this.pasteService.getPastes(pageable);
        model.addAttribute("page", page);
        model.addAttribute("hidden", hidden);
        return "panel/funnybin/paste-list";
    }

    @RequestMapping("/paste/{pasteId}")
    public String paste(final Model model, @PathVariable("pasteId") final String pasteId, final HttpServletRequest request)
    {
        final Optional<Paste> paste = this.pasteService.findPaste(UUID.fromString(pasteId));

        if (paste.isPresent() && (request.isUserInRole("ADMIN") || ! paste.get().isHide()))
        {
            model.addAttribute("paste", paste.get());
        }
        else
        {
            model.addAttribute("error", true);
        }

        return "panel/funnybin/paste";
    }

    @RequestMapping("/paste/{pasteId}/hide")
    public String pasteHide(final Model model, @PathVariable("pasteId") final String pasteId, final HttpServletRequest request)
    {
        final Optional<Paste> paste = this.pasteService.findPaste(UUID.fromString(pasteId));

        if (paste.isPresent())
        {
            this.pasteService.hide(paste.get());

            model.addAttribute("paste", paste.get());
            model.addAttribute("justHidden", true);
        }
        else
        {
            model.addAttribute("error", true);
        }

        return "panel/funnybin/paste";
    }

    @RequestMapping(value = "/paste/{pasteId}/raw", produces = "text/plain")
    @ResponseBody
    public String pasteRaw(@PathVariable("pasteId") final String pasteId, final HttpServletRequest request)
    {
        final Optional<Paste> paste = this.pasteService.findPaste(UUID.fromString(pasteId));

        if (paste.isEmpty() || (! request.isUserInRole("ADMIN") && paste.get().isHide()))
        {
            throw new PasteNotFoundException();
        }

        return paste.get().getContent();
    }

    @ModelAttribute("tab")
    public String attributeTab()
    {
        return "funnybin";
    }

    @ModelAttribute("shortLinkService")
    public ShortLinkService attributeShortLinkService()
    {
        return this.shortLinkService;
    }
}
