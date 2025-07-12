package aaa.pfa.carAuctionBackend.services;

public class UserDTO {
    private final String username;
    private final String name;
    private final String lastName;

    public UserDTO(String username, String name, String lastName) {
        this.username = username;
        this.name     = name;
        this.lastName = lastName;
    }

    public String getUsername() { return username; }
    public String getName()     { return name;     }
    public String getLastName() { return lastName; }
}