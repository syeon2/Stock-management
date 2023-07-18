package project.stockmanagement.employee.service.response;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import project.stockmanagement.employee.dao.domain.Employee;
import project.stockmanagement.employee.dao.domain.EmployeeStatus;

@Getter
public class EmployeeFindResponse {
	private final Long id;
	private final String name;
	private final String phone;
	private final LocalDate workingDay;
	private final EmployeeStatus employeeStatus;
	private final Integer itemPackagingCount;
	private final Integer centerId;

	@Builder
	public EmployeeFindResponse(Long id, String name, String phone, LocalDate workingDay, EmployeeStatus employeeStatus,
		Integer itemPackagingCount, Integer centerId) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.workingDay = workingDay;
		this.employeeStatus = employeeStatus;
		this.itemPackagingCount = itemPackagingCount;
		this.centerId = centerId;
	}

	public static EmployeeFindResponse of(Employee employee) {
		return EmployeeFindResponse.builder()
			.id(employee.getId())
			.name(employee.getName())
			.phone(employee.getPhone())
			.workingDay(employee.getWorkingDay())
			.employeeStatus(employee.getEmployeeStatus())
			.itemPackagingCount(employee.getItemPackagingCount())
			.centerId(employee.getCenterId())
			.build();
	}
}
