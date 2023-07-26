package project.stockmanagement.order.service.response;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;
import project.stockmanagement.order.dao.domain.Order;
import project.stockmanagement.order.dao.domain.OrderDetail;
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

	public static OrderResponse combineOrderAndOrderDetailsToOrderResponse(Order order,
		List<OrderDetail> orderDetails) {
		List<OrderItem> orderItems = orderDetails.stream()
			.map(detail -> OrderItem.builder()
				.id(detail.getId())
				.name(detail.getName())
				.count(detail.getCount())
				.build()
			).collect(Collectors.toList());

		Integer totalCount = orderDetails.stream()
			.mapToInt(OrderDetail::getCount)
			.sum();

		return OrderResponse.builder()
			.orderStatus(order.getOrderStatus())
			.totalCount(totalCount)
			.centerId(order.getCenterId())
			.employeeId(order.getEmployeeId())
			.orderedItems(orderItems)
			.build();
	}
}
