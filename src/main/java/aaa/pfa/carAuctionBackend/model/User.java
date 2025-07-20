package aaa.pfa.carAuctionBackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    public Long id;

    @Column(nullable = false,
            unique = true)
    public String username;

    @Column(nullable = false)
    public String name, lastName;

    @Column(nullable = false)
    public String password;

    @Column(nullable = false)
    public String role;

    public User(){};

    public User(String username, String password, String role, String name, String lastName){
        super();
        this.password = password;
        this.username = username;
        this.role = role;
        this.name = name;
        this.lastName =lastName;
    }

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    public List<Car> cars;


}
