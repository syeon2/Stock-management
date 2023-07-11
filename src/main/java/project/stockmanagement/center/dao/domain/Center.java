package project.stockmanagement.center.dao.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Center {

	private Integer id;
	private final String name;
	private final Integer regionId;

	@Builder
	public Center(String name, Integer regionId) {
		this.name = name;
		this.regionId = regionId;
	}

	public Center(Integer id, String name, Integer regionId) {
		this.id = id;
		this.name = name;
		this.regionId = regionId;
	}
}
