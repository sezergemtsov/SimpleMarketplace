package testcase.effective_mobile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import testcase.effective_mobile.models.product.Grade;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    @Modifying
    @Query("update Grade set grade = :grade where id = :id")
    void changeGrade(@Param("id") Long id, @Param("grade") Integer grade);
}
