package project.stockmanagement.itemcategory.dao.domain;

import lombok.Builder;
import lombok.Getter;
import project.stockmanagement.itemcategory.service.request.ItemCategoryCreateServiceRequest;

@Getter
public class ItemCategory {

	private Integer id;
	private final String name;
	private final Integer centerId;

	@Builder
	private ItemCategory(String name, Integer centerId) {
		this.name = name;
		this.centerId = centerId;
	}

	private ItemCategory(Integer id, String name, Integer centerId) {
		this.id = id;
		this.name = name;
		this.centerId = centerId;
	}

	public static ItemCategory createFromServiceRequest(ItemCategoryCreateServiceRequest request) {
		return ItemCategory.builder()
			.name(request.getName())
			.centerId(request.getCenterId())
			.build();
	}
}
