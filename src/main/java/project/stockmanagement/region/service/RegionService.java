package project.stockmanagement.region.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.stockmanagement.region.dao.RegionRepository;
import project.stockmanagement.region.dao.domain.Region;
import project.stockmanagement.region.service.request.RegionCreateServiceRequest;
import project.stockmanagement.region.service.response.RegionResponse;

@Service
@RequiredArgsConstructor
public class RegionService {

	private final RegionRepository regionRepository;

	public RegionResponse createRegion(RegionCreateServiceRequest request) {
		Region region = Region.createFromServiceRequest(request);
		Region savedRegion = regionRepository.save(region);

		return RegionResponse.of(savedRegion);
	}

	public List<RegionResponse> getRegions() {
		return regionRepository.findAll().stream()
			.map(RegionResponse::of)
			.collect(Collectors.toList());
	}
}
