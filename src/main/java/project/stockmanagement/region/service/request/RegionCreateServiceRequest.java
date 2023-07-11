package project.stockmanagement.region.service.request;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import project.stockmanagement.region.dao.domain.Region;

@Getter
public class RegionCreateServiceRequest {

	private final String name;

	@Builder
	private RegionCreateServiceRequest(String name) {
		this.name = name;
	}

	public Region toDomain(Timestamp currentDateTime) {
		return Region.builder()
			.name(name)
			.build();
	}
}
