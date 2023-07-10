package project.stockmanagement.region.dao.mybatis;

import static org.assertj.core.api.Assertions.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import project.stockmanagement.region.dao.RegionRepository;
import project.stockmanagement.region.dao.domain.Region;

@ActiveProfiles("test")
@SpringBootTest
class MyBatisRegionRepositoryTest {

	@Autowired
	private RegionRepository regionRepository;

	@Test
	@DisplayName("지역을 생성합니다.")
	void createRegion() {
		// given
		String name = "Seoul";
		Timestamp currentDateTime = new Timestamp(System.currentTimeMillis());

		Region region = Region.builder()
			.name(name)
			.createdAt(currentDateTime)
			.updatedAt(currentDateTime)
			.build();

		// when
		Region savedRegion = regionRepository.save(region);

		// then
		assertThat(savedRegion.getId()).isEqualTo(1);
		assertThat(savedRegion.getName()).isEqualTo(name);
		assertThat(savedRegion.getCreatedAt()).isEqualTo(currentDateTime);
		assertThat(savedRegion.getUpdatedAt()).isEqualTo(currentDateTime);
	}

	@Test
	@DisplayName("모든 지역을 탐색합니다.")
	void findAllRegion() throws InterruptedException {
		// given
		Timestamp currentDateTime = Timestamp.valueOf(
			LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

		Region region1 = Region.builder()
			.name("Seoul")
			.createdAt(currentDateTime)
			.updatedAt(currentDateTime)
			.build();

		Region region2 = Region.builder()
			.name("Busan")
			.createdAt(currentDateTime)
			.updatedAt(currentDateTime)
			.build();

		Region region3 = Region.builder()
			.name("Daejeon")
			.createdAt(currentDateTime)
			.updatedAt(currentDateTime)
			.build();

		regionRepository.save(region1);
		regionRepository.save(region2);
		regionRepository.save(region3);

		// when
		List<Region> regions = regionRepository.findAll();

		// then
		assertThat(regions).hasSize(3)
			.extracting("name", "createdAt", "updatedAt")
			.containsExactlyInAnyOrder(
				tuple("Seoul", currentDateTime, currentDateTime),
				tuple("Busan", currentDateTime, currentDateTime),
				tuple("Daejeon", currentDateTime, currentDateTime)
			);
	}

	@Test
	@DisplayName("지역을 삭제합니다.")
	void deleteRegion() {
		// given
		String name = "Seoul";
		Timestamp currentDateTime = new Timestamp(System.currentTimeMillis());

		Region region = Region.builder()
			.name(name)
			.createdAt(currentDateTime)
			.updatedAt(currentDateTime)
			.build();

		// when
		regionRepository.delete(1);

		// then
		assertThat(regionRepository.findAll()).isEmpty();
	}
}
