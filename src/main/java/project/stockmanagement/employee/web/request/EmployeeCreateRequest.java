package project.stockmanagement.employee.web.request;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.stockmanagement.employee.service.request.EmployeeCreateServiceRequest;

@Getter
@NoArgsConstructor
public class EmployeeCreateRequest {

	@NotNull
	@NotBlank(message = "근로자 이름은 공백을 허용하지 않습니다.")
	private String name;

	@NotNull
	@Pattern(regexp = "[0-9]{10,11}", message = "10 ~ 11자리의 숫자만 입력 가능합니다.")
	private String phone;

	@Valid
	private EmployeeScheduleRequest schedule;

	@NotNull(message = "센터 아이디는 필수 입니다.")
	private Integer centerId;

	@Builder
	private EmployeeCreateRequest(String name, String phone, EmployeeScheduleRequest schedule, Integer centerId) {
		this.name = name;
		this.phone = phone;
		this.schedule = schedule;
		this.centerId = centerId;
	}

	public EmployeeCreateServiceRequest toServiceRequest() {
		return EmployeeCreateServiceRequest.builder()
			.name(this.name)
			.phone(this.phone)
			.workingDay(LocalDate.of(this.schedule.getYear(), this.schedule.getMonth(), this.schedule.getDay()))
			.centerId(this.centerId)
			.build();
	}
}
