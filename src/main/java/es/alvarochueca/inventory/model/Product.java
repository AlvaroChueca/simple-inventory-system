package es.alvarochueca.inventory.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_SEQ_GEN")
	@SequenceGenerator(name = "PRODUCT_SEQ_GEN", sequenceName = "seq_product", initialValue = 1, allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", nullable = false)
	@NotBlank(message = "Name is required")
	@Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
	private String name;

	@Column(name = "description")
	@Size(max = 500, message = "Description must be less than 500 characters")
	private String description;

	@Column(name = "quantity", nullable = false)
	@NotNull(message = "Quantity is required")
	@Min(value = 0, message = "Quantity must be greater than or equal to 0")
	private Integer quantity;

	@Column(name = "min_stock", nullable = false)
	@NotNull(message = "Minimum stock is required")
	@Min(value = 0, message = "Minimum stock must be greater than or equal to 0")
	private Integer minStock;

	@Column(name = "price", nullable = false)
	@NotNull(message = "Price is required")
	@DecimalMin(value = "0.01", message = "Price must be greater than or equal to 0.01")
	@Digits(integer = 10, fraction = 2)
	private BigDecimal price;

	@Column(name = "sku", nullable = false, unique = true)
	@Pattern(regexp = "[A-Z]{3}-[0-9]{4}", message = "SKU must be in the format XXX-XXXX")
	private String sku;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	public boolean isLowStock() {
		return quantity <= minStock;
	}
}
