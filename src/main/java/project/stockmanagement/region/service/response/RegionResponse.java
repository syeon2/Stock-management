package project.stockmanagement.region.service.response;

import lombok.Builder;
import lombok.Getter;
import project.stockmanagement.region.dao.domain.Region;

@Getter
public class RegionResponse {

	private final Integer id;
	private final String name;

	@Builder
	private RegionResponse(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public static RegionResponse of(Region region) {
		return RegionResponse.builder()
			.id(region.getId())
			.name(region.getName())
			.build();
	}
}
