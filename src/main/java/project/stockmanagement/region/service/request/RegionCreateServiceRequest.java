package project.stockmanagement.region.service.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RegionCreateServiceRequest {

	private final String name;

	@Builder
	private RegionCreateServiceRequest(String name) {
		this.name = name;
	}
}
