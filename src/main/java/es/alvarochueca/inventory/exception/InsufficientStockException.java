package es.alvarochueca.inventory.exception;

public class InsufficientStockException extends RuntimeException {
	public InsufficientStockException(Integer currentStock, Integer quantityToReduce) {
		super(String.format("Not enough stock. Current stock: %d, trying to reduce: %d", currentStock, quantityToReduce));
	}
}
