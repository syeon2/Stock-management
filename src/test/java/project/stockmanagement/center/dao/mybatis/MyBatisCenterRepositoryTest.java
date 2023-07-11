package project.stockmanagement.center.dao.mybatis;

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

@ActiveProfiles("test")
@SpringBootTest
class MyBatisCenterRepositoryTest {

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
	@DisplayName("센터를 저장합니다.")
	void saveCenter() {
		// given
		String centerName = "성남 1지점";
		Center center = createCenter(centerName, 1);

		// when
		Center savedCenter = centerRepository.save(center);

		// then
		assertThat(savedCenter.getId()).isEqualTo(1);
		assertThat(savedCenter.getName()).isEqualTo(centerName);
		assertThat(savedCenter.getRegionId()).isEqualTo(1);
	}

	@Test
	@DisplayName("모든 센터를 조회합니다.")
	void findAllCenter() {
		// given
		Center center1 = createCenter("성남 1지점", 1);
		Center center2 = createCenter("하남 2지점", 2);
		Center center3 = createCenter("용인 3지점", 3);

		centerRepository.save(center1);
		centerRepository.save(center2);
		centerRepository.save(center3);

		// when
		List<Center> centers = centerRepository.findAll();

		// then
		assertThat(centers).hasSize(3)
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
