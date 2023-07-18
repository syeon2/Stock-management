package project.stockmanagement.order.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.stockmanagement.common.config.redis.RedisItemStockRepository;
import project.stockmanagement.employee.dao.EmployeeRepository;
import project.stockmanagement.employee.dao.domain.Employee;
import project.stockmanagement.item.dao.ItemRepository;
import project.stockmanagement.order.dao.OrderDetailRepository;
import project.stockmanagement.order.dao.OrderRepository;
import project.stockmanagement.order.dao.domain.Order;
import project.stockmanagement.order.dao.domain.OrderDetail;
import project.stockmanagement.order.dao.domain.OrderStatus;
import project.stockmanagement.order.service.response.OrderToEmployeeResponse;

@Service
@RequiredArgsConstructor
public class OrderToEmployeeService {

	private final OrderRepository orderRepository;
	private final OrderDetailRepository orderDetailRepository;
	private final EmployeeRepository employeeRepository;
	private final ItemRepository itemRepository;
	private final RedisItemStockRepository redisItemStockRepository;

	@Transactional
	public OrderToEmployeeResponse dispatchWaitedOrderToEmployee(Long employeeId) {
		Order waitingStatusOrder = orderRepository.findWaitingStatusOrder();
		Long orderId = waitingStatusOrder.getId();

		orderRepository.update(orderId,
			waitingStatusOrder.toUpdateOrderWhenDispatchToEmployee(employeeId, OrderStatus.PROCESS));

		return OrderToEmployeeResponse.of(orderId, orderDetailRepository.findByOrderId(orderId));
	}

	/**
	 * TODO: 주문 처리에 성공한 상품의 재고 감소는 추후 Kafka 같은 Message Queue 를 사용하여 RDB의 재고 History에 기록할 예정입니다.
	 */
	public Long completeOrder(Long orderId, Long employeeId) {
		// Redis 재고에서 먼저 에러 발생 시 뒤의 MySQL 에 쿼리 요청하는 작업에 접근하지 못합니다.
		List<OrderDetail> findOrderDetails = orderDetailRepository.findByOrderId(orderId);
		findOrderDetails.forEach(detail ->
			redisItemStockRepository.decreaseItemStock(detail.getItemId(), detail.getCount()));

		// 위의 Redis 에서 재고 확인 완료되면 주문 상태를 Complete 로 변경합니다.
		Order findOrder = orderRepository.findById(orderId);
		orderRepository.update(orderId, findOrder.toUpdateOrderWhenComplete(OrderStatus.COMPLETE));

		// 중요하지 않은 통계 데이터는 비동기 처리합니다.
		increaseItemPackingCount(employeeId);

		return orderId;
	}

	private void increaseItemPackingCount(Long employeeId) {
		Employee employee = employeeRepository.findById(employeeId);
		CompletableFuture.runAsync(() ->
			employeeRepository.update(employeeId, employee.increaseItemPackingCount()));
	}
}
