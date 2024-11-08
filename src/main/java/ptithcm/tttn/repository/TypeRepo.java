package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Supplier;
import ptithcm.tttn.entity.Type;

@Repository
public interface TypeRepo extends JpaRepository<Type,Long> {

    @Query(value = "SELECT * FROM type WHERE type_name = ?1  ", nativeQuery = true)
    Type findTypeByName(String type_name);
}
