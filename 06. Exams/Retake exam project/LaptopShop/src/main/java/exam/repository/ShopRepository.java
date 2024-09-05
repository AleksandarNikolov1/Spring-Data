package exam.repository;

import exam.model.entities.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    boolean existsByName(String name);
    Shop findByName(String name);
}
