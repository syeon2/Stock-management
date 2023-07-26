package project.stockmanagement.item.dao.domain;

import lombok.Builder;
import lombok.Getter;
import project.stockmanagement.item.service.request.ItemCreateServiceRequest;
import project.stockmanagement.item.service.request.ItemUpdateServiceRequest;

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

	public static Item createFromServiceRequest(ItemCreateServiceRequest request, Integer quantity) {
		return Item.builder()
			.name(request.getName())
			.quantity(quantity)
			.itemCategoryId(request.getItemCategoryId())
			.build();
	}

	public static Item updateFromServiceRequest(ItemUpdateServiceRequest request) {
		return Item.builder()
			.name(request.getName())
			.quantity(request.getQuantity())
			.itemCategoryId(request.getItemCategoryId())
			.build();
	}
}
