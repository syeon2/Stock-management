package project.stockmanagement.item.service.response;

import lombok.Builder;
import lombok.Getter;
import project.stockmanagement.item.dao.domain.Item;

@Getter
public class ItemResponse {

	private final Long id;
	private final String name;
	private final Integer quantity;
	private final Integer itemCategoryId;

	@Builder
	private ItemResponse(Long id, String name, Integer quantity, Integer itemCategoryId) {
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.itemCategoryId = itemCategoryId;
	}

	public static ItemResponse of(Item item) {
		return ItemResponse.builder()
			.id(item.getId())
			.name(item.getName())
			.quantity(item.getQuantity())
			.itemCategoryId(item.getItemCategoryId())
			.build();
	}
}
