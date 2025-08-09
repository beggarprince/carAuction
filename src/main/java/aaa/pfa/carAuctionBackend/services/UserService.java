package aaa.pfa.carAuctionBackend.services;


import aaa.pfa.carAuctionBackend.DTO.UserDTO;
import aaa.pfa.carAuctionBackend.DTO.UserRegisterDTO;
import aaa.pfa.carAuctionBackend.model.User;
import aaa.pfa.carAuctionBackend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    //Update user
    //fetch user, change variables in transaction
    //@Autowired
    private UserRepository userRepository;

    private UserDetailsServiceService userDetailsServiceService;

    //@Autowired
    private PasswordEncoder passwordEncoder;

    //public UserService(){};

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       UserDetailsServiceService userDetailsServiceService)
    {
        super();
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsServiceService = userDetailsServiceService;
    }

    public Optional<UserDTO> updateUser(Long id, UserDTO dto){

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

        user = userRepository.save(user);
        return Optional.ofNullable(createUserDTO(user));

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

    public Optional<UserDTO> getByUsername(String username){

        Optional<User> user = userRepository.findByUsername(username);
        return user.map(this::createUserDTO);

    }

    public UserDTO createUserDTO(User user){

        return new UserDTO(user.username,
                user.name,
                user.lastName,
                user.id,
                user.carIds()
                );
    }

    public Optional<UserDTO> currentUser(Authentication authentication){
        String username = authentication.getName();

        return getByUsername(username);
    }

    public List<UserDTO> getAllUsers(){
        List<UserDTO> dtoList = new ArrayList<>();

        Iterable<User> userList = userRepository.findAll();

        for (User user : userList) {
            dtoList.add(createUserDTO(user));
        }

        return (dtoList);
    }
}
