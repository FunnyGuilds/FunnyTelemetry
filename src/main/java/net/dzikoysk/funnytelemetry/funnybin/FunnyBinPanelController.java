package net.dzikoysk.funnytelemetry.funnybin;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import java.security.Principal;
import java.util.UUID;

import net.dzikoysk.funnytelemetry.funnybin.exception.PasteNotFoundException;
import net.dzikoysk.funnytelemetry.funnybin.shortlink.FunnyBinBundleShortLinkService;
import net.dzikoysk.funnytelemetry.funnybin.shortlink.FunnyBinPasteShortLinkService;
import net.dzikoysk.funnytelemetry.logs.ActionType;
import net.dzikoysk.funnytelemetry.logs.LogService;
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
    private final PasteService                   pasteService;
    private final FunnyBinPasteShortLinkService  pasteShortLinkService;
    private final FunnyBinBundleShortLinkService bundleShortLinkService;
    private final LogService                     logService;

    public FunnyBinPanelController(final PasteService pasteService, final FunnyBinPasteShortLinkService pasteShortLinkService, final FunnyBinBundleShortLinkService bundleShortLinkService,
                                   final LogService logService)
    {
        this.pasteService = pasteService;
        this.pasteShortLinkService = pasteShortLinkService;
        this.bundleShortLinkService = bundleShortLinkService;
        this.logService = logService;
    }

    @RequestMapping("/")
    public String index()
    {
        return "panel/funnybin/index";
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
        this.pasteService.findPaste(UUID.fromString(pasteId))
                .filter(paste -> request.isUserInRole("ADMIN") || ! paste.isHide())
                .ifPresentOrElse(
                        paste -> model.addAttribute("paste", paste),
                        () -> model.addAttribute("error", true)
                );

        return "panel/funnybin/paste";
    }

    @RequestMapping("/paste/{pasteId}/hide")
    public String pasteHide(final Model model, @PathVariable("pasteId") final String pasteId, final HttpServletRequest request, final Principal principal)
    {
        this.pasteService.findPaste(UUID.fromString(pasteId))
                .ifPresentOrElse(paste -> {
                            if ( !paste.isHide())
                            {
                                this.pasteService.hide(paste);
                                this.logService.submitLog(ActionType.HID_PASTE, paste.getUniqueId().toString(), principal.getName(), request.getRemoteAddr());
                            }

                            model.addAttribute("paste", paste);
                            model.addAttribute("justHidden", true);
                        },
                        () -> model.addAttribute("error", true)
                );

        return "panel/funnybin/paste";
    }

    @RequestMapping(value = "/paste/{pasteId}/raw", produces = "text/plain")
    @ResponseBody
    public String pasteRaw(@PathVariable("pasteId") final String pasteId, final HttpServletRequest request)
    {
        return this.pasteService.findPaste(UUID.fromString(pasteId))
                .filter(paste -> request.isUserInRole("ADMIN") || ! paste.isHide())
                .orElseThrow(PasteNotFoundException::new)
                .getContent();
    }

    @RequestMapping("/bundles")
    public String bundles(final Model model, @PageableDefault final Pageable pageable)
    {
        return this.bundles(model, false, pageable);
    }

    @RequestMapping("/bundles/hidden")
    public String bundlesHidden(final Model model, @PageableDefault final Pageable pageable)
    {
        return this.bundles(model, true, pageable);
    }

    private String bundles(final Model model, final boolean hidden, final Pageable pageable)
    {
        final Page<PasteBundle> page = hidden ? this.pasteService.getBundlesHidden(pageable) : this.pasteService.getBundles(pageable);
        model.addAttribute("page", page);
        model.addAttribute("hidden", hidden);
        return "panel/funnybin/bundle-list";
    }

    @RequestMapping("/bundle/{bundleId}")
    public String bundle(final Model model, @PathVariable("bundleId") final String bundleId, final HttpServletRequest request)
    {
        this.pasteService.findBundle(UUID.fromString(bundleId))
                .filter(bundle -> request.isUserInRole("ADMIN") || ! bundle.isHide())
                .ifPresentOrElse(
                        bundle -> model.addAttribute("bundle", bundle),
                        () -> model.addAttribute("error", true)
                );

        return "panel/funnybin/bundle";
    }

    @RequestMapping("/bundle/{bundleId}/hide")
    @Transactional
    public String bundleHide(final Model model, @PathVariable("bundleId") final String bundleId, final HttpServletRequest request, final Principal principal)
    {
        this.pasteService.findBundle(UUID.fromString(bundleId))
                .ifPresentOrElse(bundle -> {
                            bundle.getPastes()
                                    .stream()
                                    .filter(paste -> ! paste.isHide())
                                    .forEach(paste -> this.logService.submitLog(
                                            ActionType.HID_PASTE,
                                            paste.getUniqueId().toString(),
                                            principal.getName(), request.getRemoteAddr()
                                    ));

                            this.pasteService.hide(bundle);

                            model.addAttribute("bundle", bundle);
                            model.addAttribute("justHidden", true);
                        },
                        () -> model.addAttribute("error", true)
                );

        return "panel/funnybin/bundle";
    }

    @ModelAttribute("tab")
    public String attributeTab()
    {
        return "funnybin";
    }

    @ModelAttribute("pasteShortLinkService")
    public ShortLinkService attributeShortLinkService()
    {
        return this.pasteShortLinkService;
    }

    @ModelAttribute("bundleShortLinkService")
    public ShortLinkService attributeBundleShortLinkService()
    {
        return this.bundleShortLinkService;
    }
}
