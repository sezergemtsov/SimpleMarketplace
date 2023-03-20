package testcase.effective_mobile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testcase.effective_mobile.models.user.Notification;
import testcase.effective_mobile.models.user.ShopUser;

import java.util.List;

@Repository
public interface NotificationsRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByUserListContaining(ShopUser user);
}
