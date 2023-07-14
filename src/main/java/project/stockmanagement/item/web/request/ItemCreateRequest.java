package project.stockmanagement.item.web.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.stockmanagement.item.service.request.ItemCreateServiceRequest;

@Getter
@NoArgsConstructor
public class ItemCreateRequest {

	@NotNull
	@NotBlank(message = "상품의 이름은 공백을 허용하지 않습니다.")
	private String name;

	@NotNull(message = "상품 카테고리 아이디는 필수 값입니다.")
	private Integer itemCategoryId;

	@Builder
	private ItemCreateRequest(String name, Integer itemCategoryId) {
		this.name = name;
		this.itemCategoryId = itemCategoryId;
	}

	public ItemCreateServiceRequest toServiceRequest() {
		return ItemCreateServiceRequest.builder()
			.name(this.name)
			.itemCategoryId(this.itemCategoryId)
			.build();
	}
}
