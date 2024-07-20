package org.store.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.store.core.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
