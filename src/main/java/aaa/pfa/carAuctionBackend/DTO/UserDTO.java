package aaa.pfa.carAuctionBackend.DTO;

public class UserDTO {
    private final String username;
    private final String name;
    private final String lastName;
    private final Long id;

    public UserDTO(String username, String name, String lastName, Long id) {
        this.username = username;
        this.name     = name;
        this.lastName = lastName;
        this.id       = id;

    }

    public String getUsername() { return username; }
    public String getName()     { return name;     }
    public String getLastName() { return lastName; }
    public Long getId()       { return id;     }
}