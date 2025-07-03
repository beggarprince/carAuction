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
        Optional<User> user = userRepository.findByUsername(username);

        org.springframework.security.core.userdetails.User.UserBuilder builder = null;

        if(user.isPresent()){
            User currentUser = user.get();
            builder = org.springframework.security.core.userdetails.
                    User.withUsername(username);
                builder.password(currentUser.password);
                builder.roles("ROLE_ " + currentUser.role);
        }
        else{
            throw new UsernameNotFoundException("User not found");
        }
        return builder.build();
    }
}
