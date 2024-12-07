package ptithcm.tttn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Data
@Entity
@Table(name = "warranty")
public class Warranty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long warranty_id;

    @Column
    private LocalDateTime start_date;

    @Column
    private LocalDateTime end_date;

    @Column
    private LocalDateTime updated_at;

    @Column
    private LocalDateTime created_at;

    @Column
    private String status;

    @Column
    private String product_id;

    @OneToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product_warranty;

    @OneToMany(mappedBy = "warranty_detail")
    @JsonIgnore
    private List<Warranty_detail> warrantyDetails;


}
