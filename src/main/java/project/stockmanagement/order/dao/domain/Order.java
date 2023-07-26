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

	public Order toUpdateOrderWhenDispatchToEmployee(Long employeeId, OrderStatus orderStatus) {
		return Order.builder()
			.id(this.id)
			.orderStatus(orderStatus)
			.totalCount(this.totalCount)
			.centerId(this.centerId)
			.employeeId(employeeId)
			.build();
	}

	public Order toUpdateOrderWhenComplete(OrderStatus orderStatus, Long employeeId) {
		return Order.builder()
			.id(this.id)
			.orderStatus(orderStatus)
			.totalCount(this.totalCount)
			.centerId(this.centerId)
			.employeeId(employeeId)
			.build();
	}
}
