package project.stockmanagement.order.dao.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Order {

	private Long id;
	private final OrderStatus orderStatus;
	private final Integer totalCount;
	private final Integer centerId;
	private final Long employeeId;

	@Builder
	private Order(Long id, OrderStatus orderStatus, Integer totalCount, Integer centerId, Long employeeId) {
		this.id = id;
		this.orderStatus = orderStatus;
		this.totalCount = totalCount;
		this.centerId = centerId;
		this.employeeId = employeeId;
	}
}
