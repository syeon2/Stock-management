package project.stockmanagement.center.service.request;

import lombok.Builder;
import lombok.Getter;
import project.stockmanagement.center.dao.domain.Center;

@Getter
public class CenterCreateServiceRequest {

	private final String name;
	private final Integer regionId;

	@Builder
	public CenterCreateServiceRequest(String name, Integer regionId) {
		this.name = name;
		this.regionId = regionId;
	}

	public Center toDomain() {
		return Center.builder()
			.name(name)
			.regionId(regionId)
			.build();
	}
}
