package John.s.Cooking.Space.demo.controler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username;
        if(authentication==null){
            username="anonymousUser";
        }
        else if(authentication instanceof OAuth2AuthenticationToken){
            OAuth2AuthenticationToken oAuth2AuthenticationToken=(OAuth2AuthenticationToken)authentication;
            OAuth2User oAuth2User=oAuth2AuthenticationToken.getPrincipal();
            username=oAuth2User.getAttribute("name");
                if(username==null){
                    username=oAuth2User.getAttribute("login");
                }
        }
        else{
            username=authentication.getName();

        }
        model.addAttribute("username", username);
        return "home";
    }
}
