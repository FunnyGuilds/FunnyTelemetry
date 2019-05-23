package net.dzikoysk.funnytelemetry.panel;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import net.dzikoysk.funnytelemetry.logs.ActionType;
import net.dzikoysk.funnytelemetry.logs.LogService;
import net.dzikoysk.funnytelemetry.setup.SetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SetupController
{
    private final SetupService setupService;
    private final LogService   logService;

    @Autowired
    public SetupController(final SetupService setupService, final LogService logService)
    {
        this.setupService = setupService;
        this.logService = logService;
    }

    @RequestMapping(value = "/setup", method = RequestMethod.GET)
    public String setup()
    {
        if (! this.setupService.isSetupAvailable())
        {
            return "redirect:/";
        }

        return "setup";
    }

    @RequestMapping(value = "/setup", method = RequestMethod.POST)
    public String setup(@RequestParam("setup_password") final String password, final Principal principal, final RedirectAttributes redirectAttributes, final HttpServletRequest request)
    {
        if (! this.setupService.validatePassword(password))
        {
            redirectAttributes.addFlashAttribute("error", "Invalid setup password");
            return "redirect:/setup";
        }

        this.setupService.setupUser(principal);
        this.logService.submitLog(ActionType.USED_SETUP, null, principal.getName(), request.getRemoteAddr());

        return "redirect:/";
    }
}
