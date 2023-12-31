package project.stockmanagement.order.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import project.stockmanagement.common.config.redis.RedisItemStockRepository;
import project.stockmanagement.common.error.exception.NotEnoughStockException;
import project.stockmanagement.employee.dao.EmployeeRepository;
import project.stockmanagement.employee.dao.domain.Employee;
import project.stockmanagement.employee.dao.domain.EmployeeStatus;
import project.stockmanagement.item.dao.ItemRepository;
import project.stockmanagement.item.dao.domain.Item;
import project.stockmanagement.itemcategory.dao.ItemCategoryRepository;
import project.stockmanagement.itemcategory.dao.domain.ItemCategory;
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
	private ItemRepository itemRepository;

	@Autowired
	private ItemCategoryRepository itemCategoryRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private RedisItemStockRepository redisItemStockRepository;

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
		OrderToEmployeeResponse orderToEmployeeResponse = orderToEmployeeService.dispatchWaitedOrderToEmployee(1L, 1L);

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
			orderToEmployeeService.dispatchWaitedOrderToEmployee(1L, 1L);
		});

		Thread thread2 = new Thread(() -> {
			orderToEmployeeService.dispatchWaitedOrderToEmployee(2L, 2L);
		});

		thread1.start();
		thread2.start();

		thread1.join();
		thread2.join();

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

	// @Test
	// @DisplayName("주문을 완료합니다.")
	// void completeOrder() throws InterruptedException {
	// 	// given
	// 	Order order = createOrder(OrderStatus.WAITING, 5, 1, null);
	// 	orderRepository.save(order);
	//
	// 	OrderDetail orderDetail = createOrderDetail("itemA", 5, 1L, 1L);
	// 	orderDetailRepository.save(orderDetail);
	//
	// 	Employee employee = Employee.builder()
	// 		.phone("0000000000")
	// 		.name("memberA")
	// 		.employeeStatus(EmployeeStatus.WORKING)
	// 		.centerId(1)
	// 		.workingDay(LocalDate.now())
	// 		.build();
	//
	// 	employeeRepository.save(employee);
	//
	// 	// when
	// 	orderToEmployeeService.completeOrder(1L, 1L);
	//
	// 	// then
	// 	Order findOrder = orderRepository.findById(1L);
	// 	assertThat(findOrder)
	// 		.extracting("id", "orderStatus", "totalCount", "centerId", "employeeId")
	// 		.contains(1L, OrderStatus.COMPLETE, 5, 1, 1L);
	//
	// 	Employee findEmployee = employeeRepository.findById(1L);
	// 	assertThat(findEmployee.getItemPackagingCount()).isEqualTo(1);
	// }

	@Test
	@DisplayName("주문을 완료합니다.")
	void completeOrder() throws InterruptedException {
		// given
		Order order = createOrder(OrderStatus.WAITING, 5, 1, null);
		orderRepository.save(order);

		OrderDetail orderDetail1 = createOrderDetail("itemA", 5, 1L, 1L);
		orderDetailRepository.save(orderDetail1);

		Order order2 = createOrder(OrderStatus.WAITING, 10, 1, null);
		orderRepository.save(order2);

		OrderDetail orderDetail2 = createOrderDetail("itemA", 20, 2L, 1L);
		OrderDetail orderDetail3 = createOrderDetail("itemB", 20, 2L, 2L);
		orderDetailRepository.save(orderDetail2);
		orderDetailRepository.save(orderDetail3);

		Item item1 = Item.builder()
			.id(1L)
			.name("itemA")
			.quantity(0)
			.itemCategoryId(1)
			.build();

		Item item2 = Item.builder()
			.id(2L)
			.name("itemB")
			.quantity(0)
			.itemCategoryId(1)
			.build();

		itemRepository.save(item1);
		itemRepository.save(item2);

		ItemCategory itemCategory = ItemCategory.builder()
			.name("food")
			.centerId(1)
			.build();

		itemCategoryRepository.save(itemCategory);

		Employee employee = Employee.builder()
			.phone("0000000000")
			.name("memberA")
			.employeeStatus(EmployeeStatus.WORKING)
			.centerId(1)
			.workingDay(LocalDate.now())
			.build();

		redisItemStockRepository.clearItemStock();
		redisItemStockRepository.setItemStock(1L, 100);
		redisItemStockRepository.setItemStock(2L, 10);

		employeeRepository.save(employee);

		// when
		orderToEmployeeService.completeOrder(1L, 1L);
		assertThatThrownBy(() -> orderToEmployeeService.completeOrder(2L, 1L))
			.isInstanceOf(NotEnoughStockException.class);

		// then
		Long itemStock = redisItemStockRepository.getItemStock(1L);
		Long itemStock2 = redisItemStockRepository.getItemStock(2L);
		
		assertThat(itemStock).isEqualTo(95);
		assertThat(itemStock2).isEqualTo(10);
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
