package project.stockmanagement.region.dao.domain;

import lombok.Builder;
import lombok.Getter;
import project.stockmanagement.region.service.request.RegionCreateServiceRequest;

@Getter
public class Region {

	private Integer id;
	private final String name;

	@Builder
	private Region(String name) {
		this.name = name;
	}

	public Region(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public static Region createFromServiceRequest(RegionCreateServiceRequest request) {
		return Region.builder()
			.name(request.getName())
			.build();
	}
}
