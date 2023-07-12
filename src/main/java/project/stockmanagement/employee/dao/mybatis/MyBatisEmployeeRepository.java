package project.stockmanagement.employee.dao.mybatis;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.stockmanagement.common.basewrapper.CombinedEntity;
import project.stockmanagement.employee.dao.EmployeeRepository;
import project.stockmanagement.employee.dao.domain.Employee;
import project.stockmanagement.employee.dao.domain.EmployeeStatus;

@Repository
@RequiredArgsConstructor
public class MyBatisEmployeeRepository implements EmployeeRepository {

	private final EmployeeMapper employeeMapper;

	@Override
	public Employee save(Employee employee) {
		Employee createEntity = employee.toCreateEntity(EmployeeStatus.WAITING, 0);
		CombinedEntity<Employee> createEmployee = CombinedEntity.toCreateData(createEntity);

		employeeMapper.save(createEmployee);

		return createEmployee.getEntity();
	}

	@Override
	public Employee findById(Long id) {
		Optional<Employee> findEmployeeOptional = employeeMapper.findById(id);

		return findEmployeeOptional.orElseThrow(
			() -> new NoSuchElementException("아이디에 해당하는 근로자가 없습니다."));
	}

	@Override
	public List<Employee> findByCenterId(Integer centerId) {
		return employeeMapper.findByCenterId(centerId);
	}

	@Override
	public Long update(Long id, Employee updateEmployee) {
		Employee employee = findById(id).toUpdateEntity(updateEmployee);
		CombinedEntity<Employee> updateData = CombinedEntity.toUpdateData(employee);
		employeeMapper.update(id, updateData);

		return updateData.getEntity().getId();
	}

	@Override
	public void delete(Long id) {
		employeeMapper.delete(id);
	}
}
