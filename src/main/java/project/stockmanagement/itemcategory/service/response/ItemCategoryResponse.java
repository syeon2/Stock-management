package project.stockmanagement.itemcategory.service.response;

import lombok.Builder;
import lombok.Getter;
import project.stockmanagement.itemcategory.dao.domain.ItemCategory;

@Getter
public class ItemCategoryResponse {

	private Integer id;
	private String name;
	private Integer centerId;

	@Builder
	private ItemCategoryResponse(Integer id, String name, Integer centerId) {
		this.id = id;
		this.name = name;
		this.centerId = centerId;
	}

	public static ItemCategoryResponse of(ItemCategory itemCategory) {
		return ItemCategoryResponse.builder()
			.id(itemCategory.getId())
			.name(itemCategory.getName())
			.centerId(itemCategory.getCenterId())
			.build();
	}
}
