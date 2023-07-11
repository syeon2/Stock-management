package project.stockmanagement.region.service.request;

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

	public Region toDomain() {
		return Region.builder()
			.name(name)
			.build();
	}
}
