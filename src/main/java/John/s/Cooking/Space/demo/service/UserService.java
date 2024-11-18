package John.s.Cooking.Space.demo.service;

import John.s.Cooking.Space.demo.entity.User;
import John.s.Cooking.Space.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(User user) {
        Optional<User> user1=userRepository.findByUsername(user.getUsername());
        if(user1.isPresent()) {
            System.out.println("error found");
            throw new UsernameNotFoundException("User with username " + user.getUsername() + " already exists");
        }
        System.out.println("here");
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public Long getCurrentUserId() {
        // Get the authentication object from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            // Fetch the user from the repository
            User user = userRepository.findByUsername(username).orElseThrow(() ->
                    new UsernameNotFoundException("User not found with username: " + username));

            // Return the user's ID
            return user.getId();
        }

        // Handle the case where the user is not authenticated
        throw new SecurityException("No authenticated user found.");
    }

    public int findByUsername(String username) {
        Optional<User> userList=userRepository.findByUsername(username);
        if(userList.isPresent()) {
            return 1;
        }
        return 0;
    }
}
