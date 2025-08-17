package aaa.pfa.carAuctionBackend.DTO;

import aaa.pfa.carAuctionBackend.model.Car;

import java.util.List;

public class UserDTO {
    private final String username;
    private final String name;
    private final String lastName;
    private final Long id;
    private final List<Long> cars;
    private String picUrl = null;


    //With pic
    public UserDTO(String username,
                   String name,
                   String lastName,
                   Long id,
                   List<Long> cars,
                   String picUrl) {
        this.username = username;
        this.name     = name;
        this.lastName = lastName;
        this.id       = id;
        this.cars = cars;
        this.picUrl = picUrl;
    }

    public String getUsername() {
        return username; }
    public String getName()     {
        return name;     }
    public String getLastName() {
        return lastName; }
    public Long getId()       {
        return id;     }
    public String getPicUrl(){
        return picUrl;
    }

}