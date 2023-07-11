package project.stockmanagement.center.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.stockmanagement.center.service.CenterService;
import project.stockmanagement.center.service.response.CenterResponse;
import project.stockmanagement.center.web.request.CenterCreateRequest;
import project.stockmanagement.common.basewrapper.ApiResult;

@RestController
@RequiredArgsConstructor
public class CenterController {

	private final CenterService centerService;

	@PostMapping("/api/v1/center")
	public ApiResult<CenterResponse> createCenter(@Valid @RequestBody CenterCreateRequest request) {
		CenterResponse createdCenter = centerService.createCenter(request.toServiceRequest());

		return ApiResult.onSuccess(createdCenter);
	}
	
	@GetMapping("/api/v1/centers")
	public ApiResult<List<CenterResponse>> getAllCenters() {
		List<CenterResponse> centers = centerService.getAllCenters();

		return ApiResult.onSuccess(centers);
	}
}
