package project.stockmanagement.order.web.request;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderToEmployeeRequest {

	@NotNull(message = "주문 아이디는 필수 값입니다.")
	private Long orderId;

	@NotNull(message = "근로자 아이디는 필수 값입니다.")
	private Long employeeId;

	@Builder
	private OrderToEmployeeRequest(Long orderId, Long employeeId) {
		this.orderId = orderId;
		this.employeeId = employeeId;
	}
}
