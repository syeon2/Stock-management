package project.stockmanagement.employee.dao.mybatis;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import project.stockmanagement.employee.dao.EmployeeRepository;
import project.stockmanagement.employee.dao.domain.Employee;
import project.stockmanagement.employee.dao.domain.EmployeeStatus;

@ActiveProfiles("test")
@SpringBootTest
class MyBatisEmployeeRepositoryTest {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@AfterEach
	void after() {
		jdbcTemplate.execute("delete from employee");
		jdbcTemplate.execute("alter table employee auto_increment = 1");
	}

	@Test
	@DisplayName("근로자를 생성합니다.")
	public void saveEmployee() {
		// given
		LocalDate workingDay = LocalDate.now();
		Employee employee = createEmployee("Suyeon", "0000000000", EmployeeStatus.WAITING, 0, workingDay, 1);

		// when
		Employee savedEmployee = employeeRepository.save(employee);

		// then
		assertThat(savedEmployee)
			.extracting("id", "name", "phone", "employeeStatus", "itemPackagingCount", "workingDay", "centerId")
			.contains(1, "Suyeon", "0000000000", EmployeeStatus.WAITING, 0, workingDay, 1);
	}

	@Test
	@DisplayName("아이디를 사용하여 근로자를 조회합니다.")
	void findEmployeeById() {
		// given
		LocalDate workingDay = LocalDate.now();
		Employee employee = createEmployee("memberA", "0000000000", EmployeeStatus.WAITING, 0, workingDay, 1);
		employeeRepository.save(employee);

		// when
		Employee findEmployee = employeeRepository.findById(1L);

		// then
		assertThat(findEmployee)
			.extracting("id", "name", "phone", "employeeStatus", "itemPackagingCount", "workingDay", "centerId")
			.contains(1L, "memberA", "0000000000", EmployeeStatus.WAITING, 0, workingDay, 1);
	}

	@Test
	@DisplayName("센터 아이디를 사용하여 근로자들을 조회합니다.")
	void findEmployeeByCenterId() {
		// given
		LocalDate workingDay = LocalDate.now();
		Employee employee1 = createEmployee("memberA", "0000000000", EmployeeStatus.WAITING, 0, workingDay, 1);
		Employee employee2 = createEmployee("memberB", "0000000000", EmployeeStatus.WAITING, 0, workingDay, 1);
		Employee employee3 = createEmployee("memberC", "0000000000", EmployeeStatus.WAITING, 0, workingDay, 1);

		employeeRepository.save(employee1);
		employeeRepository.save(employee2);
		employeeRepository.save(employee3);

		// when
		List<Employee> employees = employeeRepository.findByCenterId(1);

		// then
		assertThat(employees).hasSize(3)
			.extracting("id", "name", "phone", "employeeStatus", "itemPackagingCount", "workingDay", "centerId")
			.containsExactlyInAnyOrder(
				tuple(1L, "memberA", "0000000000", EmployeeStatus.WAITING, 0, workingDay, 1),
				tuple(2L, "memberB", "0000000000", EmployeeStatus.WAITING, 0, workingDay, 1),
				tuple(3L, "memberC", "0000000000", EmployeeStatus.WAITING, 0, workingDay, 1)
			);
	}

	@Test
	@DisplayName("근로자의 정보를 수정합니다.")
	void updateEmployee() {
		// given
		LocalDate workingDay = LocalDate.now();
		Employee employee = createEmployee("memberA", "0000000000", EmployeeStatus.WAITING, 0, workingDay, 1);
		Employee savedEmployee = employeeRepository.save(employee);
		Long id = savedEmployee.getId();

		String changedName = "memberB";
		Employee updateEmployee = createEmployee(changedName, "0000000000", EmployeeStatus.WAITING, 0, workingDay, 1);

		// when
		Long update = employeeRepository.update(id, updateEmployee);

		// then
		Employee findEmployee = employeeRepository.findById(id);
		assertThat(findEmployee)
			.extracting("id", "name", "phone", "employeeStatus", "itemPackagingCount", "workingDay", "centerId")
			.contains(1L, changedName, "0000000000", EmployeeStatus.WAITING, 0, workingDay, 1);
	}

	@Test
	@DisplayName("근로자 정보를 삭제합니다.")
	void delete() {
		// given
		LocalDate workingDay = LocalDate.now();
		Employee employee = createEmployee("memberA", "0000000000", EmployeeStatus.WAITING, 0, workingDay, 1);
		employeeRepository.save(employee);

		Employee findEmployee = employeeRepository.findById(1L);
		assertThat(findEmployee.getWorkingDay()).isEqualTo(workingDay);

		// when
		employeeRepository.delete(1L);

		// then
		assertThatThrownBy(() -> employeeRepository.findById(1L))
			.isInstanceOf(NoSuchElementException.class);
	}

	private Employee createEmployee(String name, String phone, EmployeeStatus employeeStatus,
		int itemPackagingCount, LocalDate workingDay, int centerId) {
		return Employee.builder()
			.name(name)
			.phone(phone)
			.employeeStatus(employeeStatus)
			.itemPackagingCount(itemPackagingCount)
			.workingDay(workingDay)
			.centerId(centerId)
			.build();
	}
}
