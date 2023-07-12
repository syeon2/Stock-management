package project.stockmanagement.itemcategory.web;

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

import project.stockmanagement.itemcategory.service.ItemCategoryService;
import project.stockmanagement.itemcategory.service.response.ItemCategoryResponse;
import project.stockmanagement.itemcategory.web.request.ItemCategoryCreateRequest;

@WebMvcTest(controllers = ItemCategoryController.class)
class ItemCategoryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ItemCategoryService itemCategoryService;

	@Test
	@DisplayName("상품 카테고리를 생성하는 요청을 보냅니다.")
	void createItemCategoryRequest() throws Exception {
		// given
		ItemCategoryCreateRequest request = ItemCategoryCreateRequest.builder()
			.name("신선 식품")
			.centerId(1)
			.build();

		// when  // then
		mockMvc.perform(
				post("/api/v1/itemcategory")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("상품 카테고리를 생성하는 요청에 이름은 공백이 될 수 없습니다.")
	void createItemCategoryRequestWithoutName() throws Exception {
		// given
		ItemCategoryCreateRequest request = ItemCategoryCreateRequest.builder()
			.name(" ")
			.centerId(1)
			.build();

		// when  // then
		mockMvc.perform(
				post("/api/v1/itemcategory")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.data").isEmpty())
			.andExpect(jsonPath("$.message").value("상품 카테고리의 이름은 공백이 될 수 없습니다."));
	}

	@Test
	@DisplayName("상품 카테고리를 생성하는 요청에 센터 아이디는 필수 값입니다.")
	void createItemCategoryRequestWithoutCenterId() throws Exception {
		// given
		ItemCategoryCreateRequest request = ItemCategoryCreateRequest.builder()
			.name("냉동 제품")
			.build();

		// when  // then
		mockMvc.perform(
				post("/api/v1/itemcategory")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.data").isEmpty())
			.andExpect(jsonPath("$.message").value("센터 아이디는 필수 값입니다."));
	}

	@Test
	@DisplayName("아이디를 사용하여 상품 카테고리를 조회하는 요청을 보냅니다.")
	void findItemCategoryById() throws Exception {
		// given
		ItemCategoryResponse itemCategoryResponse = ItemCategoryResponse.builder()
			.name("신선 식품")
			.centerId(1)
			.build();

		int itemCategoryId = 1;
		when(itemCategoryService.findItemCategory(itemCategoryId)).thenReturn(itemCategoryResponse);

		// when  // then
		mockMvc.perform(
				get("/api/v1/itemcategory/" + itemCategoryId)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.name").value("신선 식품"))
			.andExpect(jsonPath("$.data.centerId").value(1))
			.andExpect(jsonPath("$.message").isEmpty());
	}

	@Test
	@DisplayName("센터 아이디를 사용하여 상품 카테고리를 조회하는 요청을 보냅니다.")
	void findItemCategoryByCenterId() throws Exception {
		// given
		ItemCategoryResponse itemCategoryResponse1 = ItemCategoryResponse.builder()
			.name("신선 식품")
			.centerId(1)
			.build();
		ItemCategoryResponse itemCategoryResponse2 = ItemCategoryResponse.builder()
			.name("신선 식품")
			.centerId(1)
			.build();
		ItemCategoryResponse itemCategoryResponse3 = ItemCategoryResponse.builder()
			.name("신선 식품")
			.centerId(1)
			.build();

		int centerId = 1;
		when(itemCategoryService.findItemCategoriesByCenterId(centerId))
			.thenReturn(List.of(itemCategoryResponse1, itemCategoryResponse2, itemCategoryResponse3));

		// when  // then
		mockMvc.perform(
				get("/api/v1/itemcategory/center/" + centerId)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isArray())
			.andExpect(jsonPath("$.message").isEmpty());
	}
}
