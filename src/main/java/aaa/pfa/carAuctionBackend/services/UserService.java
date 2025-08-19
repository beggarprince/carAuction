package aaa.pfa.carAuctionBackend.services;


import aaa.pfa.carAuctionBackend.DTO.PictureDTO;
import aaa.pfa.carAuctionBackend.DTO.ProfilePictureDTO;
import aaa.pfa.carAuctionBackend.DTO.UserDTO;
import aaa.pfa.carAuctionBackend.DTO.UserRegisterDTO;
import aaa.pfa.carAuctionBackend.model.User;
import aaa.pfa.carAuctionBackend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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

        //Check for pic url

        return new UserDTO(user.username,
                user.name,
                user.lastName,
                user.id,
                user.carIds(),
                ""//Users can upload pfp later, not on sign up form
                );
    }

    private void checkForUserPicURL(){

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

    public Boolean uploadUserProfilePic(@Valid ProfilePictureDTO dto) {
        var s = String.format("Updating userid: %d with the profilepicurl:%s", dto.id(),dto.url());
        System.out.println(s);
        //Get user
        User u = userRepository.findById(dto.id()).orElse(null);

        if(u != null){
            var pic = dto.url();
            u.setPictureUrl(pic);
            userRepository.save(u);
            System.out.println("Set user pfp");

            return true;
        }
        System.out.println("User was null");


        return false;
    }
}
