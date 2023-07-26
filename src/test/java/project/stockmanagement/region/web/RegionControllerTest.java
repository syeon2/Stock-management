package project.stockmanagement.region.web;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import project.stockmanagement.region.service.RegionService;
import project.stockmanagement.region.service.response.RegionResponse;
import project.stockmanagement.region.web.request.RegionCreateRequest;

@WebMvcTest(controllers = RegionController.class)
class RegionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private RegionService regionService;

	@Test
	@DisplayName("지역을 등록합니다.")
	void createRegion() throws Exception {
		// given
		RegionCreateRequest request = RegionCreateRequest.builder()
			.name("Seoul")
			.build();

		// when  // then
		mockMvc.perform(
				post("/api/v1/region")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("지역 이름은 공백을 허용하지 않습니다.")
	void createRegionWithoutName() throws Exception {
		// given
		RegionCreateRequest request = RegionCreateRequest.builder()
			.name(" ")
			.build();

		// when  // then
		mockMvc.perform(
				post("/api/v1/region")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.data").doesNotExist())
			.andExpect(jsonPath("$.message").value("지역 이름은 공백을 허용하지 않습니다."));
	}

	@Test
	@DisplayName("전체 지역을 조회한다.")
	void getAllRegion() throws Exception {
		// given
		List<RegionResponse> regions = List.of();
		when(regionService.getRegions()).thenReturn(regions);

		// when  // then
		mockMvc.perform(
				get("/api/v1/regions")
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isArray())
			.andExpect(jsonPath("$.message").doesNotExist());
	}
}
