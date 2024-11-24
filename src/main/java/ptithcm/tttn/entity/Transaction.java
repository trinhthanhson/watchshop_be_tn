package ptithcm.tttn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Data
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long transaction_id;

    @Column
    private String content;

    @Column
    private String transaction_code;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime updated_at;

    @Column
    private int total_quantity;

    @Column
    private int total_price;

    @Column
    private String status;

    @Column
    private Long type_id;

    @Column
    private Long staff_id;

    @Column
    private Long updated_by;

    @Column
    private Long request_id;

    @Column
    private Long bill_id;

    @OneToOne
    @JoinColumn(name = "bill_id",insertable = false,updatable = false)
    @JsonIgnore
    private Bill_supplier billSupplier;

    @ManyToOne
    @JoinColumn(name =  "request_id",insertable = false,updatable = false)
    @JsonIgnore
    private Transaction_request transactionRequest;

    @ManyToOne
    @JoinColumn(name = "type_id",insertable = false,updatable = false)
    private Type type_transaction;

    @ManyToOne
    @JoinColumn(name = "staff_id",insertable = false,updatable = false)
    private Staff staff_transaction;

    @ManyToOne
    @JoinColumn(name = "updated_by",insertable = false,updatable = false)
    private Staff staff_updated;

    @OneToMany(mappedBy = "transaction_detail")
    private List<Transaction_detail> transactionDetails;



}
