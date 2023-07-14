package project.stockmanagement.order.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import project.stockmanagement.order.dao.OrderDetailRepository;
import project.stockmanagement.order.dao.OrderRepository;
import project.stockmanagement.order.dao.domain.Order;
import project.stockmanagement.order.dao.domain.OrderDetail;
import project.stockmanagement.order.dao.domain.OrderStatus;
import project.stockmanagement.order.service.request.OrderCreateServiceRequest;
import project.stockmanagement.order.service.request.OrderedItem;
import project.stockmanagement.order.service.response.OrderResponse;

@ActiveProfiles("test")
@SpringBootTest
class OrderServiceTest {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@AfterEach
	void after() {
		jdbcTemplate.execute("delete from orders");
		jdbcTemplate.execute("delete from order_detail");
		jdbcTemplate.execute("alter table orders auto_increment = 1");
		jdbcTemplate.execute("alter table order_detail auto_increment = 1");
	}

	@Test
	@DisplayName("주문 요청으로부터 주문, 주문 상세 내역을 생성합니다.")
	void createOrder() {
		// given
		long itemId1 = 1L;
		long itemId2 = 2L;
		OrderedItem orderedItem1 = createOrderedItem(itemId1, "itemA", 2);
		OrderedItem orderedItem2 = createOrderedItem(itemId2, "itemB", 4);

		OrderCreateServiceRequest request = OrderCreateServiceRequest.builder()
			.orderedItems(List.of(orderedItem1, orderedItem2))
			.centerId(1)
			.build();

		// when
		Long orderId = orderService.createOrder(request);

		// then
		assertThat(orderId).isEqualTo(1L);

		Order findOrder = orderRepository.findById(orderId);

		assertThat(findOrder)
			.extracting("id", "orderStatus", "totalCount", "centerId", "employeeId")
			.contains(1L, OrderStatus.WAITING, 6, 1, null);

		List<OrderDetail> findOrderDetails = orderDetailRepository.findByOrderId(orderId);
		assertThat(findOrderDetails).hasSize(2)
			.extracting("id", "name", "count", "orderId", "itemId")
			.containsExactlyInAnyOrder(
				tuple(1L, "itemA", 2, orderId, itemId1),
				tuple(2L, "itemB", 4, orderId, itemId2)
			);
	}

	@Test
	@DisplayName("주문 내역을 조회합니다.")
	void findOrderInfo() {
		// given
		OrderedItem orderedItem1 = createOrderedItem(1L, "itemA", 2);
		OrderedItem orderedItem2 = createOrderedItem(2L, "itemB", 4);

		OrderCreateServiceRequest request = OrderCreateServiceRequest.builder()
			.orderedItems(List.of(orderedItem1, orderedItem2))
			.centerId(1)
			.build();

		Long orderId = orderService.createOrder(request);

		// when
		OrderResponse orderInfo = orderService.findOrderInfo(orderId);

		// then
		assertThat(orderInfo)
			.extracting("orderStatus", "totalCount", "centerId", "employeeId")
			.contains(OrderStatus.WAITING, 6, 1, null);

		assertThat(orderInfo.getOrderedItems()).hasSize(2)
			.extracting("id", "name", "count")
			.containsExactlyInAnyOrder(
				tuple(1L, "itemA", 2),
				tuple(2L, "itemB", 4)
			);
	}

	private OrderedItem createOrderedItem(long id, String name, int count) {
		return OrderedItem.builder()
			.id(id)
			.name(name)
			.count(count)
			.build();
	}
}
