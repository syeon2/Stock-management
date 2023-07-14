package project.stockmanagement.order.service.request;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import project.stockmanagement.order.dao.domain.Order;
import project.stockmanagement.order.dao.domain.OrderDetail;
import project.stockmanagement.order.dao.domain.OrderStatus;

@Getter
@RequiredArgsConstructor
public class OrderCreateServiceRequest {

	private final List<OrderedItem> orderedItems;
	private final Integer centerId;

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
				.id(item.getId())
				.build()
			).collect(Collectors.toList());
	}
}
