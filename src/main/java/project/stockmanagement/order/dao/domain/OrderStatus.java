package project.stockmanagement.order.dao.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
	WAITING("처리 대기"),
	PROCESS("처리 중"),
	COMPLETE("처리 완료"),
	CANCEL("주문 취소");

	private final String text;
}
