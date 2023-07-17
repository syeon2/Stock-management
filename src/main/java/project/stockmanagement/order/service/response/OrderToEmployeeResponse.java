package project.stockmanagement.order.service.response;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;
import project.stockmanagement.order.dao.domain.OrderDetail;
import project.stockmanagement.order.service.request.OrderItem;

@Getter
public class OrderToEmployeeResponse {

	private final List<OrderItem> orderItems;

	@Builder
	private OrderToEmployeeResponse(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public static OrderToEmployeeResponse of(List<OrderDetail> orderDetails) {
		List<OrderItem> collect = orderDetails.stream()
			.map(detail -> OrderItem.builder()
				.id(detail.getId())
				.name(detail.getName())
				.count(detail.getCount())
				.build())
			.collect(Collectors.toList());

		return OrderToEmployeeResponse.builder()
			.orderItems(collect)
			.build();
	}
}
