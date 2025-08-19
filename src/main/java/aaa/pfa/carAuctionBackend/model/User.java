package aaa.pfa.carAuctionBackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
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

    @JsonIgnore
    @Column(nullable = false)
    public String password;

    @Column(nullable = false)
    public String role;

    @Column(nullable = true)
    public String profilePicUrl;

    public User(){};

    public User(String username, String password, String role, String name, String lastName){
        super();
        this.password = password;
        this.username = username;
        this.role = role;
        this.name = name;
        this.lastName =lastName;
        this.cars = new ArrayList<Car>();
    }

    @Column
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<Car> cars;


    public List<Long> carIds() {
        List<Long> ids = new ArrayList<>();
        for(Car c : cars){
            ids.add(c.getId());
        }
        return ids;
    }

    public void addCarToUser(Car car) {
        cars.add(car);
    }

    public void setPictureUrl(String string) {
        this.profilePicUrl = string;
    }
}
