package project.stockmanagement.region.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import project.stockmanagement.region.dao.RegionRepository;
import project.stockmanagement.region.dao.domain.Region;
import project.stockmanagement.region.service.request.RegionCreateServiceRequest;
import project.stockmanagement.region.service.response.RegionResponse;

@ActiveProfiles("test")
@SpringBootTest
class RegionServiceTest {

	@Autowired
	private RegionRepository regionRepository;

	@Autowired
	private RegionService regionService;

	@Test
	@DisplayName("지역을 생성합니다.")
	void createRegion() {
		// given
		Region region = createRegion("Seoul");
		regionRepository.save(region);

		RegionCreateServiceRequest request = RegionCreateServiceRequest.builder()
			.name("Busan")
			.build();

		// when
		RegionResponse regionResponse = regionService.createRegion(request);

		// then
		assertThat(regionResponse)
			.extracting("id", "name")
			.contains(1, "Busan");

		List<Region> regions = regionRepository.findAll();
		assertThat(regions).hasSize(2)
			.extracting("id", "name")
			.containsExactlyInAnyOrder(
				tuple(1, "Seoul"),
				tuple(2, "Busan")
			);
	}

	private Region createRegion(String name) {
		return Region.builder()
			.name(name)
			.build();
	}
}
