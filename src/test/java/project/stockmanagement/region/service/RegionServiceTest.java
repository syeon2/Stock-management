package project.stockmanagement.region.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
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

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@AfterEach
	void after() {
		jdbcTemplate.execute("delete from region");
		jdbcTemplate.execute("alter table region auto_increment = 1");
	}

	@Test
	@DisplayName("지역을 생성하는 요청 테스트")
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
			.contains(2, "Busan");

		List<Region> regions = regionRepository.findAll();
		assertThat(regions).hasSize(2)
			.extracting("id", "name")
			.containsExactlyInAnyOrder(
				tuple(1, "Seoul"),
				tuple(2, "Busan")
			);
	}

	@Test
	@DisplayName("전체 지역을 조회하는 요청 테스트")
	void getRegions() {
		// given
		Region region1 = createRegion("Seoul");
		Region region2 = createRegion("Busan");
		Region region3 = createRegion("Daejeon");

		regionRepository.save(region1);
		regionRepository.save(region2);
		regionRepository.save(region3);

		// when
		List<RegionResponse> regionResponses = regionService.getRegions();

		// then
		assertThat(regionResponses).hasSize(3)
			.extracting("id", "name")
			.containsExactlyInAnyOrder(
				tuple(1, "Seoul"),
				tuple(2, "Busan"),
				tuple(3, "Daejeon")
			);
	}

	private Region createRegion(String name) {
		return Region.builder()
			.name(name)
			.build();
	}
}
