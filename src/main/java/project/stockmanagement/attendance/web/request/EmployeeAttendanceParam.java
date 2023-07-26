package project.stockmanagement.attendance.web.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EmployeeAttendanceParam {

	private final Long employeeId;
	private final Integer page;
}
