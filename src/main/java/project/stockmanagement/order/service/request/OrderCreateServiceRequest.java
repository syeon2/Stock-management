package project.stockmanagement.order.service.request;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;
import project.stockmanagement.order.dao.domain.Order;
import project.stockmanagement.order.dao.domain.OrderDetail;
import project.stockmanagement.order.dao.domain.OrderStatus;

@Getter
public class OrderCreateServiceRequest {

	private final List<OrderedItem> orderedItems;
	private final Integer centerId;

	@Builder
	private OrderCreateServiceRequest(List<OrderedItem> orderedItems, Integer centerId) {
		this.orderedItems = orderedItems;
		this.centerId = centerId;
	}

	public Order toOrderDomain(OrderStatus orderStatus) {

		Integer totalCount = orderedItems.stream()
			.mapToInt(OrderedItem::getCount)
			.sum();

		return Order.builder()
			.orderStatus(orderStatus)
			.totalCount(totalCount)
			.centerId(this.centerId)
			.build();
	}

	public List<OrderDetail> toOrderDetails(Long orderId) {
		return orderedItems.stream()
			.map(item -> OrderDetail.builder()
				.name(item.getName())
				.count(item.getCount())
				.orderId(orderId)
				.itemId(item.getId())
				.build()
			).collect(Collectors.toList());
	}
}
