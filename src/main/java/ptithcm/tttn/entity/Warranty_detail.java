package ptithcm.tttn.entity;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "warranty_detail")
public class Warranty_detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long warranty_detail_id;

    @Column
    private String content;

    @Column
    private Long warranty_id;

    @Column
    private Long staff_id;

    @ManyToOne
    @JoinColumn(name = "staff_id",insertable = false,updatable = false)
    private Staff warranty_staff;

    @ManyToOne
    @JoinColumn(name = "warranty_id",insertable = false,updatable = false)
    private Warranty warranty_detail;
}
