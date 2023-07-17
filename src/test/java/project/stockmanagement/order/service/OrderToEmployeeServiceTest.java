package project.stockmanagement.order.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import project.stockmanagement.order.dao.OrderDetailRepository;
import project.stockmanagement.order.dao.OrderRepository;
import project.stockmanagement.order.dao.domain.Order;
import project.stockmanagement.order.dao.domain.OrderDetail;
import project.stockmanagement.order.dao.domain.OrderStatus;
import project.stockmanagement.order.service.response.OrderToEmployeeResponse;

@ActiveProfiles("test")
@SpringBootTest
class OrderToEmployeeServiceTest {

	@Autowired
	private OrderToEmployeeService orderToEmployeeService;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private RedisTemplate<String, String> redisOrderLockTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@AfterEach
	public void after() {
		jdbcTemplate.execute("delete from orders");
		jdbcTemplate.execute("delete from order_detail");
		jdbcTemplate.execute("alter table orders auto_increment = 1");
		jdbcTemplate.execute("alter table order_detail auto_increment = 1");
	}

	@Test
	@DisplayName("대기 중인 주문을 근로자에게 할당합니다.")
	void dispatchWaitedOrderToEmployee() throws InterruptedException {
		// given
		Order order = createOrder(OrderStatus.WAITING, 0, 1, null);
		orderRepository.save(order);

		OrderDetail orderDetail1 = createOrderDetail("itemA", 2, 1L, 1L);
		OrderDetail orderDetail2 = createOrderDetail("itemB", 3, 1L, 2L);
		orderDetailRepository.save(orderDetail1);
		orderDetailRepository.save(orderDetail2);

		// when
		OrderToEmployeeResponse orderToEmployeeResponse = orderToEmployeeService.dispatchWaitedOrderToEmployee(1L);

		// then
		assertThat(orderToEmployeeResponse.getOrderItems()).hasSize(2)
			.extracting("id", "name", "count")
			.containsExactlyInAnyOrder(
				tuple(1L, "itemA", 2),
				tuple(2L, "itemB", 3)
			);

		Order findOrder = orderRepository.findById(1L);
		assertThat(findOrder)
			.extracting("id", "orderStatus", "totalCount", "centerId", "employeeId")
			.contains(1L, OrderStatus.PROCESS, 0, 1, 1L);
	}

	@Test
	@DisplayName("분산 환경에서 대기 중인 주문을 각 근로자에게 할당합니다.")
	void dispatchWaitedOrderToEmployeeForDistributionServer() throws InterruptedException {
		// given
		Order order = createOrder(OrderStatus.WAITING, 5, 1, null);
		orderRepository.save(order);

		OrderDetail orderDetail1 = createOrderDetail("itemA", 2, 1L, 1L);
		OrderDetail orderDetail2 = createOrderDetail("itemB", 3, 1L, 2L);
		orderDetailRepository.save(orderDetail1);
		orderDetailRepository.save(orderDetail2);

		Order order2 = createOrder(OrderStatus.WAITING, 13, 1, null);
		orderRepository.save(order2);

		OrderDetail orderDetail3 = createOrderDetail("itemC", 6, 2L, 1L);
		OrderDetail orderDetail4 = createOrderDetail("itemD", 7, 2L, 2L);
		orderDetailRepository.save(orderDetail3);
		orderDetailRepository.save(orderDetail4);

		// when
		Thread thread1 = new Thread(() -> {
			orderToEmployeeService.dispatchWaitedOrderToEmployee(1L);
		});

		Thread thread2 = new Thread(() -> {
			orderToEmployeeService.dispatchWaitedOrderToEmployee(2L);
		});

		thread1.start();
		thread2.start();

		thread1.join();
		thread2.join();

		// then
		assertThatThrownBy(() -> orderToEmployeeService.dispatchWaitedOrderToEmployee(2L))
			.isInstanceOf(NoSuchElementException.class);

		List<OrderDetail> findOrderDetails1 = orderDetailRepository.findByOrderId(1L);
		assertThat(findOrderDetails1).hasSize(2)
			.extracting("id", "name", "count")
			.containsExactlyInAnyOrder(
				tuple(1L, "itemA", 2),
				tuple(2L, "itemB", 3)
			);

		List<OrderDetail> findOrderDetails2 = orderDetailRepository.findByOrderId(2L);
		assertThat(findOrderDetails2).hasSize(2)
			.extracting("id", "name", "count")
			.containsExactlyInAnyOrder(
				tuple(3L, "itemC", 6),
				tuple(4L, "itemD", 7)
			);

		Order findOrder1 = orderRepository.findById(1L);
		assertThat(findOrder1)
			.extracting("id", "orderStatus", "totalCount", "centerId", "employeeId")
			.contains(1L, OrderStatus.PROCESS, 5, 1, 1L);

		Order findOrder2 = orderRepository.findById(2L);
		assertThat(findOrder2)
			.extracting("id", "orderStatus", "totalCount", "centerId", "employeeId")
			.contains(2L, OrderStatus.PROCESS, 13, 1, 2L);
	}

	private Order createOrder(OrderStatus orderStatus, Integer totalCount, Integer centerId, Long employeeId) {
		return Order.builder()
			.orderStatus(orderStatus)
			.totalCount(totalCount)
			.centerId(centerId)
			.employeeId(employeeId)
			.build();
	}

	private OrderDetail createOrderDetail(String name, int count, long orderId, long itemId) {
		return OrderDetail.builder()
			.name(name)
			.count(count)
			.orderId(orderId)
			.itemId(itemId)
			.build();
	}
}
