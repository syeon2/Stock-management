package project.stockmanagement.order.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.stockmanagement.common.config.redis.RedisOrderLockRepository;
import project.stockmanagement.order.dao.OrderDetailRepository;
import project.stockmanagement.order.dao.OrderRepository;
import project.stockmanagement.order.dao.domain.Order;
import project.stockmanagement.order.dao.domain.OrderStatus;
import project.stockmanagement.order.service.response.OrderToEmployeeResponse;

@Service
@RequiredArgsConstructor
public class OrderToEmployeeService {

	private final OrderRepository orderRepository;
	private final OrderDetailRepository orderDetailRepository;
	private final RedisOrderLockRepository redisOrderLockRepository;

	/**
	 * 동시적으로 DB 에서 WAITING Status 의 주문데이터를 조회하는 이슈를 Redis 분산락으로 처리
	 */
	public OrderToEmployeeResponse dispatchWaitedOrderToEmployee(Long employeeId) {
		Order waitingStatusOrder = orderRepository.findWaitingStatusOrder();
		Long orderId = waitingStatusOrder.getId();

		Boolean isLocked = redisOrderLockRepository.lock(orderId);
		while (!Boolean.TRUE.equals(isLocked)) {
			dispatchWaitedOrderToEmployee(employeeId);
		}

		orderRepository.update(orderId,
			waitingStatusOrder.toUpdateOrderWhenDispatchToEmployee(employeeId, OrderStatus.PROCESS));

		return OrderToEmployeeResponse.of(orderId, orderDetailRepository.findByOrderId(orderId));
	}
}
