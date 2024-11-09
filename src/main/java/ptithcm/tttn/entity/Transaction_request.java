package ptithcm.tttn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Data
@Entity
@Table(name = "transaction_request")
public class Transaction_request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long request_id;

    @Column
    private String note;

    @Column
    private String content;

    @Column
    private String status;

    @Column
    private int total_quantity;

    @Column
    private int total_price;

    @Column
    private LocalDateTime created_at;

    @Column
    private Long type_id;

    @Column
    private Long staff_id_created;

    @Column
    private Long staff_id_updated;

    @ManyToOne
    @JoinColumn(name = "staff_id_created",insertable = false,updatable = false)
    private Staff staff_created_request;

    @ManyToOne
    @JoinColumn(name = "staff_id_updated",insertable = false,updatable = false)
    private Staff staff_updated_request;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "type_id",insertable = false,updatable = false)
    private Type type_request;

    @OneToMany(mappedBy = "request")
    @JsonIgnore
    private List<Request_detail> requestDetails;



}
