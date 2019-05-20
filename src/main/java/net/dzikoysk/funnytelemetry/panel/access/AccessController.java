package net.dzikoysk.funnytelemetry.panel.access;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import net.dzikoysk.funnytelemetry.panel.AccessLevel;
import net.dzikoysk.funnytelemetry.panel.logs.ActionType;
import net.dzikoysk.funnytelemetry.panel.logs.LogService;
import net.dzikoysk.funnytelemetry.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/panel/access")
public class AccessController
{
    private final PanelAccessService panelAccessService;
    private final SessionService     sessionService;
    private final LogService         logService;

    @Autowired
    public AccessController(final PanelAccessService panelAccessService, final SessionService sessionService, final LogService logService)
    {
        this.panelAccessService = panelAccessService;
        this.sessionService = sessionService;
        this.logService = logService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(final Model model)
    {
        model.addAttribute("access_users", this.panelAccessService.getAccessLevelForUsers());
        model.addAttribute("access_organizations", this.panelAccessService.getAccessLevelForOrganizations());

        return "panel/access/index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String post(final @RequestParam Map<String, String> params, final RedirectAttributes redirectAttributes, final HttpServletRequest request, final Principal principal)
    {
        final StringBuilder log = new StringBuilder();
        final Map<String, AccessLevel> userAccess = new HashMap<>();
        final Map<String, AccessLevel> organizationAccess = new HashMap<>();

        for (final Map.Entry<String, String> entry : params.entrySet())
        {
            if ("_csrf".equals(entry.getKey()))
            {
                continue;
            }

            if (entry.getKey().startsWith("user_"))
            {
                userAccess.put(entry.getKey().substring(5), AccessLevel.valueOf(entry.getValue()));
            }
            if (entry.getKey().startsWith("org_"))
            {
                organizationAccess.put(entry.getKey().substring(4), AccessLevel.valueOf(entry.getValue()));
            }
            log.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
        }

        if (log.length() != 0)
        {
            log.setLength(log.length() - 1);
        }

        this.panelAccessService.reorganizeAccess(userAccess, organizationAccess);
        this.logService.submitLog(ActionType.CHANGE_ACCESS, log.toString(), principal.getName(), request.getRemoteAddr());
        this.sessionService.invalidateAllRoles();
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/panel/access/";
    }

    @ModelAttribute("tab")
    public String attributeTab()
    {
        return "access";
    }
}
