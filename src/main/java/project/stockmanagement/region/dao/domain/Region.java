package project.stockmanagement.region.dao.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Region {

	private Integer id;
	private final String name;

	@Builder
	private Region(String name) {
		this.name = name;
	}
}
