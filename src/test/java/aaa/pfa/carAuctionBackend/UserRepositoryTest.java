package aaa.pfa.carAuctionBackend;

import aaa.pfa.carAuctionBackend.model.User;
import aaa.pfa.carAuctionBackend.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    //l1 tacked so i know nothing is default or whatever
    User globalUserl1 = new User(
            "usernameL1",
            "passwordL1",
            "ROLEL1",
            "First nameL1",
            "Last NameL1"
    );

    @Test
    @DisplayName("Saving user to repository")
    void saveUser() {

        User userl1 = new User("leifdawg", "password", "ADMIN", "po", "vega");

        repository.save(userl1);
        assertThat(
                //  true
                repository.findByUsername("leifdawg").isPresent()
        ).isTrue();
    }

    @Test
    @DisplayName("Deleting user from repository")
    void deleteAllUsers() {
        User userl2 = new User("leifdawg", "password", "ADMIN", "po", "vega");

        repository.save(userl2);
        repository.deleteAll();
        assertThat(repository.count()).isEqualTo(0);

    }

    @Test
    @DisplayName("Delete specific user")
    void deleteSpecificUserTest() {
        User userToDelete = new User(
                "delete",
                "delete",
                "garbage",
                "delete",
                "delete"
        );
        User userToKeep = new User(
                "username", "password", "role", "name", "last name"
        );

        repository.save(userToDelete);
        repository.save(userToKeep);
        repository.deleteById(userToDelete.id);

        Optional<User> deletedUser = repository.findById(userToDelete.id);
        assertThat(deletedUser).isNotPresent();
    }

    @Test
    @DisplayName("Retrieve specific user")
    void returnUserTest() {
        User userl3 = new User("driver", "driver-password", "ADMIN", "me", "fr");
        repository.save(userl3);
        User l4 = repository.findByUsername("driver").orElseThrow();
        //assertThat(l4.equals(userl3));
        assertEquals(l4, userl3);
    }

    @Test
    @DisplayName("Find user by id")
    void findByIdTest() {
        repository.save(globalUserl1);
        Long id = globalUserl1.id;
        User userFoundById = repository.findById(id).orElseThrow();
        assertThat(userFoundById.equals(globalUserl1));
    }
}
