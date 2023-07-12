package project.stockmanagement.attendance.dao.mybatis;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.stockmanagement.attendance.dao.AttendanceRepository;
import project.stockmanagement.attendance.dao.domain.Attendance;

@Repository
@RequiredArgsConstructor
public class MyBatisAttendanceRepository implements AttendanceRepository {

	private final AttendanceMapper attendanceMapper;

	@Override
	public Attendance save(Attendance attendance, Timestamp currentDateTime) {
		Attendance createAttendance = Attendance.toCreateEntity(attendance, currentDateTime);
		attendanceMapper.save(createAttendance);

		return createAttendance;
	}

	@Override
	public Attendance findById(Long id) {
		Optional<Attendance> findAttendanceOptional = attendanceMapper.findById(id);

		return findAttendanceOptional.orElseThrow(
			() -> new NoSuchElementException("근로 관리 내역이 존재하지 않습니다."));
	}

	@Override
	public List<Attendance> findByEmployeeId(Long id) {
		return attendanceMapper.findByEmployeeId(id);
	}

	@Override
	public List<Attendance> findByCenterId(Integer id) {
		return attendanceMapper.findByCenterId(id);
	}

	@Override
	public Long update(Long id, Attendance updateAttendance, Timestamp updateDateTime) {
		Attendance updateEntity = findById(id).toUpdateEntity(updateAttendance, updateDateTime);
		attendanceMapper.update(id, updateEntity);

		return updateEntity.getId();
	}

	@Override
	public void delete(Long id) {
		attendanceMapper.delete(id);
	}
}
