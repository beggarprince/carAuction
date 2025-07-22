package aaa.pfa.carAuctionBackend.DTO;

import aaa.pfa.carAuctionBackend.model.Car;

import java.util.List;

public class UserDTO {
    private final String username;
    private final String name;
    private final String lastName;
    private final Long id;
    private final List<Long> cars;

    public UserDTO(String username,
                   String name,
                   String lastName,
                   Long id,
                   List<Long> cars) {
        this.username = username;
        this.name     = name;
        this.lastName = lastName;
        this.id       = id;
        this.cars = cars;
    }

    public String getUsername() { return username; }
    public String getName()     { return name;     }
    public String getLastName() { return lastName; }
    public Long getId()       { return id;     }


}