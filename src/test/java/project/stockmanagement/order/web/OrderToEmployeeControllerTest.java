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
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import project.stockmanagement.order.service.OrderToEmployeeService;
import project.stockmanagement.order.service.response.OrderToEmployeeResponse;

@WebMvcTest(controllers = OrderToEmployeeController.class)
class OrderToEmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private OrderToEmployeeService orderToEmployeeService;

	@Test
	@DisplayName("근로자에게 대기 상태인 주문 처리 건 1개에 대한 상품들을 할당합니다.")
	void dispatchWaitedOrderToEmployee() throws Exception {
		// given
		OrderToEmployeeResponse request = OrderToEmployeeResponse.builder()
			.orderItems(List.of())
			.build();

		long employeeId = 1;
		when(orderToEmployeeService.dispatchWaitedOrderToEmployee(employeeId)).thenReturn(request);

		// when  // then
		mockMvc.perform(
				post("/api/v1/employee/order/" + employeeId)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.orderItems").isArray());
	}
}
