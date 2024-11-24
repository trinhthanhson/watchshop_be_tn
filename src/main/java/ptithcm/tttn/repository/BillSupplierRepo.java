package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Bill_supplier;

@Repository
public interface BillSupplierRepo extends JpaRepository<Bill_supplier,Long> {
}
