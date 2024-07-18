package org.store.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.store.core.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
