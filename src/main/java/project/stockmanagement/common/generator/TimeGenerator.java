package project.stockmanagement.common.generator;

import java.sql.Timestamp;

public class TimeGenerator {

	public static Timestamp currentTime() {
		return new Timestamp(System.currentTimeMillis());
	}
}
