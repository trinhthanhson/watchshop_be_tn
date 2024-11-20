package ptithcm.tttn.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Data
@Entity
@Table(name =  "order_status")
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long status_id;

    @Column
    private String status_name;

    @Column
    private int status_index;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime updated_at;

    @Column
    private Long staff_id;

    @Column
    private Long updated_by;

    @ManyToOne
    @JoinColumn(name = "staff_id",insertable = false,updatable = false)
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "updated_by",insertable = false,updatable = false)
    private Staff staff_update;

    @OneToMany(mappedBy = "order_status")
    private List<Orders> orders;
}
