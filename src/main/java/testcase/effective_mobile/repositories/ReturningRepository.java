package testcase.effective_mobile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testcase.effective_mobile.models.user.ReturningInfo;

@Repository
public interface ReturningRepository extends JpaRepository<ReturningInfo, Long> {
}
