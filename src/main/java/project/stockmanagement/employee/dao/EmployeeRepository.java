package project.stockmanagement.employee.dao;

import java.util.List;

import project.stockmanagement.employee.dao.domain.Employee;

public interface EmployeeRepository {

	Employee save(Employee employee);

	Employee findById(Long id);

	List<Employee> findByCenterId(Integer centerId);

	Long update(Long id, Employee updateEmployee);

	void delete(Long id);
}
