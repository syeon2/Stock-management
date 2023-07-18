package project.stockmanagement.employee.dao.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EmployeeStatus {

	WAITING("대기 중"),
	WORKING("업무 중"),
	LEAVE_EARLY("조퇴"),
	LEAVE_ON_TIME("정시 퇴근"),
	OVERTIME("연장 근무");

	private final String text;
}
