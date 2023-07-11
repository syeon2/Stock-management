package project.stockmanagement.center.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import project.stockmanagement.center.dao.CenterRepository;
import project.stockmanagement.center.dao.domain.Center;
import project.stockmanagement.center.service.request.CenterCreateServiceRequest;
import project.stockmanagement.center.service.response.CenterResponse;

@ActiveProfiles("test")
@SpringBootTest
class CenterServiceTest {

	@Autowired
	private CenterService centerService;

	@Autowired
	private CenterRepository centerRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@AfterEach
	public void after() {
		jdbcTemplate.execute("delete from center");
		jdbcTemplate.execute("alter table center auto_increment = 1");
	}

	@Test
	@DisplayName("센터를 생성하는 요청 테스트")
	void createCenter() {
		// given
		Center center = createCenter("하남 1지점", 1);
		centerRepository.save(center);

		String centerName = "성남 2지점";
		CenterCreateServiceRequest request = CenterCreateServiceRequest.builder()
			.name(centerName)
			.regionId(2)
			.build();

		// when
		CenterResponse centerResponse = centerService.createCenter(request);

		// then
		assertThat(centerResponse)
			.extracting("id", "name", "regionId")
			.contains(2, "성남 2지점", 2);

		List<Center> centers = centerRepository.findAll();
		assertThat(centers).hasSize(2)
			.extracting("id", "name", "regionId")
			.containsExactlyInAnyOrder(
				tuple(1, "하남 1지점", 1),
				tuple(2, "성남 2지점", 2)
			);
	}

	@Test
	@DisplayName("모든 센터를 조회하는 요청 테스트")
	void getAllCenters() {
		// given
		Center center1 = createCenter("성남 1지점", 1);
		Center center2 = createCenter("하남 2지점", 2);
		Center center3 = createCenter("용인 3지점", 3);

		centerRepository.save(center1);
		centerRepository.save(center2);
		centerRepository.save(center3);

		// when
		List<CenterResponse> centerResponses = centerService.getAllCenters();

		// then
		assertThat(centerResponses).hasSize(3)
			.extracting("id", "name", "regionId")
			.containsExactlyInAnyOrder(
				tuple(1, "성남 1지점", 1),
				tuple(2, "하남 2지점", 2),
				tuple(3, "용인 3지점", 3)
			);
	}

	private Center createCenter(String name, Integer regionId) {
		return Center.builder()
			.name(name)
			.regionId(regionId)
			.build();
	}
}
