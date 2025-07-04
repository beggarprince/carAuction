package aaa.pfa.carAuctionBackend.model;


import jakarta.persistence.*;

@Entity
@Table(name="items")
public class SaleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id")
    public User user;

    //TODO i should probably make some enums instead of string category
    @Column(nullable = false)
    public String title, description, category;
    @Column
    public int price;

    public SaleItem(){};

    public SaleItem(
            String title, String description, String category,
            int price, User user
    ){
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.user = user;
    }

}
