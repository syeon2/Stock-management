package project.stockmanagement.attendance.web;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.stockmanagement.attendance.service.AttendanceService;
import project.stockmanagement.attendance.service.response.AttendanceResponse;
import project.stockmanagement.attendance.web.request.AttendanceCreateRequest;
import project.stockmanagement.common.basewrapper.ApiResult;

@RestController
@RequiredArgsConstructor
public class AttendanceForEmployeeController {

	private final AttendanceService attendanceService;

	@PostMapping("/api/v1/attendance")
	public ApiResult<AttendanceResponse> createAttendance(@Valid @RequestBody AttendanceCreateRequest request) {
		AttendanceResponse attendanceResponse = attendanceService.createAttendance(request.toServiceRequest());

		return ApiResult.onSuccess(attendanceResponse);
	}
}
