package project.stockmanagement.employee.web.request;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.stockmanagement.employee.dao.domain.EmployeeStatus;
import project.stockmanagement.employee.service.request.EmployeeUpdateServiceRequest;

@Getter
@NoArgsConstructor
public class EmployeeUpdateRequest {

	@NotBlank(message = "근로자 이름은 공백을 허용하지 않습니다.")
	private String name;

	@Pattern(regexp = "[0-9]{10,11}", message = "10 ~ 11자리의 숫자만 입력 가능합니다.")
	private String phone;

	private EmployeeStatus employeeStatus;

	@Min(message = "최소 재고 처리량은 0입니다.", value = 0)
	private Integer itemPackagingCount;

	@Valid
	private EmployeeScheduleRequest schedule;

	private Integer centerId;

	@Builder
	private EmployeeUpdateRequest(String name, String phone, EmployeeStatus employeeStatus, Integer itemPackagingCount,
		Integer centerId) {
		this.name = name;
		this.phone = phone;
		this.employeeStatus = employeeStatus;
		this.itemPackagingCount = itemPackagingCount;
		this.centerId = centerId;
	}

	public EmployeeUpdateServiceRequest toServiceRequest() {
		return EmployeeUpdateServiceRequest.builder()
			.name(this.name)
			.phone(this.phone)
			.employeeStatus(this.employeeStatus)
			.itemPackagingCount(this.itemPackagingCount)
			.workingDay(getLocalDate())
			.centerId(this.centerId)
			.build();
	}

	private LocalDate getLocalDate() {
		if (this.schedule == null) {
			return null;
		}

		return LocalDate.of(this.schedule.getYear(), this.schedule.getMonth(),
			this.schedule.getDay());
	}
}
