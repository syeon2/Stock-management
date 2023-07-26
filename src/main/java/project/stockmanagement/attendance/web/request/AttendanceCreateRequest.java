package project.stockmanagement.attendance.web.request;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.stockmanagement.attendance.dao.domain.WorkStatus;
import project.stockmanagement.attendance.service.request.AttendanceCreateServiceRequest;

@Getter
@NoArgsConstructor
public class AttendanceCreateRequest {

	@NotNull(message = "근무 형태는 필수 값 입니다.")
	private WorkStatus workStatus;

	@NotNull(message = "근로자 아이디는 필수 값 입니다.")
	private Long employeeId;

	@NotNull(message = "센터 아이디는 필수 값 입니다.")
	private Integer centerId;

	@Builder
	private AttendanceCreateRequest(WorkStatus workStatus, Long employeeId, Integer centerId) {
		this.workStatus = workStatus;
		this.employeeId = employeeId;
		this.centerId = centerId;
	}

	public AttendanceCreateServiceRequest toServiceRequest() {
		return AttendanceCreateServiceRequest.builder()
			.workStatus(this.workStatus)
			.employeeId(this.employeeId)
			.centerId(this.centerId)
			.build();
	}
}
