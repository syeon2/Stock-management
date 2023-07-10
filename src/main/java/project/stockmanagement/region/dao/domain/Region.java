package project.stockmanagement.region.dao.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Region {

	private Integer id;
	private final String name;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	@Builder
	private Region(String name, Timestamp createdAt, Timestamp updatedAt) {
		this.name = name;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
}
