package project.stockmanagement.common.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import project.stockmanagement.common.basewrapper.ApiResult;

@RestControllerAdvice
public class ErrorController {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
	public ApiResult<Void> handlerBadRequest(RuntimeException exception) {
		return ApiResult.onFailure(exception.getLocalizedMessage());
	}
}
