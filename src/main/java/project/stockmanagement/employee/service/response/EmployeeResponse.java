package project.stockmanagement.employee.service.response;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import project.stockmanagement.employee.dao.domain.Employee;

@Getter
public class EmployeeResponse {

	private final Long id;
	private final String name;
	private final String phone;
	private final LocalDate workingDay;
	private final Integer centerId;

	@Builder
	private EmployeeResponse(Long id, String name, String phone, LocalDate workingDay, Integer centerId) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.workingDay = workingDay;
		this.centerId = centerId;
	}

	public static EmployeeResponse of(Employee employee) {
		return EmployeeResponse.builder()
			.id(employee.getId())
			.name(employee.getName())
			.phone(employee.getPhone())
			.workingDay(employee.getWorkingDay())
			.centerId(employee.getCenterId())
			.build();
	}
}
