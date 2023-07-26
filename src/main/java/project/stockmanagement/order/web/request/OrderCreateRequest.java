package project.stockmanagement.order.web.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.stockmanagement.order.service.request.OrderCreateServiceRequest;
import project.stockmanagement.order.service.request.OrderItem;

@Getter
@NoArgsConstructor
public class OrderCreateRequest {

	@NotNull(message = "주문 시 상품은 필수 값입니다.")
	private List<OrderItem> items;

	@NotNull(message = "물류 센터 아이디는 필수 값입니다.")
	private Integer centerId;

	@Builder
	private OrderCreateRequest(List<OrderItem> orderedItems, Integer centerId) {
		this.items = orderedItems;
		this.centerId = centerId;
	}

	public OrderCreateServiceRequest toServiceRequest() {
		return OrderCreateServiceRequest.builder()
			.orderedItems(items)
			.centerId(centerId)
			.build();
	}
}
