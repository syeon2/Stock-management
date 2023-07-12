package project.stockmanagement.common.generator;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TimeGenerator {

	public static Timestamp currentDateTime() {
		return Timestamp.valueOf(LocalDateTime.now());
	}
}
