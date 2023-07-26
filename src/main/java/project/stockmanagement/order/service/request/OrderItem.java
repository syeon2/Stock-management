package project.stockmanagement.order.service.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderItem {

	private Long id;
	private String name;
	private Integer count;

	@Builder
	private OrderItem(Long id, String name, Integer count) {
		this.id = id;
		this.name = name;
		this.count = count;
	}
}
