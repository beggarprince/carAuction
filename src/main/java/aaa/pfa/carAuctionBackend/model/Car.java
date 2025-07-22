package aaa.pfa.carAuctionBackend.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="cars")
public class Car {

    @Column(nullable = false)
    //TODO i might handle make with enums
    private String make, model;

    @Column(nullable = false)
    private int year, mileage;
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


    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "JSON")
    private List<String> pictureURL = new ArrayList<>();

    public Car(){};

    public Car(String make,
               String model,
               int year,
               int mileage,
               double price,
               User user){
        this.make = make;
        this.model = model;
        this.price = price;
        this.year = year;
        this.datePosted = new Date();
        this.mileage = mileage;
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

    public int getMileage() {
        return mileage;
    }

    public List<String> getPicturesURL() {
        return pictureURL;
    }

    public void setPictureURL(List<String > picURL){
        pictureURL.addAll(picURL);
    }

    public void addPictureUrl(String url){
        pictureURL.add(url);
    }
}
