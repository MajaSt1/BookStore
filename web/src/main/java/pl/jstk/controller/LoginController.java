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

    /**
     * Method is handling HTTP Get request and returning view with login information execution
     *
     * @param error check if there is any error occured
     * @param logout check if user is logout
     * @return Form login execution.
     */
    @GetMapping(value = "/logon")
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

    /**
     * Method is handling HTTP Get request and returning view with handling error 403.
     *
     * @param user User that is currently log on
     * @return Handle error 403.
     */
    @GetMapping(value = "/error403")
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
