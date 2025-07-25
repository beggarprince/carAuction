package aaa.pfa.carAuctionBackend.repository;


import aaa.pfa.carAuctionBackend.model.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface UserRepository
        extends CrudRepository<User, Long> {

    @Override
    Optional<User> findById(Long id);

    @Query
    Optional<User> findByUsername(String username);


    boolean existsByUsername(@NotBlank String username);
}
