package project.stockmanagement.order.service.response;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;
import project.stockmanagement.order.dao.domain.OrderDetail;
import project.stockmanagement.order.service.request.OrderItem;

@Getter
public class OrderToEmployeeResponse {

	private final Long orderId;
	private final List<OrderItem> orderItems;
	private final Integer totalCount;

	@Builder
	private OrderToEmployeeResponse(Long orderId, List<OrderItem> orderItems, Integer totalCount) {
		this.orderId = orderId;
		this.orderItems = orderItems;
		this.totalCount = totalCount;
	}

	public static OrderToEmployeeResponse of(Long orderId, List<OrderDetail> orderDetails) {
		List<OrderItem> collect = orderDetails.stream()
			.map(detail -> OrderItem.builder()
				.id(detail.getId())
				.name(detail.getName())
				.count(detail.getCount())
				.build())
			.collect(Collectors.toList());

		Integer allCount = orderDetails.stream()
			.mapToInt(OrderDetail::getCount)
			.sum();

		return OrderToEmployeeResponse.builder()
			.orderId(orderId)
			.orderItems(collect)
			.totalCount(allCount)
			.build();
	}
}
