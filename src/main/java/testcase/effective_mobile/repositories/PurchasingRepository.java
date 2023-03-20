package testcase.effective_mobile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import testcase.effective_mobile.models.user.Purchasing;
import testcase.effective_mobile.models.user.ShopUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchasingRepository extends JpaRepository<Purchasing, Long> {
    List<Purchasing> findByUser(ShopUser user);

    @Modifying
    @Query("update Purchasing set isReturned = true")
    void changeReturning();

    @Query("select Product from Purchasing where product.id = :id and user.id = :userId")
    Optional<Purchasing> getProductById(@Param("id") Long id, @Param("userId") Long userId);

}
