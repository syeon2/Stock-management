package project.stockmanagement.employee.web.request;

import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EmployeeScheduleRequest {

	@Range(min = 1950, max = 2100, message = "연도는 1950 - 2100까지 입력 가능합니다.")
	private final Integer year;

	@Range(min = 1, max = 12, message = "월은 1 - 12까지 입력 가능합니다.")
	private final Integer month;

	@Range(min = 1, max = 31, message = "일은 1 - 31까지 입력 가능합니다.")
	private final Integer day;
}
