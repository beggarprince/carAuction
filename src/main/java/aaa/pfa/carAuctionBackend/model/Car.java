package aaa.pfa.carAuctionBackend.model;


import jakarta.persistence.*;

@Entity
@Table(name="cars")
public class Car {

    @Column(nullable = false)
    public String make, model;

    @Column(nullable = false)
    public int year, price;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    public User user;

    public Car(){};

    public Car(String make, String model, int price, int year){
        this.make = make;
        this.model = model;
        this.price = price;
        this.year = year;
    }
    public void setUser(User user){
        this.user = user;
    }


}
