package project.stockmanagement.employee.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import project.stockmanagement.employee.dao.EmployeeRepository;
import project.stockmanagement.employee.dao.domain.Employee;
import project.stockmanagement.employee.dao.domain.EmployeeStatus;
import project.stockmanagement.employee.service.request.EmployeeCreateServiceRequest;
import project.stockmanagement.employee.service.request.EmployeeUpdateServiceRequest;
import project.stockmanagement.employee.service.response.EmployeeFindResponse;
import project.stockmanagement.employee.service.response.EmployeeResponse;

@ActiveProfiles("test")
@SpringBootTest
class EmployeeServiceTest {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	public void before() {
		jdbcTemplate.execute("delete from employee");
		jdbcTemplate.execute("alter table employee auto_increment = 1");
	}

	@Test
	@DisplayName("근로자를 생성합니다.")
	void createEmployee() {
		// given
		LocalDate workingDay = LocalDate.now();
		EmployeeCreateServiceRequest request = EmployeeCreateServiceRequest.builder()
			.name("memberA")
			.phone("010-0000-0000")
			.workingDay(workingDay)
			.centerId(1)
			.build();

		// when
		EmployeeResponse employeeResponse = employeeService.createEmployee(request);

		// then
		assertThat(employeeResponse)
			.extracting("id", "name", "phone", "workingDay", "centerId")
			.contains(1L, "memberA", "010-0000-0000", workingDay, 1);
	}

	@Test
	@DisplayName("근로자를 조회합니다.")
	void findEmployee() {
		// given
		LocalDate workingDay = LocalDate.now();
		Employee employee = createEmployee("memberA", "000-000-000", EmployeeStatus.WAITING, 0, workingDay, 1);
		employeeRepository.save(employee);

		// when
		EmployeeFindResponse employeeResponse = employeeService.findEmployee(1L);

		// then
		assertThat(employeeResponse)
			.extracting("id", "name", "phone", "workingDay", "employeeStatus", "itemPackagingCount", "centerId")
			.contains(1L, "memberA", "000-000-000", workingDay, EmployeeStatus.WAITING, 0, 1);
	}

	@Test
	@DisplayName("센터 아이디를 사용해 근로자를 조회합니다.")
	void findEmployeesByCenterId() {
		// given
		LocalDate workingDay = LocalDate.now();
		int centerId = 1;
		Employee employee1 = createEmployee("memberA", "000-000-000", EmployeeStatus.WAITING, 0, workingDay, centerId);
		Employee employee2 = createEmployee("memberB", "000-000-000", EmployeeStatus.WAITING, 0, workingDay, centerId);
		Employee employee3 = createEmployee("memberC", "000-000-000", EmployeeStatus.WAITING, 0, workingDay, centerId);

		employeeRepository.save(employee1);
		employeeRepository.save(employee2);
		employeeRepository.save(employee3);

		// when
		List<EmployeeResponse> employeesResponse = employeeService.findEmployeesByCenterId(centerId);

		// then
		assertThat(employeesResponse).hasSize(3)
			.extracting("id", "name", "phone", "workingDay", "centerId")
			.containsExactlyInAnyOrder(
				tuple(1L, "memberA", "000-000-000", workingDay, centerId),
				tuple(2L, "memberB", "000-000-000", workingDay, centerId),
				tuple(3L, "memberC", "000-000-000", workingDay, centerId)
			);
	}

	@Test
	@DisplayName("근로자의 정보를 업데이트 합니다.")
	void updateEmployee() {
		// given
		LocalDate workingDay = LocalDate.now();
		Employee employee = createEmployee("memberA", "000-000-000", EmployeeStatus.WAITING, 0, workingDay, 1);
		Employee savedEmployee = employeeRepository.save(employee);

		Long id = savedEmployee.getId();
		LocalDate updateDay = LocalDate.now();
		EmployeeUpdateServiceRequest updateServiceRequest = EmployeeUpdateServiceRequest.builder()
			.name("memberB")
			.phone("000-000-000")
			.employeeStatus(EmployeeStatus.WORKING)
			.itemPackagingCount(0)
			.workingDay(workingDay)
			.centerId(2)
			.build();

		// when
		Long updateEmployeeId = employeeService.updateEmployee(id, updateServiceRequest);

		// then
		assertThat(updateEmployeeId).isEqualTo(id);

		Employee findEmployee = employeeRepository.findById(id);
		assertThat(findEmployee)
			.extracting("id", "name", "phone", "workingDay", "centerId")
			.contains(1L, "memberB", "000-000-000", updateDay, 2);
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
