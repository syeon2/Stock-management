package project.stockmanagement.common.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import project.stockmanagement.common.basewrapper.ApiResult;
import project.stockmanagement.common.error.exception.NotEnoughStockException;

@RestControllerAdvice
public class ErrorController {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
	public ApiResult<Void> handlerBadRequest(RuntimeException exception) {
		return ApiResult.onFailure(exception.getLocalizedMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NotEnoughStockException.class)
	public ApiResult<Void> handlerNotEnoughStockRequest(NotEnoughStockException e) {
		return ApiResult.onFailure(e.getLocalizedMessage());
	}

	/**
	 * Validation Exception Handler
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ApiResult<Void> handlerValidationRequest(MethodArgumentNotValidException e) {
		return ApiResult.onFailure(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
	}
}
