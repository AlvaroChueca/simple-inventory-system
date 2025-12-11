package es.alvarochueca.inventory.repository;

import es.alvarochueca.inventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	Optional<Product> findBySku(String sku);

	boolean existsBySku(String sku);

	List<Product> findAllByOrderByUpdatedAtDesc();

	List<Product> findByNameContainingIgnoreCase(String name);

	List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);

	@Query("SELECT p FROM Product p WHERE p.quantity < p.minStock ORDER BY (p.minStock - p.quantity) DESC")
	List<Product> findLowStockProducts();
}
