package com.retailpulse.repository;
import com.retailpulse.model.CartItem;
import com.retailpulse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
    void deleteByUser(User user);
}
