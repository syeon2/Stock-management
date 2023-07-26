package project.stockmanagement.common.error.exception;

public class NotEnoughStockException extends RuntimeException {
	public NotEnoughStockException(String message) {
		super(message);
	}
}
