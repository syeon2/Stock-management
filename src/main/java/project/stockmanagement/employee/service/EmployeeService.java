package project.stockmanagement.employee.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.stockmanagement.employee.dao.EmployeeRepository;
import project.stockmanagement.employee.dao.domain.Employee;
import project.stockmanagement.employee.service.request.EmployeeCreateServiceRequest;
import project.stockmanagement.employee.service.response.EmployeeResponse;

@Service
@RequiredArgsConstructor
public class EmployeeService {

	private final EmployeeRepository employeeRepository;

	public EmployeeResponse createEmployee(EmployeeCreateServiceRequest request) {
		Employee employee = request.toDomain();
		Employee savedEmployee = employeeRepository.save(employee);

		return EmployeeResponse.of(savedEmployee);
	}

	public EmployeeResponse findEmployee(Long id) {
		Employee employee = employeeRepository.findById(id);

		return EmployeeResponse.of(employee);
	}

	public List<EmployeeResponse> findEmployeesByCenterId(Integer centerId) {
		List<Employee> employees = employeeRepository.findByCenterId(centerId);

		return employees.stream()
			.map(EmployeeResponse::of)
			.collect(Collectors.toList());
	}

	public Long updateEmployee(Long id, Employee updateEmployee) {
		return employeeRepository.update(id, updateEmployee);
	}
}
