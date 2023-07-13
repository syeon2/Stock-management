package project.stockmanagement.item.service.request;

import lombok.Builder;
import lombok.Getter;
import project.stockmanagement.item.dao.domain.Item;

@Getter
public class ItemUpdateServiceRequest {

	private String name;
	private Integer quantity;
	private Integer itemCategoryId;

	@Builder
	private ItemUpdateServiceRequest(String name, Integer quantity, Integer itemCategoryId) {
		this.name = name;
		this.quantity = quantity;
		this.itemCategoryId = itemCategoryId;
	}

	public Item toDomain() {
		return Item.builder()
			.name(this.name)
			.quantity(this.quantity)
			.itemCategoryId(this.itemCategoryId)
			.build();
	}
}
