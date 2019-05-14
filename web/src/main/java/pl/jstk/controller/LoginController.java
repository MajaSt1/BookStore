package pl.jstk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import pl.jstk.constants.ModelConstants;
import pl.jstk.constants.ViewNames;

import java.security.Principal;

@Controller
public class LoginController  {

    @GetMapping(value = "/login")
    public ModelAndView loginPage(@ModelAttribute(value = "error") String error,
                                  @ModelAttribute(value = "param.logout") String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid Credentials provided.");
        }
        if (logout != null) {
            model.addObject("message", "Logged out successfully.");
        }
        model.setViewName("login");
        return model;
    }

    @GetMapping(value = "/403")
    public ModelAndView accesssDenied(Principal user) {

        ModelAndView model = new ModelAndView();

        if (user != null) {
            model.addObject(ModelConstants.MESSAGE, "Hi " + user.getName()
                    + ", you do not have permission to access this page!");
        } else {
            model.addObject(ModelConstants.MESSAGE,
                    "You do not have permission to access this page!");
        }

        model.setViewName(ViewNames.ERROR403);
        return model;
    }
}
