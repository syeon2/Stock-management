package project.stockmanagement.attendance.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.stockmanagement.attendance.service.AttendanceService;
import project.stockmanagement.attendance.service.response.AttendanceResponse;
import project.stockmanagement.attendance.web.request.AttendanceCreateRequest;
import project.stockmanagement.attendance.web.request.AttendanceUpdateWorkStatusRequest;
import project.stockmanagement.common.basewrapper.ApiResult;

@RestController
@RequiredArgsConstructor
public class AttendanceController {

	private final AttendanceService attendanceService;

	@PostMapping("/api/v1/attendance")
	public ApiResult<AttendanceResponse> createAttendance(@Valid @RequestBody AttendanceCreateRequest request) {
		AttendanceResponse attendanceResponse = attendanceService.createAttendance(request.toServiceRequest());

		return ApiResult.onSuccess(attendanceResponse);
	}

	@GetMapping("/api/v1/attendance/employee/{id}")
	public ApiResult<List<AttendanceResponse>> getEmployeeAttendances(@PathVariable Long id) {
		List<AttendanceResponse> attendance = attendanceService.findAttendance(id);

		return ApiResult.onSuccess(attendance);
	}

	@GetMapping("/api/v1/attendance/center/{id}")
	public ApiResult<List<AttendanceResponse>> getAttendanceInCenter(@PathVariable Integer id) {
		List<AttendanceResponse> attendance = attendanceService.findAttendance(id);

		return ApiResult.onSuccess(attendance);
	}

	@PostMapping("/api/v1/attendance/{id}")
	public ApiResult<Long> changeWorkStatusAttendance(@PathVariable Long id,
		@Valid @RequestBody AttendanceUpdateWorkStatusRequest request) {
		Long changedId = attendanceService.changeWorkStatusAttendance(id, request.getWorkStatus());

		return ApiResult.onSuccess(changedId);
	}
}
