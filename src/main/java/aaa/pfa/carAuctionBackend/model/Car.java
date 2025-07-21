package aaa.pfa.carAuctionBackend.model;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="cars")
public class Car {

    @Column(nullable = false)
    //TODO i might handle make with enums
    private String make, model;

    @Column(nullable = false)
    private int year;
    private double price;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user; //owner

    @Column
    private Date datePosted;

    public Car(){};

    public Car(String make, String model, double price, int year, User user){
        this.make = make;
        this.model = model;
        this.price = price;
        this.year = year;
        this.datePosted = new Date();
        this.user = user;
        printDetails();
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

    public double getPrice() {
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

    public void printDetails() {
        System.out.println("Car ID: " + id);
        System.out.println("Make: " + make);
        System.out.println("Model: " + model);
        System.out.println("Year: " + year);
        System.out.println("Price: " + price);
        System.out.println("Date Posted: " + datePosted);
        System.out.println("User: " + (user != null ? user : "None"));
    }
}
