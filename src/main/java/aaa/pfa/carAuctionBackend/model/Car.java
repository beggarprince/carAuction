package aaa.pfa.carAuctionBackend.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="cars")
public class Car {

    //TODO does not handle location, city, zip, distance yet.

    @Column(nullable = false)
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
    private User user; //ownerUsername

    @Column
    private Date datePosted;


    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "JSON")
    private final List<String> pictureURL = new ArrayList<>();

    //This is going to be a ton of information that is optional
    @Column
    private String transmission, drive, fuel, carType, title, cylinder, color, carCondition, description;

    protected Car(){};

    private Car(Builder builder){
        this.make = builder.make;
        this.model = builder.model;
        this.year = builder.year;
        this.mileage = builder.mileage;
        this.price = builder.price;
        this.user = builder.user;

        this.transmission = builder.transmission;
        this.drive = builder.drive;
        this.fuel = builder.fuel;
        this.carType = builder.carType;
        this.title = builder.title;
        this.cylinder = builder.cylinder;
        this.color = builder.color;
        this.carCondition = builder.carCondition;
        this.datePosted = new Date();
        this.description = builder.description;
        printDetails();
    }


    public static class Builder{
        // Required fields
        private final String make;
        private final String model;
        private final int year;
        private final int mileage;
        private final double price;
        private final User user;

        // Optional fields
        private String transmission;
        private String drive;
        private String fuel;
        private String carType;
        private String title;
        private String cylinder;
        private String color;
        private String carCondition;
        private String description;

        public Builder(String make, String model, int year, int mileage, double price, User user) {
            this.make = make;
            this.model = model;
            this.year = year;
            this.mileage = mileage;
            this.price = price;
            this.user = user;
        }


        public Builder transmission(String transmission){
            this.transmission = transmission;
            return this;
        }

        public Builder drive(String drive) {
            this.drive = drive;
            return this;
        }

        public Builder fuel(String fuel) {
            this.fuel = fuel;
            return this;
        }

        public Builder carType(String type) {
            this.carType = type;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder cylinder(String cylinder) {
            this.cylinder = cylinder;
            return this;
        }

        public Builder color(String color) {
            this.color = color;
            return this;
        }

        public Builder carCondition(String condition) {
            this.carCondition = condition;
            return this;
        }

        public Builder description(String description){
            this.description = description;
            return this;
        }

        public Car build() {
            return new Car(this);
        }
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

        if(transmission != null){
            System.out.println("Transmission: " + transmission);
        }
        if (drive != null) {
            System.out.println("Drive: " + drive);
        }
        if (fuel != null) {
            System.out.println("Fuel: " + fuel);
        }
        if (carType != null) {
            System.out.println("Type: " + carType);
        }
        if (title != null) {
            System.out.println("Title: " + title);
        }
        if (cylinder != null) {
            System.out.println("Cylinder: " + cylinder);
        }
        if (color != null) {
            System.out.println("Color: " + color);
        }
        if (carCondition != null) {
            System.out.println("Condition: " + carCondition);
        }
        if(description != null){
            System.out.println("Description: " + description);
        }
        System.out.println("--------------------");
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
