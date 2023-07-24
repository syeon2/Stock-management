package project.stockmanagement.order.web.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SetItemStockRequest {

	@NotNull(message = "상품 아이디는 필수 값입니다.")
	private Long itemId;

	@NotNull(message = "재고는 필수 값입니다.")
	@Min(value = 1, message = "재고는 0이하일 수 없습니다.")
	private Integer stock;

	@Builder
	private SetItemStockRequest(Long itemId, Integer stock) {
		this.itemId = itemId;
		this.stock = stock;
	}
}
