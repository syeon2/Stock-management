package project.stockmanagement.region.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.stockmanagement.common.basewrapper.ApiResult;
import project.stockmanagement.region.service.RegionService;
import project.stockmanagement.region.service.response.RegionResponse;
import project.stockmanagement.region.web.request.RegionCreateRequest;

@RestController
@RequiredArgsConstructor
public class RegionController {

	private final RegionService regionService;

	@PostMapping("/api/v1/region")
	public ApiResult<RegionResponse> createRegion(@Valid @RequestBody RegionCreateRequest request) {
		RegionResponse savedRegion = regionService.createRegion(request.toServiceRequest());

		return ApiResult.onSuccess(savedRegion);
	}

	@GetMapping("/api/v1/regions")
	public ApiResult<List<RegionResponse>> getRegions() {
		List<RegionResponse> regions = regionService.getRegions();

		return ApiResult.onSuccess(regions);
	}
}
