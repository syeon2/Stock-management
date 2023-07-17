package project.stockmanagement.order.web;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.stockmanagement.common.basewrapper.ApiResult;
import project.stockmanagement.order.service.OrderToEmployeeService;
import project.stockmanagement.order.service.response.OrderToEmployeeResponse;

@RestController
@RequiredArgsConstructor
public class OrderToEmployeeController {

	private final OrderToEmployeeService orderToEmployeeService;

	@PostMapping("/api/v1/employee/order/{employeeId}")
	public ApiResult<OrderToEmployeeResponse> dispatchWaitedOrderToEmployee(@PathVariable Long employeeId) {
		OrderToEmployeeResponse orderToEmployeeResponse =
			orderToEmployeeService.dispatchWaitedOrderToEmployee(employeeId);

		return ApiResult.onSuccess(orderToEmployeeResponse);
	}
}
