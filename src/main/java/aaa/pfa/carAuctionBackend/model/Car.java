package aaa.pfa.carAuctionBackend.model;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="cars")
public class Car {

    @Column(nullable = false)
    private String make, model;

    @Column(nullable = false)
    private int year, price;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Column
    private Date datePosted;

    public Car(){};

    public Car(String make, String model, int price, int year){
        this.make = make;
        this.model = model;
        this.price = price;
        this.year = year;
        this.datePosted = new Date();
    }
    public void setUser(User user){
        this.user = user;
    }


    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public int getPrice() {
        return price;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Date getDatePosted() {
        return datePosted;
    }
}
