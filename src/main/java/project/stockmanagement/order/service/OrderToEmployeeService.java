package project.stockmanagement.order.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
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

	@Transactional
	public OrderToEmployeeResponse dispatchWaitedOrderToEmployee(Long employeeId) {
		Order waitingStatusOrder = orderRepository.findWaitingStatusOrder();
		Long orderId = waitingStatusOrder.getId();

		orderRepository.update(orderId,
			waitingStatusOrder.toUpdateOrderWhenDispatchToEmployee(employeeId, OrderStatus.PROCESS));

		return OrderToEmployeeResponse.of(orderId, orderDetailRepository.findByOrderId(orderId));
	}
}
