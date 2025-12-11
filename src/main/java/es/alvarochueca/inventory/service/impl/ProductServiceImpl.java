package es.alvarochueca.inventory.service.impl;

import es.alvarochueca.inventory.exception.DuplicateSkuException;
import es.alvarochueca.inventory.exception.InsufficientStockException;
import es.alvarochueca.inventory.exception.ProductNotFoundException;
import es.alvarochueca.inventory.model.Product;
import es.alvarochueca.inventory.repository.ProductRepository;
import es.alvarochueca.inventory.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository repository;

	@Override
	@Transactional
	public Product createProduct(Product product) {
		if(repository.existsBySku(product.getSku())){
			throw new DuplicateSkuException(product.getSku());
		}
		return repository.save(product);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Product> getAllProducts() {
		return repository.findAllByOrderByUpdatedAtDesc();
	}

	@Override
	@Transactional(readOnly = true)
	public Product getProductById(Long id) {
		return repository.findById(id).orElseThrow(
				() -> new ProductNotFoundException("Product not found with id: " + id)
		);
	}

	@Override
	@Transactional
	public Product updateProduct(Long id, Product product) {
		Product existingProduct = getProductById(id);
		existingProduct.setName(product.getName());
		existingProduct.setDescription(product.getDescription());
		existingProduct.setMinStock(product.getMinStock());
		existingProduct.setPrice(product.getPrice());
		return repository.save(existingProduct);
	}

	@Override
	@Transactional
	public void deleteProduct(Long id) {
		Product existingProduct = getProductById(id);
		repository.delete(existingProduct);
	}

	@Override
	@Transactional(readOnly = true)
	public Product getProductBySku(String sku) {
		return repository.findBySku(sku).orElseThrow(() -> new ProductNotFoundException("Product not found with sku: " + sku));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Product> findByName(String name) {
		if (name == null || name.trim().isEmpty()){
			throw new IllegalArgumentException("Name can't be null or empty");
		}
		return repository.findByNameContainingIgnoreCase(name);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Product> findByPriceBetween(BigDecimal min, BigDecimal max) {
		if (min.compareTo(max) > 0){
			throw new IllegalArgumentException("Min price can't be greater than max price");
		}
		return repository.findByPriceBetween(min, max);
	}

	@Override
	@Transactional
	public Product addStock(Long id, Integer quantity) {
		if (quantity == null || quantity <= 0) {
			throw new IllegalArgumentException("Quantity to add must be positive.");
		}
		Product existingProduct = getProductById(id);
		existingProduct.addStock(quantity);
		return repository.save(existingProduct);
	}

	@Override
	@Transactional
	public Product reduceStock(Long id, Integer quantity) {
		if (quantity == null || quantity <= 0) {
			throw new IllegalArgumentException("Quantity to reduce must be positive.");
		}
		Product existingProduct = getProductById(id);
		int newQuantity = existingProduct.getQuantity() - quantity;

		if (newQuantity < 0) {
			throw new InsufficientStockException(existingProduct.getQuantity(), quantity);
		}
		existingProduct.reduceStock(quantity);
		return repository.save(existingProduct);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Product> findLowStockProducts() {
		return repository.findLowStockProducts();
	}
}
