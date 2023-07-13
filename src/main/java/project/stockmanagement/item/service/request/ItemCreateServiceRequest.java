package project.stockmanagement.item.service.request;

import lombok.Builder;
import lombok.Getter;
import project.stockmanagement.item.dao.domain.Item;

@Getter
public class ItemCreateServiceRequest {

	private final String name;
	private final Integer itemCategoryId;

	@Builder
	public ItemCreateServiceRequest(String name, Integer itemCategoryId) {
		this.name = name;
		this.itemCategoryId = itemCategoryId;
	}

	public Item toDomain(Integer quantity) {
		return Item.builder()
			.name(this.name)
			.quantity(quantity)
			.itemCategoryId(this.itemCategoryId)
			.build();
	}
}
