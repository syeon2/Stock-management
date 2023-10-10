package project.stockmanagement.order.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import lombok.RequiredArgsConstructor;
import project.stockmanagement.common.config.redis.RedisItemStockRepository;
import project.stockmanagement.employee.dao.EmployeeRepository;
import project.stockmanagement.item.dao.ItemRepository;
import project.stockmanagement.itemcategory.dao.ItemCategoryRepository;
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
	private final ItemCategoryRepository itemCategoryRepository;
	private final RedisItemStockRepository redisItemStockRepository;

	// 수정된 기획대로 가져가기
	public OrderToEmployeeResponse dispatchWaitedOrderToEmployee(Long orderId, Long employeeId) {
		Order findOrder = orderRepository.findById(orderId);

		orderRepository.update(orderId,
			findOrder.toUpdateOrderWhenDispatchToEmployee(employeeId, OrderStatus.PROCESS));

		return OrderToEmployeeResponse.of(orderId, orderDetailRepository.findByOrderId(orderId));
	}

	// 보상 트랜잭션 적용
	public Long completeOrder(Long orderId, Long employeeId) {
		// Redis 재고에서 먼저 에러 발생 시 뒤의 MySQL 에 쿼리 요청하는 작업에 접근하지 않습니다.
		List<OrderDetail> findOrderDetails = orderDetailRepository.findByOrderId(orderId);

		redisItemStockRepository.decreaseItemStock(findOrderDetails);

		Order findOrder = orderRepository.findById(orderId);
		orderRepository.update(orderId, findOrder.toUpdateOrderWhenComplete(OrderStatus.COMPLETE, employeeId));

		increaseItemPackingCount(employeeId);

		return orderId;
	}

	@Bulkhead(name = "increaseItemPackingCount", type = Bulkhead.Type.THREADPOOL)
	private void increaseItemPackingCount(Long employeeId) {
		CompletableFuture.supplyAsync(() -> employeeRepository.findById(employeeId))
			.thenAccept(e -> employeeRepository.update(employeeId, e.increaseItemPackingCount()));
	}
}
