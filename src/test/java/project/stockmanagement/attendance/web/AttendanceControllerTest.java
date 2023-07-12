package project.stockmanagement.attendance.web;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import project.stockmanagement.attendance.dao.domain.WorkStatus;
import project.stockmanagement.attendance.service.AttendanceService;
import project.stockmanagement.attendance.service.response.AttendanceResponse;
import project.stockmanagement.attendance.web.request.AttendanceCreateRequest;
import project.stockmanagement.attendance.web.request.AttendanceUpdateWorkStatusRequest;

@WebMvcTest(controllers = AttendanceController.class)
class AttendanceControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private AttendanceService attendanceService;

	@Test
	@DisplayName("출근 관리 내역을 생성합니다.")
	void createAttendanceOfEmployee() throws Exception {
		// given
		AttendanceCreateRequest request = AttendanceCreateRequest.builder()
			.workStatus(WorkStatus.DAY)
			.employeeId(1L)
			.centerId(1)
			.build();

		// when  // then
		mockMvc.perform(
				post("/api/v1/attendance")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("출근 관리 내역에 근무 형태는 필수 값입니다.")
	void createAttendanceOfEmployeeWithoutWorkStatus() throws Exception {
		// given
		AttendanceCreateRequest request = AttendanceCreateRequest.builder()
			.employeeId(1L)
			.centerId(1)
			.build();

		// when  // then
		mockMvc.perform(
				post("/api/v1/attendance")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.data").isEmpty())
			.andExpect(jsonPath("$.message").value("근무 형태는 필수 값 입니다."));
	}

	@Test
	@DisplayName("출근 관리 내역에 근로자 아이디는 필수 값입니다.")
	void createAttendanceOfEmployeeWithoutEmployeeId() throws Exception {
		// given
		AttendanceCreateRequest request = AttendanceCreateRequest.builder()
			.workStatus(WorkStatus.DAY)
			.centerId(1)
			.build();

		// when  // then
		mockMvc.perform(
				post("/api/v1/attendance")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.data").isEmpty())
			.andExpect(jsonPath("$.message").value("근로자 아이디는 필수 값 입니다."));
	}

	@Test
	@DisplayName("출근 관리 내역에 센터 아이디는 필수 값입니다.")
	void createAttendanceOfEmployeeWithoutCenterId() throws Exception {
		// given
		AttendanceCreateRequest request = AttendanceCreateRequest.builder()
			.workStatus(WorkStatus.DAY)
			.employeeId(1L)
			.build();

		// when  // then
		mockMvc.perform(
				post("/api/v1/attendance")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.data").isEmpty())
			.andExpect(jsonPath("$.message").value("센터 아이디는 필수 값 입니다."));
	}

	@Test
	@DisplayName("근로자 아이디로 전체 근로 내역을 조회합니다.")
	void getAttendanceUsingEmployeeId() throws Exception {
		// given
		List<AttendanceResponse> attendances = List.of();
		long employeeId = 1L;
		when(attendanceService.findAttendance(employeeId)).thenReturn(attendances);

		// when  // then
		mockMvc.perform(
				get("/api/v1/attendance/employee/" + employeeId)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isArray())
			.andExpect(jsonPath("$.message").isEmpty());
	}

	@Test
	@DisplayName("센터 아이디로 전체 근로 내역을 조회합니다.")
	void getAttendanceUsingCenterId() throws Exception {
		// given
		List<AttendanceResponse> attendances = List.of();
		int centerId = 1;
		when(attendanceService.findAttendance(centerId)).thenReturn(attendances);

		// when  // then
		mockMvc.perform(
				get("/api/v1/attendance/employee/" + centerId)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isArray())
			.andExpect(jsonPath("$.message").isEmpty());
	}

	@Test
	@DisplayName("아이디를 받아 근로 내역의 근무 형태를 수정합니다.")
	void changeWorkStatusOfAttendanceUsingId() throws Exception {
		// given
		AttendanceUpdateWorkStatusRequest request = AttendanceUpdateWorkStatusRequest.builder()
			.workStatus(WorkStatus.DAY)
			.build();

		long attendanceId = 1;
		when(attendanceService.changeWorkStatusAttendance(attendanceId, request.getWorkStatus()))
			.thenReturn(attendanceId);

		// when  // then
		mockMvc.perform(
				post("/api/v1/attendance/" + attendanceId)
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").value(attendanceId))
			.andExpect(jsonPath("$.message").isEmpty());
	}
}
