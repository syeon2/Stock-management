package project.stockmanagement.order.service.request;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import project.stockmanagement.order.dao.domain.Order;
import project.stockmanagement.order.dao.domain.OrderStatus;

@Getter
public class OrderCreateServiceRequest {

	private final List<OrderItem> orderedItems;
	private final Integer centerId;

	@Builder
	private OrderCreateServiceRequest(List<OrderItem> orderedItems, Integer centerId) {
		this.orderedItems = orderedItems;
		this.centerId = centerId;
	}

	public Order toOrderDomain(OrderStatus orderStatus) {

		Integer totalCount = orderedItems.stream()
			.mapToInt(OrderItem::getCount)
			.sum();

		return Order.builder()
			.orderStatus(orderStatus)
			.totalCount(totalCount)
			.centerId(this.centerId)
			.build();
	}
}
