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
import project.stockmanagement.attendance.web.request.AttendanceUpdateWorkStatusRequest;
import project.stockmanagement.attendance.web.request.EmployeeAttendanceParam;
import project.stockmanagement.common.basewrapper.ApiResult;

@RestController
@RequiredArgsConstructor
public class AttendanceForCenterController {

	private final AttendanceService attendanceService;

	@GetMapping("/api/v1/attendance")
	public ApiResult<List<AttendanceResponse>> getEmployeeAttendances(EmployeeAttendanceParam request) {
		List<AttendanceResponse> attendance =
			attendanceService.findAttendance(request.getEmployeeId(), request.getPage());

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
