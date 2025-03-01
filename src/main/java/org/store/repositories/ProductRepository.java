package org.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.store.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
