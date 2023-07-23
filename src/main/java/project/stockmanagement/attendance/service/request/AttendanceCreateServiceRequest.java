package project.stockmanagement.attendance.service.request;

import lombok.Builder;
import lombok.Getter;
import project.stockmanagement.attendance.dao.domain.WorkStatus;

@Getter
public class AttendanceCreateServiceRequest {

	private final WorkStatus workStatus;
	private final Long employeeId;
	private final Integer centerId;

	@Builder
	private AttendanceCreateServiceRequest(WorkStatus workStatus, Long employeeId, Integer centerId) {
		this.workStatus = workStatus;
		this.employeeId = employeeId;
		this.centerId = centerId;
	}
}
