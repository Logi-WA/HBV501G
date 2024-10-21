package is.hi.verzla.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import is.hi.verzla.entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c WHERE c.userId = :userId")
    Cart findByUserId(@Param("userId") Long userId);
}
