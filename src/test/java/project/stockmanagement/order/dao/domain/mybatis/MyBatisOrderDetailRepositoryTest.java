package project.stockmanagement.order.dao.domain.mybatis;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import project.stockmanagement.order.dao.OrderDetailRepository;
import project.stockmanagement.order.dao.domain.OrderDetail;

@SpringBootTest
class MyBatisOrderDetailRepositoryTest {

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@AfterEach
	void after() {
		jdbcTemplate.execute("delete from order_detail");
		jdbcTemplate.execute("alter table order_detail auto_increment = 1");
	}

	@Test
	@DisplayName("주문 세부 내역을 저장합니다.")
	void saveOrderDetail() {
		// given
		OrderDetail orderDetail = createOrderDetail("itemA", 0, 1L, 1L);

		// when
		OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);

		// then
		assertThat(savedOrderDetail.getId()).isEqualTo(1L);
	}

	@Test
	@DisplayName("주문 세부 내역을 조회합니다.")
	void findById() {
		// given
		OrderDetail orderDetail = createOrderDetail("itemA", 0, 1L, 1L);
		Long id = orderDetailRepository.save(orderDetail).getId();

		// when
		OrderDetail findOrderDetail = orderDetailRepository.findById(id);

		// then
		assertThat(findOrderDetail)
			.extracting("id", "name", "count", "orderId", "itemId")
			.contains(1L, "itemA", 0, 1L, 1L);
	}

	@Test
	@DisplayName("주문 아이디를 사용하여 주문 세부 내역들을 조회합니다.")
	void findByOrderId() {
		// given
		long orderId = 1L;
		OrderDetail orderDetail1 = createOrderDetail("itemA", 0, orderId, 1L);
		OrderDetail orderDetail2 = createOrderDetail("itemB", 0, orderId, 1L);
		OrderDetail orderDetail3 = createOrderDetail("itemC", 0, orderId, 1L);

		orderDetailRepository.save(orderDetail1);
		orderDetailRepository.save(orderDetail2);
		orderDetailRepository.save(orderDetail3);

		// when
		List<OrderDetail> findOrderDetails = orderDetailRepository.findByOrderId(orderId);

		// then
		assertThat(findOrderDetails)
			.extracting("id", "name", "count", "orderId", "itemId")
			.containsExactlyInAnyOrder(
				tuple(1L, "itemA", 0, orderId, 1L),
				tuple(2L, "itemB", 0, orderId, 1L),
				tuple(3L, "itemC", 0, orderId, 1L)
			);
	}

	@Test
	@DisplayName("주문 세부 내역을 삭제합니다.")
	void delete() {
		// given
		OrderDetail orderDetail = createOrderDetail("itemA", 0, 1L, 1L);
		Long id = orderDetailRepository.save(orderDetail).getId();

		assertThat(orderDetailRepository.findById(id).getId()).isEqualTo(id);

		// when
		orderDetailRepository.delete(id);

		// then
		assertThatThrownBy(() -> orderDetailRepository.findById(id))
			.isInstanceOf(NoSuchElementException.class);
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
