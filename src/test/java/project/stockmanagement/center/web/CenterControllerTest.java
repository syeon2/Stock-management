package project.stockmanagement.center.web;

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

import project.stockmanagement.center.service.CenterService;
import project.stockmanagement.center.service.response.CenterResponse;
import project.stockmanagement.center.web.request.CenterCreateRequest;

@WebMvcTest(controllers = CenterController.class)
class CenterControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private CenterService centerService;

	@Test
	@DisplayName("물류 센터를 등록합니다.")
	void createCenter() throws Exception {
		// given
		CenterCreateRequest request = CenterCreateRequest.builder()
			.name("성남 1지점")
			.regionId(1)
			.build();

		// when  // then
		mockMvc.perform(
				post("/api/v1/center")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("물류 센터 이름은 공백을 허용하지 않습니다.")
	void createCenterWithoutName() throws Exception {
		// given
		CenterCreateRequest request = CenterCreateRequest.builder()
			.name(" ")
			.regionId(1)
			.build();

		// when  // then
		mockMvc.perform(
				post("/api/v1/center")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.data").isEmpty())
			.andExpect(jsonPath("$.message").value("물류 센터 이름은 공백을 허용하지 않습니다."));
	}

	@Test
	@DisplayName("물류 센터 지역 아이디는 필수 입니다.")
	void createCenterWithoutRegionId() throws Exception {
		// given
		CenterCreateRequest request = CenterCreateRequest.builder()
			.name("성남 1지점")
			.regionId(null)
			.build();

		// when  // then
		mockMvc.perform(
				post("/api/v1/center")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.data").isEmpty())
			.andExpect(jsonPath("$.message").value("물류 센터 지역 아이디는 필수 입니다."));
	}

	@Test
	@DisplayName("전체 물류 센터를 조회합니다.")
	void getAllCenter() throws Exception {
		// given
		List<CenterResponse> centers = List.of();
		when(centerService.getAllCenters()).thenReturn(centers);

		// when  // then
		mockMvc.perform(
				get("/api/v1/centers")
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isArray())
			.andExpect(jsonPath("$.message").isEmpty());
	}
}
