package project.stockmanagement.center.web.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.stockmanagement.center.service.request.CenterCreateServiceRequest;

@Getter
@NoArgsConstructor
public class CenterCreateRequest {

	@NotNull
	@NotBlank(message = "물류 센터 이름은 공백을 허용하지 않습니다.")
	private String name;

	@NotNull(message = "물류 센터 지역 아이디는 필수 입니다.")
	private Integer regionId;

	@Builder
	private CenterCreateRequest(String name, Integer regionId) {
		this.name = name;
		this.regionId = regionId;
	}

	public CenterCreateServiceRequest toServiceRequest() {
		return CenterCreateServiceRequest.builder()
			.name(name)
			.regionId(regionId)
			.build();
	}
}
