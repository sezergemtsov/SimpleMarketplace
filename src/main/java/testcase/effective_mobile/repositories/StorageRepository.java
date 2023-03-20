package testcase.effective_mobile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import testcase.effective_mobile.models.organization.Organization;
import testcase.effective_mobile.models.product.Discount;
import testcase.effective_mobile.models.product.Product;

@SuppressWarnings("unused")
@Repository
public interface StorageRepository extends JpaRepository<Product, Long> {
    @Modifying
    @Query("update Product set name = :name where id = :id")
    void changeName(@Param("name") String name, @Param("id") long id);

    @Modifying
    @Query("update Product set description = :description where id = :id")
    void changeDescription(@Param("description") String description, @Param("id") long id);

    @Modifying
    @Query("update Product set organization = :organization where id = :id")
    void changeOrganization(@Param("organization") Organization organization, @Param("id") long id);

    @Modifying
    @Query("update Product set price = :price where id =:id")
    void changePrice(@Param("price") long price, @Param("id") long id);

    @Modifying
    @Query("update Product set quantity = :quantity where id =:id")
    void changeQuantity(@Param("quantity") long quantity, @Param("id") long id);

    @Modifying
    @Query("update Product set quantity = quantity + :quantity where id =:id")
    void increaseQuantity(@Param("quantity") long quantity, @Param("id") long id);

    @Modifying
    @Query("update Product set quantity = quantity - :quantity where id =:id")
    void buyingProduct(@Param("quantity") long quantity, @Param("id") long id);

    @Modifying
    @Query("update Product set discount = :discount where id = :id")
    void changeDiscount(@Param("discount") Discount discount, @Param("id") long id);

    @Modifying
    @Query("update Product set keyWords = :keyWords where id = :id")
    void changeKeyWords(@Param("keyWords") String keyWords, @Param("id") long id);

    @Modifying
    @Query("update Product set parameters =:parameters where id =:id")
    void changeParams(@Param("parameters") String parameters, @Param("id") long id);


    @Modifying
    @Query("update Product set isDeleted = true where id = :id")
    void markToDelete(@Param("id") long id);

    @Modifying
    @Query("update Product set isDeleted = false where id = :id")
    void unmarkToDelete(@Param("id") long id);

}
