package project.stockmanagement.order.service.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import project.stockmanagement.order.dao.domain.OrderStatus;
import project.stockmanagement.order.service.request.OrderItem;

@Getter
public class OrderResponse {

	private final OrderStatus orderStatus;
	private final Integer totalCount;
	private final Integer centerId;
	private final Long employeeId;
	private final List<OrderItem> orderedItems;

	@Builder
	private OrderResponse(OrderStatus orderStatus, Integer totalCount, Integer centerId, Long employeeId,
		List<OrderItem> orderedItems) {
		this.orderStatus = orderStatus;
		this.totalCount = totalCount;
		this.centerId = centerId;
		this.employeeId = employeeId;
		this.orderedItems = orderedItems;
	}
}
