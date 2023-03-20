package testcase.effective_mobile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import testcase.effective_mobile.models.organization.Organization;

@Repository
public interface OrganizationsRepository extends JpaRepository<Organization,Long> {
    @Modifying
    @Query("update Organization set balance = balance + :balance where id = :id")
    void increaseBalance(@Param("balance") Long balance, @Param("id") Long id);

    @Modifying
    @Query("update Organization set balance = balance - :balance where id = :id")
    void decreaseBalance(@Param("balance") Long balance, @Param("id") Long id);
}

