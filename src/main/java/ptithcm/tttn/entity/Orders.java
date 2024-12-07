package ptithcm.tttn.entity;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import ptithcm.tttn.config.BooleanToCharConverter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Data
@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long order_id;

    @Column
    private String recipient_name;

    @Column
    private String recipient_phone;

    @Column
    private int total_quantity;

    @Column
    private int total_price;

    @Column
    private String address;

    @Column
    private String note;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime updated_at;

    @Column(name = "is_cancel", columnDefinition = "CHAR(1)")
    @Convert(converter = BooleanToCharConverter.class)
    private Boolean is_cancel;

    @Column
    private Long staff_id;

    @Column
    private Long customer_id;

    @Column
    private Long status_id;

    @Column
    private Long updated_by;

    @OneToOne(mappedBy = "order_bill")
    @JsonIgnore
    private Bill bill;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "staff_id",insertable = false,updatable = false)
    private Staff staff_order;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "updated_by", insertable = false, updatable = false)
    private Staff staff_update;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "customer_id",insertable = false,updatable = false)
    private Customer customer_order;

    @OneToMany(mappedBy = "orders")
    private List<Order_detail> orderDetails;

    @OneToMany(mappedBy = "request_order")
    @JsonIgnore
    private List<Transaction_request> requestOrders;

    @ManyToOne
    @JoinColumn(name = "status_id",insertable = false,updatable = false)
    private OrderStatus order_status;

}
