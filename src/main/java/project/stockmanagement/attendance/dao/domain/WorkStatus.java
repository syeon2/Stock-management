package project.stockmanagement.attendance.dao.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WorkStatus {

	DAY("주간"),
	NIGHT("야간"),
	HOLIDAY_DAY("휴일 주간"),
	HOLIDAY_NIGHT("휴일 야간");

	private final String text;
}
