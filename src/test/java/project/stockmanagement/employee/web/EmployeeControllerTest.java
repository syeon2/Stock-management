package project.stockmanagement.employee.web;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import project.stockmanagement.employee.dao.domain.EmployeeStatus;
import project.stockmanagement.employee.service.EmployeeService;
import project.stockmanagement.employee.service.response.EmployeeFindResponse;
import project.stockmanagement.employee.web.request.EmployeeCreateRequest;
import project.stockmanagement.employee.web.request.EmployeeScheduleRequest;

@WebMvcTest(controllers = EmployeeController.class)
class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private EmployeeService employeeService;

	@Test
	@DisplayName("근로자 생성 요청을 테스트합니다.")
	void createEmployee() throws Exception {
		// given
		EmployeeScheduleRequest schedule = new EmployeeScheduleRequest(2021, 12, 12);
		EmployeeCreateRequest request = EmployeeCreateRequest.builder()
			.name("memberA")
			.phone("12345678910")
			.schedule(schedule)
			.centerId(1)
			.build();

		// when  // then
		mockMvc.perform(
				post("/api/v1/employee")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("근로자 생성 시 근로자 이름은 공백을 허용하지 않습니다.")
	void createEmployeeWithoutName() throws Exception {
		// given
		EmployeeScheduleRequest schedule = new EmployeeScheduleRequest(2021, 12, 12);
		EmployeeCreateRequest request = EmployeeCreateRequest.builder()
			.name("  ")
			.phone("12345678910")
			.schedule(schedule)
			.centerId(1)
			.build();

		// when  // then
		mockMvc.perform(
				post("/api/v1/employee")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.data").isEmpty())
			.andExpect(jsonPath("$.message").value("근로자 이름은 공백을 허용하지 않습니다."));
	}

	@Test
	@DisplayName("근로자 생성 시 전화번호는 10 ~ 11자리의 숫자만 입력 가능합니다.")
	void createEmployeeOverRangeOfPhoneNumber() throws Exception {
		// given
		EmployeeScheduleRequest schedule = new EmployeeScheduleRequest(2021, 12, 12);
		EmployeeCreateRequest request = EmployeeCreateRequest.builder()
			.name("memberA")
			.phone("123456789101112")
			.schedule(schedule)
			.centerId(1)
			.build();

		// when  // then
		mockMvc.perform(
				post("/api/v1/employee")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.data").isEmpty())
			.andExpect(jsonPath("$.message").value("10 ~ 11자리의 숫자만 입력 가능합니다."));
	}

	@Test
	@DisplayName("근로자 생성 시 센터 아이디는 필수 입니다.")
	void createEmployeeWithoutCenterId() throws Exception {
		// given
		EmployeeScheduleRequest schedule = new EmployeeScheduleRequest(2021, 12, 12);
		EmployeeCreateRequest request = EmployeeCreateRequest.builder()
			.name("memberA")
			.phone("12345678910")
			.schedule(schedule)
			.build();

		// when  // then
		mockMvc.perform(
				post("/api/v1/employee")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.data").isEmpty())
			.andExpect(jsonPath("$.message").value("센터 아이디는 필수 입니다."));
	}

	@Test
	@DisplayName("근로자 생성 시 연도는 1959 - 2100 범위에서 입력 가능합니다.")
	void createEmployeeOverRangeYear() throws Exception {
		// given
		EmployeeScheduleRequest schedule = new EmployeeScheduleRequest(2111, 12, 12);
		EmployeeCreateRequest request = EmployeeCreateRequest.builder()
			.name("memberA")
			.phone("12345678910")
			.schedule(schedule)
			.centerId(1)
			.build();

		// when  // then
		mockMvc.perform(
				post("/api/v1/employee")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.data").isEmpty())
			.andExpect(jsonPath("$.message").value("연도는 1950 - 2100까지 입력 가능합니다."));
	}

	@Test
	@DisplayName("근로자 생성 시 월은 1 - 12까지 입력 가능합니다.")
	void createEmployeeOverRangeMoneh() throws Exception {
		// given
		EmployeeScheduleRequest schedule = new EmployeeScheduleRequest(2011, 14, 12);
		EmployeeCreateRequest request = EmployeeCreateRequest.builder()
			.name("memberA")
			.phone("12345678910")
			.schedule(schedule)
			.centerId(1)
			.build();

		// when  // then
		mockMvc.perform(
				post("/api/v1/employee")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.data").isEmpty())
			.andExpect(jsonPath("$.message").value("월은 1 - 12까지 입력 가능합니다."));
	}

	@Test
	@DisplayName("근로자 생성 시 일은 1 - 31까지 입력 가능합니다.")
	void createEmployeeOverRangeDay() throws Exception {
		// given
		EmployeeScheduleRequest schedule = new EmployeeScheduleRequest(2011, 11, 32);
		EmployeeCreateRequest request = EmployeeCreateRequest.builder()
			.name("memberA")
			.phone("12345678910")
			.schedule(schedule)
			.centerId(1)
			.build();

		// when  // then
		mockMvc.perform(
				post("/api/v1/employee")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.data").isEmpty())
			.andExpect(jsonPath("$.message").value("일은 1 - 31까지 입력 가능합니다."));
	}

	@Test
	@DisplayName("근로자 아이디로 근로자를 조회합니다.")
	void findEmployee() throws Exception {
		// given
		long memberId = 1;
		EmployeeFindResponse employeeResponse = EmployeeFindResponse.builder()
			.id(memberId)
			.name("memberA")
			.phone("0000000000")
			.workingDay(LocalDate.now())
			.employeeStatus(EmployeeStatus.WAITING)
			.itemPackagingCount(0)
			.centerId(1)
			.build();

		when(employeeService.findEmployee(1L)).thenReturn(employeeResponse);

		// when  // then
		mockMvc.perform(
				get("/api/v1/employee/" + memberId)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.id").value(memberId))
			.andExpect(jsonPath("$.message").isEmpty());
	}

	@Test
	@DisplayName("센터 아이디로 근로자를 조회합니다.")
	void findEmployeeByCenter() throws Exception {
		// given
		long memberId = 1;

		when(employeeService.findEmployeesByCenterId(1)).thenReturn(List.of());

		// when  // then
		mockMvc.perform(
				get("/api/v1/employee/center/" + memberId)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isArray())
			.andExpect(jsonPath("$.message").isEmpty());
	}
}
