package ptithcm.tttn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Data
@Entity
@Table(name = "type")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long type_id;

    @Column
    private String type_name;

    @Column
    private LocalDateTime created_at;

    @OneToMany(mappedBy = "type_request")
    @JsonIgnore
    private List<Transaction_request> transactionRequests;

    @OneToMany(mappedBy = "type_transaction")
    @JsonIgnore
    private List<Transaction> Transactions;
    
}
