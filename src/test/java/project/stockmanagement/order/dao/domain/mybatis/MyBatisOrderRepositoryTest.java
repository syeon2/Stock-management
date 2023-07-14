package project.stockmanagement.order.dao.domain.mybatis;

import static org.assertj.core.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import project.stockmanagement.order.dao.OrderRepository;
import project.stockmanagement.order.dao.domain.Order;
import project.stockmanagement.order.dao.domain.OrderStatus;

@ActiveProfiles("test")
@SpringBootTest
class MyBatisOrderRepositoryTest {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@AfterEach
	public void after() {
		jdbcTemplate.execute("delete from orders");
		jdbcTemplate.execute("alter table orders auto_increment = 1");
	}

	@Test
	@DisplayName("주문 내역을 추가합니다.")
	void saveOrder() {
		// given
		Order order = createOrder(OrderStatus.WAITING, 0, 1, null);

		// when
		Long savedId = orderRepository.save(order);

		// then
		assertThat(savedId).isEqualTo(1L);
	}

	@Test
	@DisplayName("주문 아이디를 통해 주문을 조회합니다.")
	void findById() {
		// given
		Order order = createOrder(OrderStatus.WAITING, 0, 1, null);
		Long id = orderRepository.save(order);

		// when
		Order findOrder = orderRepository.findById(id);

		// then
		assertThat(findOrder)
			.extracting("id", "orderStatus", "totalCount", "centerId", "employeeId")
			.contains(1L, OrderStatus.WAITING, 0, 1, null);
	}

	@Test
	@DisplayName("주문 내역을 수정합니다.")
	void update() {
		// given
		Order order = createOrder(OrderStatus.WAITING, 0, 1, null);
		Long id = orderRepository.save(order);

		Order updateOrder = createOrder(OrderStatus.PROCESS, 5, 1, 2L);

		// when
		Long updatedId = orderRepository.update(id, updateOrder);

		// then
		assertThat(updatedId).isEqualTo(id);

		Order findOrder = orderRepository.findById(id);
		assertThat(findOrder)
			.extracting("id", "orderStatus", "totalCount", "centerId", "employeeId")
			.contains(id, OrderStatus.PROCESS, 5, 1, 2L);
	}

	@Test
	@DisplayName("주문 내역을 삭제합니다.")
	void delete() {
		// given
		Order order = createOrder(OrderStatus.WAITING, 0, 1, null);
		Long id = orderRepository.save(order);

		assertThat(orderRepository.findById(id).getId()).isEqualTo(id);
		// when
		orderRepository.delete(id);

		// then
		assertThatThrownBy(() -> orderRepository.findById(id))
			.isInstanceOf(NoSuchElementException.class);
	}

	private Order createOrder(OrderStatus orderStatus, Integer totalCount, Integer centerId, Long employeeId) {
		return Order.builder()
			.orderStatus(orderStatus)
			.totalCount(totalCount)
			.centerId(centerId)
			.employeeId(employeeId)
			.build();
	}
}
