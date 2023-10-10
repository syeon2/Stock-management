package project.stockmanagement.item.web;

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

import project.stockmanagement.item.service.ItemService;
import project.stockmanagement.item.service.response.ItemResponse;
import project.stockmanagement.item.web.request.ItemCreateRequest;
import project.stockmanagement.item.web.request.ItemUpdateRequest;

@WebMvcTest(controllers = ItemController.class)
class ItemControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	public ItemService itemService;

	@Test
	@DisplayName("상품을 생성하는 요청을 보냅니다.")
	void createItem() throws Exception {
		// given
		ItemCreateRequest request = ItemCreateRequest.builder()
			.name("itemA")
			.itemCategoryId(1)
			.build();

		// when  // then
		mockMvc.perform(
				post("/api/v1/item")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("상품 이름은 공백을 허용하지 않습니다.")
	void createItemBlankName() throws Exception {
		// given
		ItemCreateRequest request = ItemCreateRequest.builder()
			.name("   ")
			.itemCategoryId(1)
			.build();

		// when  // then
		mockMvc.perform(
				post("/api/v1/item")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.data").doesNotExist())
			.andExpect(jsonPath("$.message").value("상품의 이름은 공백을 허용하지 않습니다."));
	}

	@Test
	@DisplayName("상품 카테고리 아이디는 필수 값입니다.")
	void createItemWithoutItemCategoryId() throws Exception {
		// given
		ItemCreateRequest request = ItemCreateRequest.builder()
			.name("itemA")
			.build();

		// when  // then
		mockMvc.perform(
				post("/api/v1/item")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.data").doesNotExist())
			.andExpect(jsonPath("$.message").value("상품 카테고리 아이디는 필수 값입니다."));
	}

	@Test
	@DisplayName("상품을 조회하는 요청을 보냅니다.")
	void findItem() throws Exception {
		// given
		long itemId = 1L;
		ItemResponse itemResponse = ItemResponse.builder()
			.id(itemId)
			.name("itemA")
			.quantity(0)
			.itemCategoryId(1)
			.build();

		when(itemService.findItem(1L)).thenReturn(itemResponse);

		// when  // then
		mockMvc.perform(
				get("/api/v1/item/" + itemId)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.id").value(itemId))
			.andExpect(jsonPath("$.message").doesNotExist());
	}

	@Test
	@DisplayName("상품 카테고리를 사용하여 상품을 조회하는 요청을 보냅니다.")
	void findItemByItemCategoryId() throws Exception {
		// given
		int itemCategoryId = 1;

		when(itemService.findItemByItemCategoryId(itemCategoryId)).thenReturn(List.of());

		// when  // then
		mockMvc.perform(
				get("/api/v1/item/item_category_id/" + itemCategoryId)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isArray())
			.andExpect(jsonPath("$.message").doesNotExist());
	}

	@Test
	@DisplayName("상품 정보를 수정하는 요청을 보냅니다.")
	void changeItemInfo() throws Exception {
		// given
		Long itemId = 1L;
		ItemUpdateRequest itemUpdateRequest = ItemUpdateRequest.builder()
			.name("itemA")
			.quantity(0)
			.itemCategoryId(1)
			.build();

		when(itemService.changeItemInfo(itemId, itemUpdateRequest.toServiceRequest())).thenReturn(itemId);

		// when  // then
		mockMvc.perform(
				post("/api/v1/item/" + itemId)
					.content(objectMapper.writeValueAsString(itemUpdateRequest))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk());
	}
}
