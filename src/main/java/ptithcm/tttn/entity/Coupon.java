package ptithcm.tttn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ptithcm.tttn.config.BooleanToCharConverter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long coupon_id;

    @Column
    private String content;

    @Column
    private Date start_date;

    @Column
    private Date end_date;

    @Column
    private float percent;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime updated_at;

    @Column
    private String status;

    @Column
    private String title;

    @Column(name = "is_delete", columnDefinition = "CHAR(1)")
    @Convert(converter = BooleanToCharConverter.class)
    private Boolean is_delete;

    @Column
    private Long created_by;

    @Column
    private Long updated_by;

    @OneToMany(mappedBy = "coupon_detail")
    private List<Coupon_detail> couponDetails;

    @ManyToOne
    @JoinColumn(name = "created_by", insertable = false, updatable = false)
    private Staff staff_create;

    @ManyToOne
    @JoinColumn(name = "updated_by", insertable = false, updatable = false)
    private Staff staff_update;

}
