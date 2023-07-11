package project.stockmanagement.common.basewrapper;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.stockmanagement.common.generator.TimeGenerator;

@Getter
@NoArgsConstructor
public class CombinedEntity<T> {

	private T entity;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	@Builder
	private CombinedEntity(T entity, Timestamp createdAt, Timestamp updatedAt) {
		this.entity = entity;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public static <T> CombinedEntity<T> toCreateData(T entity) {
		return CombinedEntity.<T>builder()
			.entity(entity)
			.createdAt(TimeGenerator.currentTime())
			.updatedAt(TimeGenerator.currentTime())
			.build();
	}

	public static <T> CombinedEntity<T> toUpdateData(T entity) {
		return CombinedEntity.<T>builder()
			.entity(entity)
			.updatedAt(TimeGenerator.currentTime())
			.build();
	}

	public T toEntity() {
		return this.entity;
	}
}
