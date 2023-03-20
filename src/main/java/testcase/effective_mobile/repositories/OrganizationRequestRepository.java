package testcase.effective_mobile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import testcase.effective_mobile.models.organization.OrganizationRegistrationRequest;

import java.util.List;

@Repository
public interface OrganizationRequestRepository extends JpaRepository<OrganizationRegistrationRequest, Long> {
    List<OrganizationRegistrationRequest> findAllByApprovedFalse();

    @Modifying
    @Query("update OrganizationRegistrationRequest set approved = true where id = :id")
    boolean approvingRequest(@Param("id") long id);
}
