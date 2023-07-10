package project.stockmanagement.common.generator;

import static org.assertj.core.api.Assertions.*;

import java.sql.Timestamp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TimeGeneratorTest {

	@Test
	@DisplayName("호출 시 새로운 인스턴스를 생성하여 각각 현재 시간을 반환합니다.")
	void getCurrentTime() throws InterruptedException {
		Timestamp caseA = TimeGenerator.currentTime();
		Thread.sleep(100);
		Timestamp caseB = TimeGenerator.currentTime();

		assertThat(caseA).isNotSameAs(caseB);
	}
}
