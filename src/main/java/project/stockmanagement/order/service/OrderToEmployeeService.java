package project.stockmanagement.order.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.stockmanagement.employee.dao.EmployeeRepository;
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
	private final EmployeeRepository employeeRepository;

	/**
	 * 기존에는 서버에서 OrderStatus = Waiting 인 데이터를 조회하여 근로자에게 dispatch 하였습니다.
	 * 하지만 기획상으로 근로자가 자신이 처리할 주문 아이디를 함께 요청하는데 큰 무리는 없기 때문에 근로자가 처리해야하는 orderId를 제공함으로써
	 * 기존의 트랜잭션과 락을 사용하여 큰 부하의 요인이 되었던 메소드를 최적화 가능해졌습니다.
	 */
	public OrderToEmployeeResponse dispatchWaitedOrderToEmployee(Long orderId, Long employeeId) {
		Order findOrder = orderRepository.findById(orderId);

		orderRepository.update(orderId,
			findOrder.toUpdateOrderWhenDispatchToEmployee(employeeId, OrderStatus.PROCESS));

		return OrderToEmployeeResponse.of(orderId, orderDetailRepository.findByOrderId(orderId));
	}

	/**
	 * 해당 로직은 전체적인 프로세스 수정에 의해 재고를 실시간으로 처리하지 않아도 되었습니다.
	 * 해당 로직을 통해 주문 상태만 Complete 로 변경하고 특정 상품에 대한 재고 상황은 쿼리로 풀어내었습니다.
	 */
	public Long completeOrder(Long orderId, Long employeeId) {
		increaseItemPackingCount(employeeId);

		Order findOrder = orderRepository.findById(orderId);
		orderRepository.update(orderId, findOrder.toUpdateOrderWhenComplete(OrderStatus.COMPLETE, employeeId));

		return orderId;
	}

	private void increaseItemPackingCount(Long employeeId) {
		CompletableFuture.supplyAsync(() -> employeeRepository.findById(employeeId))
			.thenAccept(e -> employeeRepository.update(employeeId, e.increaseItemPackingCount()));
	}
}
