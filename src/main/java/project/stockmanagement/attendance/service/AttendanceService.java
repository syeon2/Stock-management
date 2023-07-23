package project.stockmanagement.attendance.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.stockmanagement.attendance.dao.AttendanceRepository;
import project.stockmanagement.attendance.dao.domain.Attendance;
import project.stockmanagement.attendance.dao.domain.WorkStatus;
import project.stockmanagement.attendance.service.request.AttendanceCreateServiceRequest;
import project.stockmanagement.attendance.service.response.AttendanceResponse;
import project.stockmanagement.common.generator.TimeGenerator;

@Service
@RequiredArgsConstructor
public class AttendanceService {

	private final AttendanceRepository attendanceRepository;

	public AttendanceResponse createAttendance(AttendanceCreateServiceRequest request) {
		Attendance attendance = request.toDomain();
		Attendance savedAttendance = attendanceRepository.save(attendance, TimeGenerator.currentDateTime());

		return AttendanceResponse.of(savedAttendance);
	}

	public List<AttendanceResponse> findAttendance(Long employeeId, Integer page) {
		List<Attendance> attendances = attendanceRepository.findByEmployeeId(employeeId, page);

		return attendances.stream()
			.map(AttendanceResponse::of)
			.collect(Collectors.toList());
	}

	public List<AttendanceResponse> findAttendance(Integer centerId) {
		List<Attendance> attendances = attendanceRepository.findByCenterId(centerId);

		return attendances.stream()
			.map(AttendanceResponse::of)
			.collect(Collectors.toList());
	}

	public Long changeWorkStatusAttendance(Long id, WorkStatus workStatus) {
		Attendance changeWorkStatus = attendanceRepository
			.findById(id)
			.toChangeWorkStatus(workStatus);

		return attendanceRepository.update(id, changeWorkStatus, TimeGenerator.currentDateTime());
	}
}
