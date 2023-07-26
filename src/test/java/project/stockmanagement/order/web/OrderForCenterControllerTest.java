package project.stockmanagement.order.web;

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

import project.stockmanagement.order.dao.domain.OrderStatus;
import project.stockmanagement.order.service.OrderToCenterService;
import project.stockmanagement.order.service.request.OrderItem;
import project.stockmanagement.order.service.response.OrderResponse;
import project.stockmanagement.order.web.request.OrderCreateRequest;

@WebMvcTest(controllers = OrderForCenterController.class)
class OrderForCenterControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private OrderToCenterService orderToCenterService;

	@Test
	@DisplayName("주문을 요청하는 Http Request를 보냅니다.")
	void createOrder() throws Exception {
		// given
		OrderItem itemA = OrderItem.builder()
			.id(1L)
			.name("itemA")
			.count(1)
			.build();

		OrderCreateRequest request = OrderCreateRequest.builder()
			.orderedItems(List.of(itemA))
			.centerId(1)
			.build();

		// when  // then
		mockMvc.perform(
				post("/api/v1/center/order")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("주문 시 상품은 필수 값입니다.")
	void createOrderWithoutItems() throws Exception {
		// given
		OrderCreateRequest request = OrderCreateRequest.builder()
			.centerId(1)
			.build();

		// when  // then
		mockMvc.perform(
				post("/api/v1/center/order")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.data").doesNotExist())
			.andExpect(jsonPath("$.message").value("주문 시 상품은 필수 값입니다."));
	}

	@Test
	@DisplayName("주문 시 센터 아이디는 필수 값입니다.")
	void createOrderWithoutCenterId() throws Exception {
		// given
		OrderCreateRequest request = OrderCreateRequest.builder()
			.orderedItems(List.of())
			.build();

		// when  // then
		mockMvc.perform(
				post("/api/v1/center/order")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.data").doesNotExist())
			.andExpect(jsonPath("$.message").value("물류 센터 아이디는 필수 값입니다."));
	}

	@Test
	@DisplayName("주문 정보를 조회하는 Http Request 요청을 보냅니다.")
	void findOrderInfo() throws Exception {
		// given
		OrderResponse orderResponse = OrderResponse.builder()
			.orderStatus(OrderStatus.WAITING)
			.totalCount(1)
			.centerId(1)
			.employeeId(null)
			.orderedItems(List.of())
			.build();

		long id = 1;
		when(orderToCenterService.findOrderInfo(1L)).thenReturn(orderResponse);

		// when  // then
		mockMvc.perform(
				get("/api/v1/center/order/" + id)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.totalCount").value(1))
			.andExpect(jsonPath("$.message").doesNotExist());
	}
}
