package project.stockmanagement.attendance.web.request;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.stockmanagement.attendance.dao.domain.WorkStatus;

@Getter
@NoArgsConstructor
public class AttendanceUpdateWorkStatusRequest {

	@NotNull(message = "근무 상태는 필수 값입니다.")
	private WorkStatus workStatus;

	@Builder
	private AttendanceUpdateWorkStatusRequest(WorkStatus workStatus) {
		this.workStatus = workStatus;
	}
}
