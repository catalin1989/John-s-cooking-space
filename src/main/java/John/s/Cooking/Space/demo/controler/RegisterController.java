package John.s.Cooking.Space.demo.controler;

import John.s.Cooking.Space.demo.entity.User;
import John.s.Cooking.Space.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {

        model.addAttribute("user", new User());
        return "security/register";
    }

    @PostMapping("/register/submit")
    public String createUser(@ModelAttribute("user") User user, BindingResult bindingResult, Model model){

        int numberOfUsers= userService.findByUsername(user.getUsername());
        System.out.println("in post method");
        if(numberOfUsers>0){
            bindingResult.rejectValue("username", "username.exists","Username already exists");
        }
        if (user.getUsername().length() < 4) {
            bindingResult.rejectValue("username", "error.user", "Username must be at least 4 characters");
        }
        // Validate password
        if(user.getUsername().equals(user.getPassword())){
            bindingResult.rejectValue("password", "error.user", "Passwords must be different from the username");
        }
        if (user.getPassword().length() < 5) {
            bindingResult.rejectValue("password", "error.user", "Password must be at least 5 characters");
        }

        // If there are errors, return to the register page and show them
        if (bindingResult.hasErrors()) {
            System.out.println("Made it here");
            return "security/register";  // this is your registration page
        }
    user.setRole("user");
    userService.save(user);
    return "security/login";
    }
}
