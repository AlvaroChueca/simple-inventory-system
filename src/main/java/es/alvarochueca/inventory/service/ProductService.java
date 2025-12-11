package es.alvarochueca.inventory.service;

import es.alvarochueca.inventory.model.Product;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

	Product createProduct(@Valid Product product);

	List<Product> getAllProducts();

	Product getProductById(Long id);

	Product getProductBySku(String sku);

	Product updateProduct(Long id, @Valid Product product);

	void deleteProduct(Long id);

	List<Product> findByName(String name);

	List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);

	Product addStock(Long id, Integer quantity);

	Product reduceStock(Long id, Integer quantity);

	List<Product> findLowStockProducts();
}
