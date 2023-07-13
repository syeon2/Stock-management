package project.stockmanagement.item.dao.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Item {

	private Long id;
	private final String name;
	private final Integer quantity;
	private final Integer itemCategoryId;

	@Builder
	private Item(Long id, String name, Integer quantity, Integer itemCategoryId) {
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.itemCategoryId = itemCategoryId;
	}
}
