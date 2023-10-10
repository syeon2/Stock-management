package project.stockmanagement.attendance.dao;

import java.sql.Timestamp;
import java.util.List;

import project.stockmanagement.attendance.dao.domain.Attendance;

public interface AttendanceRepository {

	Attendance save(Attendance attendance, Timestamp currentDateTime);

	Attendance findById(Long id);

	List<Attendance> findByEmployeeId(Long employeeId, Integer limit);

	List<Attendance> findByCenterId(Integer id);

	Long update(Long id, Attendance attendance, Timestamp currentDateTime);

	void delete(Long id);
}
