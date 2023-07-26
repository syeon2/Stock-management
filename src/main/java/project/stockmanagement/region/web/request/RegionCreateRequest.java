package project.stockmanagement.region.web.request;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.stockmanagement.region.service.request.RegionCreateServiceRequest;

@Getter
@NoArgsConstructor
public class RegionCreateRequest {

	@NotBlank(message = "지역 이름은 공백을 허용하지 않습니다.")
	private String name;

	@Builder
	private RegionCreateRequest(String name) {
		this.name = name;
	}

	public RegionCreateServiceRequest toServiceRequest() {
		return RegionCreateServiceRequest.builder()
			.name(name)
			.build();
	}
}
