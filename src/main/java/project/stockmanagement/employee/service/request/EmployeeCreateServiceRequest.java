package project.stockmanagement.employee.service.request;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class EmployeeCreateServiceRequest {

	private final String name;
	private final String phone;
	private final LocalDate workingDay;
	private final Integer centerId;

	@Builder
	private EmployeeCreateServiceRequest(String name, String phone, LocalDate workingDay, Integer centerId) {
		this.name = name;
		this.phone = phone;
		this.workingDay = workingDay;
		this.centerId = centerId;
	}
}
