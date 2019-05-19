package net.dzikoysk.funnytelemetry.panel.setup;

import java.security.Principal;

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

    @Autowired
    public SetupController(final SetupService setupService)
    {
        this.setupService = setupService;
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
    public String setup(@RequestParam("setup_password") final String password, final Principal principal, final RedirectAttributes redirectAttributes)
    {
        if (! this.setupService.validatePassword(password))
        {
            redirectAttributes.addFlashAttribute("error", "Invalid setup password");
            return "redirect:/setup";
        }

        this.setupService.setupUser(principal);
        return "redirect:/";
    }
}
