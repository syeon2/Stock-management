package project.stockmanagement.attendance.service.response;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import project.stockmanagement.attendance.dao.domain.Attendance;
import project.stockmanagement.attendance.dao.domain.WorkStatus;

@Getter
public class AttendanceResponse {

	private final Long id;
	private final WorkStatus workStatus;
	private final Long employeeId;
	private final Integer centerId;
	private final Timestamp createdAt;
	private final Timestamp updatedAt;

	@Builder
	public AttendanceResponse(Long id, WorkStatus workStatus, Long employeeId, Integer centerId, Timestamp createdAt,
		Timestamp updatedAt) {
		this.id = id;
		this.workStatus = workStatus;
		this.employeeId = employeeId;
		this.centerId = centerId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public static AttendanceResponse of(Attendance attendance) {
		return AttendanceResponse.builder()
			.id(attendance.getId())
			.workStatus(attendance.getWorkStatus())
			.employeeId(attendance.getEmployeeId())
			.centerId(attendance.getCenterId())
			.createdAt(attendance.getCreatedAt())
			.updatedAt(attendance.getUpdatedAt())
			.build();
	}
}
