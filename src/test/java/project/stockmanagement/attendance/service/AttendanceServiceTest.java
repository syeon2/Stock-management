package project.stockmanagement.attendance.service;

import static org.assertj.core.api.Assertions.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import project.stockmanagement.attendance.dao.AttendanceRepository;
import project.stockmanagement.attendance.dao.domain.Attendance;
import project.stockmanagement.attendance.dao.domain.WorkStatus;
import project.stockmanagement.attendance.service.request.AttendanceCreateServiceRequest;
import project.stockmanagement.attendance.service.response.AttendanceResponse;

@ActiveProfiles("test")
@SpringBootTest
class AttendanceServiceTest {

	@Autowired
	private AttendanceService attendanceService;

	@Autowired
	private AttendanceRepository attendanceRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@AfterEach
	public void after() {
		jdbcTemplate.execute("delete from attendance");
		jdbcTemplate.execute("alter table attendance auto_increment = 1");
	}

	@Test
	@DisplayName("근로자가 출근 관리 내역을 생성합니다.")
	void createAttendanceEmployee() {
		// given
		AttendanceCreateServiceRequest request = AttendanceCreateServiceRequest.builder()
			.workStatus(WorkStatus.DAY)
			.employeeId(1L)
			.centerId(1)
			.build();

		// when
		AttendanceResponse attendanceResponse = attendanceService.createAttendance(request);

		// then
		assertThat(attendanceResponse)
			.extracting("id", "workStatus", "employeeId", "centerId")
			.contains(1L, WorkStatus.DAY, 1L, 1);
	}

	@Test
	@DisplayName("근로자 아이디로 근로 내역을 조회합니다.")
	void findAttendanceByEmployeeId() {
		// given
		long employeeId = 1L;

		Attendance attendance = createAttendance(WorkStatus.DAY, employeeId, 1);
		Timestamp currentDateTime = getCurrentDateTime();
		attendanceRepository.save(attendance, currentDateTime);

		// when
		List<AttendanceResponse> findAttendance = attendanceService.findAttendance(employeeId, 10);

		// then
		assertThat(findAttendance).hasSize(1)
			.extracting("id", "workStatus", "employeeId", "centerId", "createdAt", "updatedAt")
			.containsExactlyInAnyOrder(
				tuple(1L, WorkStatus.DAY, 1L, 1, currentDateTime, currentDateTime)
			);
	}

	@Test
	@DisplayName("센터 아이디로 근로 내역을 조회합니다.")
	void findAttendanceByCenterId() {
		// given
		int centerId = 2;

		Attendance attendance = createAttendance(WorkStatus.DAY, 1L, centerId);
		Timestamp currentDateTime = getCurrentDateTime();
		attendanceRepository.save(attendance, currentDateTime);

		// when
		List<AttendanceResponse> findAttendance = attendanceService.findAttendance(centerId);

		// then
		assertThat(findAttendance).hasSize(1)
			.extracting("id", "workStatus", "employeeId", "centerId", "createdAt", "updatedAt")
			.containsExactlyInAnyOrder(
				tuple(1L, WorkStatus.DAY, 1L, 2, currentDateTime, currentDateTime)
			);
	}

	@Test
	@DisplayName("근무 형태 값을 수정합니다.")
	void changeWorkStatusAttendance() {
		// given
		WorkStatus originStatus = WorkStatus.DAY;

		Attendance attendance = createAttendance(originStatus, 1L, 1);
		Timestamp currentDateTime = getCurrentDateTime();
		Attendance savedattendance = attendanceRepository.save(attendance, currentDateTime);

		Long id = savedattendance.getId();

		Attendance findOriginAttendance = attendanceRepository.findById(id);
		assertThat(savedattendance)
			.extracting("id", "workStatus", "employeeId", "centerId", "createdAt", "updatedAt")
			.contains(1L, originStatus, 1L, 1, currentDateTime, currentDateTime);

		// when
		WorkStatus changeWorkStatus = WorkStatus.NIGHT;
		attendanceService.changeWorkStatusAttendance(id, changeWorkStatus);

		// then
		Attendance findChangedAttendance = attendanceRepository.findById(id);
		assertThat(findChangedAttendance)
			.extracting("id", "workStatus", "employeeId", "centerId", "createdAt", "updatedAt")
			.contains(1L, changeWorkStatus, 1L, 1, currentDateTime, currentDateTime);
	}

	private Timestamp getCurrentDateTime() {
		return Timestamp.valueOf(
			LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
	}

	private Attendance createAttendance(WorkStatus workStatus, long employeeId, int centerId) {
		return Attendance.builder()
			.workStatus(workStatus)
			.employeeId(employeeId)
			.centerId(centerId)
			.build();
	}
}
