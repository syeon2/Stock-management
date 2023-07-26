package project.stockmanagement.center.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.stockmanagement.center.dao.CenterRepository;
import project.stockmanagement.center.dao.domain.Center;
import project.stockmanagement.center.service.request.CenterCreateServiceRequest;
import project.stockmanagement.center.service.response.CenterResponse;

@Service
@RequiredArgsConstructor
public class CenterService {

	private final CenterRepository centerRepository;

	public CenterResponse createCenter(CenterCreateServiceRequest request) {
		Center center = Center.createFromServiceRequest(request);
		Center savedCenter = centerRepository.save(center);

		return CenterResponse.of(savedCenter);
	}

	public List<CenterResponse> getAllCenters() {
		return centerRepository.findAll()
			.stream()
			.map(CenterResponse::of)
			.collect(Collectors.toList());
	}
}
