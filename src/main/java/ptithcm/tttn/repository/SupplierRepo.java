package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Category;
import ptithcm.tttn.entity.Supplier;

@Repository
public interface SupplierRepo extends JpaRepository<Supplier,Long> {
    @Query(value = "SELECT * FROM supplier WHERE supplier_name = ?1  ", nativeQuery = true)
    Supplier findSupplierByName(String supplier_name);
}
