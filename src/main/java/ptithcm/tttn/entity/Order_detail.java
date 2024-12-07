package ptithcm.tttn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
@Data
@Entity
@Table(name = "order_detail")
public class Order_detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long order_detail_id;

    @Column
    private int quantity;

    @Column
    private int price;

    @Column
    private String product_id;

    @Column
    private Long order_id;

    @ManyToOne
    @JoinColumn(name = "product_id",insertable = false,updatable = false)
    private Product product_order;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "order_id",insertable = false,updatable = false)
    private Orders orders;

    @OneToOne(mappedBy = "order_detail")
    @JsonIgnore
    private Review review;


}
