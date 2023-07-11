package project.stockmanagement.center.service.response;

import lombok.Builder;
import lombok.Getter;
import project.stockmanagement.center.dao.domain.Center;

@Getter
public class CenterResponse {

	private final Integer id;
	private final String name;
	private final Integer regionId;

	@Builder
	private CenterResponse(Integer id, String name, Integer regionId) {
		this.id = id;
		this.name = name;
		this.regionId = regionId;
	}

	public static CenterResponse of(Center center) {
		return CenterResponse.builder()
			.id(center.getId())
			.name(center.getName())
			.regionId(center.getRegionId())
			.build();
	}
}
