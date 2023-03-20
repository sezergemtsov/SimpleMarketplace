package testcase.effective_mobile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import testcase.effective_mobile.models.product.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Modifying
    @Query("update Review set isDeleted = true where id = :id")
    void markToDelete(@Param("id") long id);

    @Modifying
    @Query("update Review set review = :text where id = :id")
    void changeReview(@Param("id") long id, @Param("text") String text);
}
