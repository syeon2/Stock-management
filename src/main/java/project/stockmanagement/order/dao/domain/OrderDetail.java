package project.stockmanagement.order.dao.domain;

import lombok.Builder;
import lombok.Getter;

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
}
