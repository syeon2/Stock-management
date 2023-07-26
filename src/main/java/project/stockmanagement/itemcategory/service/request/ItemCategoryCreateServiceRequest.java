package project.stockmanagement.itemcategory.service.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemCategoryCreateServiceRequest {

	private final String name;
	private final Integer centerId;

	@Builder
	private ItemCategoryCreateServiceRequest(String name, Integer centerId) {
		this.name = name;
		this.centerId = centerId;
	}
}
