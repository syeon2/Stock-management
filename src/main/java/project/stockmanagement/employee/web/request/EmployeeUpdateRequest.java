package project.stockmanagement.employee.web.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.stockmanagement.employee.dao.domain.Employee;
import project.stockmanagement.employee.dao.domain.EmployeeStatus;

@Getter
@NoArgsConstructor
public class EmployeeUpdateRequest {

	private String name;
	private String phone;
	private EmployeeStatus employeeStatus;
	private Integer itemPackagingCount;
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

	public Employee toUpdateEntity() {
		return Employee.builder()
			.name(this.name)
			.phone(this.phone)
			.employeeStatus(this.employeeStatus)
			.itemPackagingCount(this.itemPackagingCount)
			.centerId(this.centerId)
			.build();
	}
}
