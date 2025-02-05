package ptithcm.tttn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "transaction_detail")
public class Transaction_detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long trans_detail_id;

    @Column
    private int quantity;

    @Column
    private int quantity_request;

    @Column
    private int start_quantity;

    @Column
    private String note;

    @Column
    private int price;

    @Column
    private Long transaction_id;

    @Column
    private String product_id;

    @ManyToOne
    @JoinColumn(name = "transaction_id",insertable = false,updatable = false)
    @JsonIgnore
    private Transaction transaction_detail;

    @ManyToOne
    @JoinColumn(name = "product_id",insertable = false,updatable = false)
    private Product product_transaction_detail;
}
