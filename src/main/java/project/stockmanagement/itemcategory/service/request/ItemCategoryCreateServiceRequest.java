package project.stockmanagement.itemcategory.service.request;

import lombok.Builder;
import lombok.Getter;
import project.stockmanagement.itemcategory.dao.domain.ItemCategory;

@Getter
public class ItemCategoryCreateServiceRequest {

	private final String name;
	private final Integer centerId;

	@Builder
	private ItemCategoryCreateServiceRequest(String name, Integer centerId) {
		this.name = name;
		this.centerId = centerId;
	}

	public ItemCategory toDomain() {
		return ItemCategory.builder()
			.name(this.name)
			.centerId(this.centerId)
			.build();
	}
}
