package aaa.pfa.carAuctionBackend.services;

import aaa.pfa.carAuctionBackend.model.User;
import aaa.pfa.carAuctionBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceService implements
        UserDetailsService {


    private final UserRepository userRepository;

    public UserDetailsServiceService(UserRepository up){
        this.userRepository = up;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Attempting to find user by username: \"" + username + "\"" );

        Optional<User> user = userRepository.findByUsername(username);
        org.springframework.security.core.userdetails.User.UserBuilder builder = null;

        if(user.isPresent()){

            System.out.println("Username found");

            User currentUser = user.get();
            builder = org.springframework.security.core.userdetails.
                    User.withUsername(username);
                builder.password(currentUser.password);
                builder.roles(currentUser.role);
        }
        else{
            System.out.println("Username not found");
            throw new UsernameNotFoundException("User not found");
        }
        return builder.build();
    }

    public User returnByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User '%s' not found".formatted(username)));
    }
}
