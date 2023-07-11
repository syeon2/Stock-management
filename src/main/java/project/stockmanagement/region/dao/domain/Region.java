package project.stockmanagement.region.dao.domain;

import lombok.Builder;
import lombok.Getter;

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
}
