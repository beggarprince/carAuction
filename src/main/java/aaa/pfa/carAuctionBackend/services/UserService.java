package aaa.pfa.carAuctionBackend.services;


import aaa.pfa.carAuctionBackend.model.User;
import aaa.pfa.carAuctionBackend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    //Update user
    //fetch user, change variables in transaction
    @Autowired
    private UserRepository userRepository;

    public UserService(){};

    public UserService(UserRepository userRepository)
    {
        super();
        this.userRepository = userRepository;
    }

    public User updateUser(Long id, UserDTO dto){


        User user = userRepository.findById(id)
                .orElseThrow(() ->
        new EntityNotFoundException("User not found"));

        //this is what we are changing
        if (dto.username != null) {
            user.username = dto.username;
        }
        if (dto.name != null) {
            user.name = dto.name;
        }
        if (dto.lastName != null) {
            user.lastName = dto.lastName;
        }

        return userRepository.save(user);
    }

}
