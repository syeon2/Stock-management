package project.stockmanagement.order.dao.domain;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;
import project.stockmanagement.order.service.request.OrderItem;

@Getter
public class OrderDetail {

	private Long id;
	private final String name;
	private final Integer count;
	private final Long orderId;
	private final Long itemId;

	@Builder
	private OrderDetail(Long id, String name, Integer count, Long orderId, Long itemId) {
		this.id = id;
		this.name = name;
		this.count = count;
		this.orderId = orderId;
		this.itemId = itemId;
	}

	public static List<OrderDetail> createFromServiceRequest(List<OrderItem> orderedItems, Long orderId) {
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
