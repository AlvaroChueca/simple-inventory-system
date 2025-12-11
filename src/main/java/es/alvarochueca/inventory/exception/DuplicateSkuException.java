package es.alvarochueca.inventory.exception;

public class DuplicateSkuException extends RuntimeException {

	public DuplicateSkuException(String sku) {
		super("SKU already exists: " + sku);
	}
}
