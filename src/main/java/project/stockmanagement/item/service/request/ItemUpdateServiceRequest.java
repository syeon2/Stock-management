package project.stockmanagement.item.service.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemUpdateServiceRequest {

	private final String name;
	private final Integer quantity;
	private final Integer itemCategoryId;

	@Builder
	private ItemUpdateServiceRequest(String name, Integer quantity, Integer itemCategoryId) {
		this.name = name;
		this.quantity = quantity;
		this.itemCategoryId = itemCategoryId;
	}
}
