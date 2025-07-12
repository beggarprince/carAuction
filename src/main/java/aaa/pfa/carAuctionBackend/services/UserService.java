package aaa.pfa.carAuctionBackend.services;


import aaa.pfa.carAuctionBackend.model.User;
import aaa.pfa.carAuctionBackend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    //Update user
    //fetch user, change variables in transaction
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(){};

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder)
    {
        super();
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User updateUser(Long id, UserDTO dto){

        User user = userRepository.findById(id)
                .orElseThrow(() ->
        new EntityNotFoundException("User not found"));

        //this is what we are changing
        if (dto.getUsername() != null) {
            user.username = dto.getUsername();
        }
        if (dto.getName() != null) {
            user.name = dto.getName();
        }
        if (dto.getLastName() != null) {
            user.lastName = dto.getLastName();
        }

        return userRepository.save(user);
    }

    @Transactional
    public User register(UserRegisterDTO dto){
        if(userRepository.existsByUsername(dto.username())){
            throw new IllegalArgumentException("Username taken");
        }

        User user = new User(
                dto.username(),
                passwordEncoder.encode(dto.password()),
                "USER",
                dto.name(),
                dto.lastName()
                );

        return userRepository.save(user);
    }

}
