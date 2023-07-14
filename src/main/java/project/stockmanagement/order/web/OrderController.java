package project.stockmanagement.order.web;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.stockmanagement.common.basewrapper.ApiResult;
import project.stockmanagement.order.service.OrderService;
import project.stockmanagement.order.service.response.OrderResponse;
import project.stockmanagement.order.web.request.OrderCreateRequest;

@RestController
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping("/api/v1/order")
	public ApiResult<Long> createOrder(@Valid @RequestBody OrderCreateRequest request) {
		Long orderedId = orderService.createOrder(request.toServiceRequest());

		return ApiResult.onSuccess(orderedId);
	}

	@GetMapping("/api/v1/order/{orderId}")
	public ApiResult<OrderResponse> findOrderInfo(@PathVariable Long orderId) {
		OrderResponse orderResponse = orderService.findOrderInfo(orderId);

		return ApiResult.onSuccess(orderResponse);
	}
}
