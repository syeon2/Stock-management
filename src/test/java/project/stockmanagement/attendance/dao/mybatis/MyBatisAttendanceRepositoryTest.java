package project.stockmanagement.attendance.dao.mybatis;

import static org.assertj.core.api.AssertionsForInterfaceTypes.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

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

@ActiveProfiles("test")
@SpringBootTest
class MyBatisAttendanceRepositoryTest {

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
	@DisplayName("출근 관리를 저장합니다.")
	void saveAttendance() {
		// given
		Timestamp currentDateTime = getCurrentDateTime();
		Attendance attendance = createdAttendance(WorkStatus.DAY, 1L, 1);

		// when
		Attendance savedAttendance = attendanceRepository.save(attendance, currentDateTime);

		// then
		assertThat(savedAttendance)
			.extracting("id", "workStatus", "employeeId", "centerId", "createdAt", "updatedAt")
			.contains(1, WorkStatus.DAY, 1L, 1, currentDateTime, currentDateTime);
	}

	@Test
	@DisplayName("아이디를 통해 근로 관리를 조회합니다.")
	void findById() {
		// given
		Attendance attendance = createdAttendance(WorkStatus.DAY, 1L, 1);
		Timestamp currentDateTime = getCurrentDateTime();
		attendanceRepository.save(attendance, currentDateTime);

		// when
		Attendance findAttendance = attendanceRepository.findById(1L);

		// then
		assertThat(findAttendance)
			.extracting("id", "workStatus", "employeeId", "centerId", "createdAt", "updatedAt")
			.contains(1L, WorkStatus.DAY, 1L, 1, currentDateTime, currentDateTime);
	}

	@Test
	@DisplayName("특정 근로자 아이디를 통해 근로 관리를 조회합니다.")
	void findByEmployeeId() {
		// given
		Long employeeId = 1L;
		Timestamp currentDateTime = getCurrentDateTime();
		Attendance attendance1 = createdAttendance(WorkStatus.DAY, employeeId, 1);
		Attendance attendance2 = createdAttendance(WorkStatus.NIGHT, employeeId, 1);
		Attendance attendance3 = createdAttendance(WorkStatus.HOLIDAY_DAY, employeeId, 1);

		attendanceRepository.save(attendance1, currentDateTime);
		attendanceRepository.save(attendance2, currentDateTime);
		attendanceRepository.save(attendance3, currentDateTime);

		// when
		List<Attendance> attendances = attendanceRepository.findByEmployeeId(employeeId, 1);

		// then
		assertThat(attendances).hasSize(3)
			.extracting("id", "workStatus", "employeeId", "centerId", "createdAt", "updatedAt")
			.containsExactlyInAnyOrder(
				tuple(1L, WorkStatus.DAY, employeeId, 1, currentDateTime, currentDateTime),
				tuple(2L, WorkStatus.NIGHT, employeeId, 1, currentDateTime, currentDateTime),
				tuple(3L, WorkStatus.HOLIDAY_DAY, employeeId, 1, currentDateTime, currentDateTime)
			);
	}

	@Test
	@DisplayName("특정 센터 아이디를 통해 근로 관리를 조회합니다.")
	void findByCenterId() {
		// given
		Integer centerId = 1;
		Timestamp currentDateTime = getCurrentDateTime();
		Attendance attendance1 = createdAttendance(WorkStatus.DAY, 1L, centerId);
		Attendance attendance2 = createdAttendance(WorkStatus.NIGHT, 2L, centerId);
		Attendance attendance3 = createdAttendance(WorkStatus.HOLIDAY_DAY, 3L, centerId);

		attendanceRepository.save(attendance1, currentDateTime);
		attendanceRepository.save(attendance2, currentDateTime);
		attendanceRepository.save(attendance3, currentDateTime);

		// when
		List<Attendance> attendances = attendanceRepository.findByCenterId(centerId);

		// then
		assertThat(attendances).hasSize(3)
			.extracting("id", "workStatus", "employeeId", "centerId", "createdAt", "updatedAt")
			.containsExactlyInAnyOrder(
				tuple(1L, WorkStatus.DAY, 1L, centerId, currentDateTime, currentDateTime),
				tuple(2L, WorkStatus.NIGHT, 2L, centerId, currentDateTime, currentDateTime),
				tuple(3L, WorkStatus.HOLIDAY_DAY, 3L, centerId, currentDateTime, currentDateTime)
			);
	}

	@Test
	@DisplayName("특정 근로 관리 데이터를 수정합니다.")
	void update() {
		// given
		Attendance attendance = createdAttendance(WorkStatus.DAY, 1L, 1);
		Timestamp currentDateTime = getCurrentDateTime();
		Attendance savedAttendance = attendanceRepository.save(attendance, currentDateTime);

		// when
		Timestamp updateDateTime = getCurrentDateTime();
		Attendance updateAttendance = createdAttendance(WorkStatus.NIGHT, 1L, 1);
		Long updatedId = attendanceRepository.update(1L, updateAttendance, updateDateTime);

		// then
		assertThat(updatedId).isEqualTo(1L);

		Attendance findAttendance = attendanceRepository.findById(1L);
		assertThat(findAttendance)
			.extracting("id", "workStatus", "employeeId", "centerId", "createdAt", "updatedAt")
			.contains(1L, WorkStatus.NIGHT, 1L, 1, currentDateTime, updateDateTime);
	}

	@Test
	@DisplayName("근로 관리 데이터를 삭제합니다.")
	void delete() {
		// given
		Attendance attendance = createdAttendance(WorkStatus.DAY, 1L, 1);
		Timestamp currentDateTime = getCurrentDateTime();
		attendanceRepository.save(attendance, currentDateTime);

		// when
		attendanceRepository.delete(1L);

		// then
		assertThatThrownBy(() -> attendanceRepository.findById(1L))
			.isInstanceOf(NoSuchElementException.class)
			.hasMessage("근로 관리 내역이 존재하지 않습니다.");
	}

	private Attendance createdAttendance(WorkStatus workStatus, Long employeeId, Integer centerId) {
		return Attendance.builder()
			.workStatus(workStatus)
			.employeeId(employeeId)
			.centerId(centerId)
			.build();
	}

	private Timestamp getCurrentDateTime() {
		return Timestamp.valueOf(
			LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
	}
}
