package project.stockmanagement.attendance.dao.domain;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import project.stockmanagement.common.generator.TimeGenerator;

@Getter
public class Attendance {

	private Long id;
	private final WorkStatus workStatus;
	private final Integer centerId;
	private final Long employeeId;
	private final Timestamp createdAt;
	private final Timestamp updatedAt;

	@Builder
	private Attendance(Long id, WorkStatus workStatus, Integer centerId, Long employeeId, Timestamp createdAt,
		Timestamp updatedAt) {
		this.id = id;
		this.workStatus = workStatus;
		this.centerId = centerId;
		this.employeeId = employeeId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public static Attendance toCreateEntity(Attendance attendance, Timestamp currentDateTime) {
		return Attendance.builder()
			.workStatus(attendance.workStatus)
			.centerId(attendance.centerId)
			.employeeId(attendance.employeeId)
			.createdAt(currentDateTime)
			.updatedAt(currentDateTime)
			.build();
	}

	public Attendance toUpdateEntity(Attendance updateAttendance, Timestamp updateDateTime) {
		return Attendance.builder()
			.id(this.id)
			.workStatus(updateAttendance.workStatus)
			.centerId(updateAttendance.centerId)
			.employeeId(updateAttendance.employeeId)
			.createdAt(this.createdAt)
			.updatedAt(updateDateTime)
			.build();
	}

	public Attendance toChangeWorkStatus(WorkStatus workStatus) {
		return Attendance.builder()
			.id(this.id)
			.workStatus(workStatus)
			.centerId(this.centerId)
			.employeeId(this.employeeId)
			.createdAt(this.createdAt)
			.updatedAt(TimeGenerator.currentDateTime())
			.build();
	}
}
