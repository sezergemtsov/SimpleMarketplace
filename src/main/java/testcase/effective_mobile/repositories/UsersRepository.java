package testcase.effective_mobile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import testcase.effective_mobile.models.user.ShopUser;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<ShopUser, Long> {

    @Modifying
    @Query("update ShopUser set balanceOfUser = balanceOfUser + :balance where id = :id")
    void addBalance(@Param("id") long id, @Param("balance") double balance);

    @Modifying
    @Query("update ShopUser set balanceOfUser = balanceOfUser - :balance where id = :id")
    void takeBalance(@Param("id") long id, @Param("balance") double balance);

    @Modifying
    @Query("update ShopUser set passwordOfUser = :password where id = :id")
    boolean setUserNewPassword(@Param("id") long id, @Param("password") String password);

    Optional<ShopUser> findByNameOfUserIgnoreCase(String nameOfUser);
}
