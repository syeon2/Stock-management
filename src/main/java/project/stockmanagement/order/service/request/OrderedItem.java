package project.stockmanagement.order.service.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderedItem {

	private final Long id;
	private final String name;
	private final Integer count;

	@Builder
	private OrderedItem(Long id, String name, Integer count) {
		this.id = id;
		this.name = name;
		this.count = count;
	}
}
