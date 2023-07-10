package project.stockmanagement.common.basewrapper;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiResult<T> {

	private final T data;
	private final String message;

	@Builder
	private ApiResult(T data, String message) {
		this.data = data;
		this.message = message;
	}

	public static <T> ApiResult<T> onSuccess(T data) {
		return ApiResult.<T>builder()
			.data(data)
			.build();
	}

	public static <T> ApiResult<T> onFailure(String message) {
		return ApiResult.<T>builder()
			.message(message)
			.build();
	}
}
