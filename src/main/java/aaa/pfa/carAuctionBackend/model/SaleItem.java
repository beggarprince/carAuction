package aaa.pfa.carAuctionBackend.model;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="items")
public class SaleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String category;

    @Column
    private int price;

    @Column
    private Date datePosted;

    public SaleItem() {}

    public SaleItem(
            String title,
            String description,
            String category,
            int price,
            User user
    ) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.user = user;
        this.datePosted = new Date();
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public Date getDatePosted() {
        return datePosted;
    }
}
