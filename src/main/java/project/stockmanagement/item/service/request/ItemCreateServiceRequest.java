package project.stockmanagement.item.service.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemCreateServiceRequest {

	private final String name;
	private final Integer itemCategoryId;

	@Builder
	public ItemCreateServiceRequest(String name, Integer itemCategoryId) {
		this.name = name;
		this.itemCategoryId = itemCategoryId;
	}
}
