package project.stockmanagement.employee.dao.domain;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Employee {

	private Long id;
	private final String name;
	private final String phone;
	private final EmployeeStatus employeeStatus;
	private final Integer itemPackagingCount;
	private final LocalDate workingDay;
	private final Integer centerId;

	@Builder
	private Employee(Long id, String name, String phone, EmployeeStatus employeeStatus, Integer itemPackagingCount,
		LocalDate workingDay, Integer centerId) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.employeeStatus = employeeStatus;
		this.itemPackagingCount = itemPackagingCount;
		this.workingDay = workingDay;
		this.centerId = centerId;
	}

	public Employee toCreateEntity(EmployeeStatus employeeStatus, Integer itemPackagingCount) {
		return Employee.builder()
			.name(this.name)
			.phone(this.phone)
			.centerId(this.centerId)
			.workingDay(this.workingDay)
			.employeeStatus(employeeStatus)
			.itemPackagingCount(itemPackagingCount)
			.build();
	}

	public Employee increaseItemPackingCount() {
		return Employee.builder()
			.name(this.name)
			.phone(this.phone)
			.centerId(this.centerId)
			.workingDay(this.workingDay)
			.employeeStatus(this.employeeStatus)
			.itemPackagingCount(this.itemPackagingCount + 1)
			.build();
	}
}
