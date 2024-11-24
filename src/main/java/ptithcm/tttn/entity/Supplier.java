package ptithcm.tttn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Data
@Entity
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long supplier_id;

    @Column
    private String supplier_name;

    @Column
    private String tax_id;

    @Column
    private String address;

    @Column
    private String phone;

    @Column
    private String email;

    @Column
    private String status;

    @Column
    private String fax;

    @Column
    private Long created_by;

    @Column
    private Long updated_by;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime updated_at;

    @ManyToOne
    @JoinColumn(name = "created_by",insertable = false, updatable = false)
    private Staff staff_create;

    @ManyToOne
    @JoinColumn(name = "updated_by",insertable = false, updatable = false)
    private Staff staff_update;

    @OneToMany(mappedBy = "supplier")
    @JsonIgnore
    private List<Bill_supplier> billSuppliers;
}
