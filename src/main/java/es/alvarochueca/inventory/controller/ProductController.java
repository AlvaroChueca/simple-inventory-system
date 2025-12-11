package es.alvarochueca.inventory.controller;

import es.alvarochueca.inventory.model.Product;
import es.alvarochueca.inventory.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Validated
public class ProductController {

	private final ProductService productService;

	@PostMapping
	public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product){
		Product newProduct = productService.createProduct(product);
		return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<Product>> getAllProducts(){
		List<Product> productList = productService.getAllProducts();
		return new ResponseEntity<>(productList, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long id){
		Product product = productService.getProductById(id);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product){
		Product updatedProduct = productService.updateProduct(id, product);
		return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
		productService.deleteProduct(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/sku/{sku}")
	public ResponseEntity<Product> getProductBySku(@PathVariable String sku){
		Product product = productService.getProductBySku(sku);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@GetMapping("/search")
	public ResponseEntity<List<Product>> getProductsByName(@RequestParam String name){
		List<Product> products = productService.findByName(name);
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@GetMapping("/precio")
	public ResponseEntity<List<Product>> getProductsByPriceRange(
			@RequestParam BigDecimal min,
			@RequestParam BigDecimal max
	) {
		List<Product> products = productService.findByPriceBetween(min, max);
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@GetMapping("/alertas")
	public ResponseEntity<List<Product>> getLowStockProducts() {
		List<Product> products = productService.findLowStockProducts();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@PostMapping("/{id}/stock/entrada")
	public ResponseEntity<Product> addStock(@PathVariable Long id, @RequestBody Integer quantity) {
		Product product = productService.addStock(id, quantity);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@PostMapping("/{id}/stock/salida")
	public ResponseEntity<Product> reduceStock(@PathVariable Long id, @RequestBody Integer quantity) {
		Product product = productService.reduceStock(id, quantity);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

}
