package ptithcm.tttn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column
    private String product_id;

    @Column(length = 150)
    private String product_name;

    @Column
    private int quantity;

    @Column(length = 30)
    private String color;

    @Column(length = 120)
    private String band_material;

    @Column(length = 120)
    private String dial_type;

    @Column(length = 150)
    private String func;

    @Column(length = 20)
    private String gender;

    @Column(length = 20)
    private String model;

    @Column(length = 20)
    private String series;

    @Column(length = 120)
    private String water_resistance;

    @Column(length = 120)
    private String case_diameter;

    @Column(length = 120)
    private String case_material;

    @Column
    private String detail;

    @Column(length = 500)
    private String image;

    @Column(length = 120)
    private String machine_movement;

    @Column(length = 120)
    private String band_width;

    @Column(length = 120)
    private String case_thickness;

    @Column(length = 10)
    private String status;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime updated_at;

    @Column
    private Long category_id;

    @Column
    private Long brand_id;

    @Column
    private Long created_by;

    @Column
    private Long updated_by;

    @ManyToOne
    @JoinColumn(name = "category_id",insertable = false,updatable = false)
    private Category category_product;

    @ManyToOne
    @JoinColumn(name = "created_by",insertable = false,updatable = false)
    private Staff staff_created;

    @ManyToOne
    @JoinColumn(name = "updated_by",insertable = false,updatable = false)
    private Staff staff_updated;

    @ManyToOne
    @JoinColumn(name = "brand_id",insertable = false,updatable = false)
    private Brand brand_product;

    @JsonIgnore
    @OneToMany(mappedBy = "product_order")
    private List<Order_detail> orderDetails;

    @JsonIgnore
    @OneToMany(mappedBy = "product_cart")
    private List<Cart_detail> cartDetails;

    @OneToMany(mappedBy = "product_price")
    private List<Update_price> updatePrices;

    @JsonIgnore
    @OneToMany(mappedBy = "product_coupon")
    private List<Coupon_detail> couponDetails;

    @JsonIgnore
    @OneToMany(mappedBy = "product_request")
    private List<Request_detail> requestDetails;

    @JsonIgnore
    @OneToMany(mappedBy = "product_transaction_detail")
    private List<Transaction_detail> transactionDetails;

    @OneToMany(mappedBy = "product_review")
    @JsonIgnore
    private List<Review> reviews;


}
