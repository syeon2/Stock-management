package project.stockmanagement.order.web;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.stockmanagement.common.basewrapper.ApiResult;
import project.stockmanagement.order.service.OrderToEmployeeService;
import project.stockmanagement.order.service.response.OrderToEmployeeResponse;
import project.stockmanagement.order.web.request.OrderToEmployeeRequest;

@RestController
@RequiredArgsConstructor
public class OrderToEmployeeController {

	private final OrderToEmployeeService orderToEmployeeService;

	@PostMapping("/api/v1/employee/order")
	public ApiResult<OrderToEmployeeResponse> dispatchWaitedOrderToEmployee(
		@Valid @RequestBody OrderToEmployeeRequest request) {
		OrderToEmployeeResponse orderToEmployeeResponse =
			orderToEmployeeService.dispatchWaitedOrderToEmployee(request.getOrderId(), request.getEmployeeId());

		return ApiResult.onSuccess(orderToEmployeeResponse);
	}

	@PostMapping("/api/v1/employee-order")
	public ApiResult<Long> completeOrder(@Valid @RequestBody OrderToEmployeeRequest request) {
		Long updateOrderId = orderToEmployeeService.completeOrder(request.getOrderId(), request.getEmployeeId());

		return ApiResult.onSuccess(updateOrderId);
	}
}
