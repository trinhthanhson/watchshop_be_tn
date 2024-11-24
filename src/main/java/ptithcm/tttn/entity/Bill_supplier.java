package ptithcm.tttn.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "bill_supplier")
public class Bill_supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long bill_id;

    @Column
    private LocalDateTime created_at;

    @Column
    private Long supplier_id;

    @Column
    private String bill_code;

    @ManyToOne
    @JoinColumn(name = "supplier_id",insertable = false,updatable = false)
    @JsonIgnore
    private Supplier supplier;

    @OneToOne(mappedBy = "billSupplier")
    @JsonIgnore
    private Transaction transaction;

}
