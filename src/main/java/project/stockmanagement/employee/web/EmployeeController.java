package project.stockmanagement.employee.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.stockmanagement.common.basewrapper.ApiResult;
import project.stockmanagement.employee.service.EmployeeService;
import project.stockmanagement.employee.service.response.EmployeeFindResponse;
import project.stockmanagement.employee.service.response.EmployeeResponse;
import project.stockmanagement.employee.web.request.EmployeeCreateRequest;
import project.stockmanagement.employee.web.request.EmployeeUpdateRequest;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

	private final EmployeeService employeeService;

	@PostMapping("/api/v1/employee")
	public ApiResult<EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeCreateRequest request) {
		EmployeeResponse employeeResponse = employeeService.createEmployee(request.toServiceRequest());

		return ApiResult.onSuccess(employeeResponse);
	}

	@GetMapping("/api/v1/employee/{id}")
	public ApiResult<EmployeeFindResponse> findEmployee(@PathVariable Long id) {
		EmployeeFindResponse employeeResponse = employeeService.findEmployee(id);

		return ApiResult.onSuccess(employeeResponse);
	}

	@GetMapping("/api/v1/employee/center/{id}")
	public ApiResult<List<EmployeeResponse>> findEmployeesByCenter(@PathVariable Integer id) {
		List<EmployeeResponse> employeeResponses = employeeService.findEmployeesByCenterId(id);

		return ApiResult.onSuccess(employeeResponses);
	}

	@PostMapping("/api/v1/employee/{id}")
	public Long updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeUpdateRequest request) {
		return employeeService.updateEmployee(id, request.toServiceRequest());
	}
}
