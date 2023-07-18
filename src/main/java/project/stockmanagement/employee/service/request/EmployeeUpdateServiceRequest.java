package project.stockmanagement.employee.service.request;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.stockmanagement.employee.dao.domain.Employee;
import project.stockmanagement.employee.dao.domain.EmployeeStatus;

@Getter
@NoArgsConstructor
public class EmployeeUpdateServiceRequest {

	private String name;
	private String phone;
	private EmployeeStatus employeeStatus;
	private Integer itemPackagingCount;
	private LocalDate workingDay;
	private Integer centerId;

	@Builder
	private EmployeeUpdateServiceRequest(String name, String phone, EmployeeStatus employeeStatus,
		Integer itemPackagingCount, LocalDate workingDay, Integer centerId) {
		this.name = name;
		this.phone = phone;
		this.employeeStatus = employeeStatus;
		this.itemPackagingCount = itemPackagingCount;
		this.workingDay = workingDay;
		this.centerId = centerId;
	}

	public Employee toDomain() {
		return Employee.builder()
			.name(this.name)
			.phone(this.phone)
			.employeeStatus(this.employeeStatus)
			.itemPackagingCount(this.itemPackagingCount)
			.workingDay(this.workingDay)
			.centerId(this.centerId)
			.build();
	}
}
