package project.stockmanagement.item.web.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.stockmanagement.item.service.request.ItemUpdateServiceRequest;

@Getter
@NoArgsConstructor
public class ItemUpdateRequest {

	@NotBlank(message = "상품 이름은 공백을 허용하지 않습니다.")
	private String name;

	@Min(value = 0, message = "수량은 음수가 될 수 없습니다.")
	private Integer quantity;
	
	private Integer itemCategoryId;

	@Builder
	private ItemUpdateRequest(String name, Integer quantity, Integer itemCategoryId) {
		this.name = name;
		this.quantity = quantity;
		this.itemCategoryId = itemCategoryId;
	}

	public ItemUpdateServiceRequest toServiceRequest() {
		return ItemUpdateServiceRequest.builder()
			.name(this.name)
			.quantity(this.quantity)
			.itemCategoryId(this.itemCategoryId)
			.build();
	}
}
