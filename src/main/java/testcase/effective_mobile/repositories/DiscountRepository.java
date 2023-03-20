package testcase.effective_mobile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testcase.effective_mobile.models.product.Discount;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
