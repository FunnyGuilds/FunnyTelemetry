package net.dzikoysk.funnytelemetry.panel.bans;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import net.dzikoysk.funnytelemetry.ban.Ban;
import net.dzikoysk.funnytelemetry.ban.BanService;
import net.dzikoysk.funnytelemetry.ban.IpAddressIsInvalid;
import net.dzikoysk.funnytelemetry.logs.ActionType;
import net.dzikoysk.funnytelemetry.logs.LogService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/panel/bans")
public class BansController
{
    private final BanService banService;
    private final LogService logService;

    public BansController(final BanService banService, final LogService logService)
    {
        this.banService = banService;
        this.logService = logService;
    }

    @RequestMapping("/")
    public String index(final Model model, @PageableDefault final Pageable pageable)
    {
        model.addAttribute("bans", this.banService.getAllActiveBans(pageable));
        model.addAttribute("showRevoked", false);
        return "panel/bans/ban-list";
    }

    @RequestMapping("/all")
    public String all(final Model model, @PageableDefault final Pageable pageable)
    {
        model.addAttribute("bans", this.banService.getAllBans(pageable));
        model.addAttribute("showRevoked", true);
        return "panel/bans/ban-list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(@ModelAttribute("banForm") final BanForm banForm)
    {
        return "panel/bans/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addPost(final HttpServletRequest request, @ModelAttribute("banForm") final BanForm banForm, final RedirectAttributes redirectAttributes, final Principal principal)
    {
        final Ban ban;

        try
        {
            ban = this.banService.ban(banForm.getAddress(), banForm.getReason(), principal.getName());
        }
        catch (final IpAddressIsInvalid e)
        {
            redirectAttributes.addFlashAttribute("banForm", banForm);
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/panel/bans/add";
        }

        this.logService.submitLog(ActionType.BANNED_IP, ban.getUniqueId().toString(), principal.getName(), request.getRemoteAddr());

        redirectAttributes.addFlashAttribute("justAdded", true);
        return "redirect:/panel/bans/" + ban.getUniqueId().toString();
    }

    @RequestMapping("/{banId}")
    public String paste(final Model model, @PathVariable("banId") final String banId, @ModelAttribute("revokeForm") final RevokeForm revokeForm)
    {
        final Optional<Ban> ban = this.banService.findBan(UUID.fromString(banId));

        if (ban.isPresent())
        {
            model.addAttribute("ban", ban.get());
        }
        else
        {
            model.addAttribute("error", true);
        }

        return "panel/bans/ban";
    }

    @RequestMapping(value = "/{banId}/revoke", method = RequestMethod.POST)
    public String revoke(@PathVariable("banId") final String banId, @ModelAttribute("revokeForm") final RevokeForm revokeForm, final Principal principal, final HttpServletRequest request)
    {
        final Optional<Ban> ban = this.banService.findBan(UUID.fromString(banId));

        if (ban.isEmpty() || ban.get().isRevoked())
        {
            return "redirect:/panel/bans/" + banId;
        }

        this.banService.revokeBan(ban.get(), revokeForm.getReason(), principal.getName());
        this.logService.submitLog(ActionType.REVOKED_BAN, ban.get().getUniqueId().toString(), principal.getName(), request.getRemoteAddr());

        return "redirect:/panel/bans/" + banId;
    }

    @ModelAttribute("tab")
    public String attributeTab()
    {
        return "bans";
    }
}
