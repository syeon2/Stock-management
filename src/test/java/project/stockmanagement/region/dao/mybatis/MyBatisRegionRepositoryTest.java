package project.stockmanagement.region.dao.mybatis;

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

@ActiveProfiles("test")
@SpringBootTest
class MyBatisRegionRepositoryTest {

	@Autowired
	private RegionRepository regionRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@AfterEach
	public void after() {
		jdbcTemplate.execute("delete from region");
		jdbcTemplate.execute("alter table region auto_increment = 1");
	}

	@Test
	@DisplayName("지역을 생성합니다.")
	void createRegion() {
		// given
		String name = "Seoul";
		Region region = createRegion(name);

		// when
		Region savedRegion = regionRepository.save(region);

		// then
		assertThat(savedRegion.getId()).isEqualTo(1);
		assertThat(savedRegion.getName()).isEqualTo(name);
	}

	@Test
	@DisplayName("모든 지역을 조회합니다.")
	void findAllRegion() throws InterruptedException {
		// given
		Region region1 = createRegion("Seoul");
		Region region2 = createRegion("Busan");
		Region region3 = createRegion("Daejeon");

		regionRepository.save(region1);
		regionRepository.save(region2);
		regionRepository.save(region3);

		// when
		List<Region> regions = regionRepository.findAll();

		// then
		assertThat(regions).hasSize(3)
			.extracting("id", "name")
			.containsExactlyInAnyOrder(
				tuple(1, "Seoul"),
				tuple(2, "Busan"),
				tuple(3, "Daejeon")
			);
	}

	@Test
	@DisplayName("지역을 삭제합니다.")
	void deleteRegion() {
		// given
		createRegion("Seoul");

		// when
		regionRepository.delete(1);

		// then
		assertThat(regionRepository.findAll()).isEmpty();
	}

	private Region createRegion(String name) {
		return Region.builder()
			.name(name)
			.build();
	}
}
