package project.stockmanagement.itemcategory.web.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.stockmanagement.itemcategory.service.request.ItemCategoryCreateServiceRequest;

@Getter
@NoArgsConstructor
public class ItemCategoryCreateRequest {

	@NotNull
	@NotBlank(message = "상품 카테고리의 이름은 공백이 될 수 없습니다.")
	private String name;

	@NotNull(message = "센터 아이디는 필수 값입니다.")
	private Integer centerId;

	@Builder
	private ItemCategoryCreateRequest(String name, Integer centerId) {
		this.name = name;
		this.centerId = centerId;
	}

	public ItemCategoryCreateServiceRequest toServiceRequest() {
		return ItemCategoryCreateServiceRequest.builder()
			.name(this.name)
			.centerId(this.centerId)
			.build();
	}
}
