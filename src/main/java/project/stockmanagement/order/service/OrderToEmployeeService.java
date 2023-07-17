package project.stockmanagement.order.service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.stockmanagement.common.config.RedisOrderLockConfig;
import project.stockmanagement.order.dao.OrderDetailRepository;
import project.stockmanagement.order.dao.OrderRepository;
import project.stockmanagement.order.dao.domain.Order;
import project.stockmanagement.order.dao.domain.OrderDetail;
import project.stockmanagement.order.dao.domain.OrderStatus;
import project.stockmanagement.order.service.request.OrderItem;
import project.stockmanagement.order.service.response.OrderToEmployeeResponse;

@Service
@RequiredArgsConstructor
public class OrderToEmployeeService {

	private final OrderRepository orderRepository;
	private final OrderDetailRepository orderDetailRepository;
	private final RedisTemplate<String, String> redisOrderLockTemplate;

	// 주문 관리 테이블에서 주문 상태 Waiting 인 값 하나 찾아서 상태를 Processing 으로 변경
	// -> 이 때 분산 서버에서 중복 데이터 조회 및 처리 가능하니 Redis DistributionLock 사용 -> Redis에 <Lock, OrderId> 형태로 저장
	// 주문 관리 테이블에 근로자 아이디 넣기
	// 모든 처리가 완료된 이후 락 해제 (Expired Time 10_000 걸기)

	// 만약 락이 걸려있는 데이터가 조회된다면

	public OrderToEmployeeResponse dispatchWaitedOrderToEmployee(Long employeeId) {
		// 주문 조회
		Order waitingStatusOrder = orderRepository.findWaitingStatusOrder();
		Long orderId = waitingStatusOrder.getId();

		// Redis Lock 걸기
		Boolean isLocked = redisOrderLockTemplate.opsForValue()
			.setIfAbsent(orderId.toString(), RedisOrderLockConfig.ORDER_DISPATCH_LOCK,
				Duration.ofMillis(100));

		if (Boolean.TRUE.equals(isLocked)) {
			// 주문 데이터 Process로 업데이트
			orderRepository.update(orderId,
				waitingStatusOrder.toUpdateOrderWhenDispatchToEmployee(employeeId, OrderStatus.PROCESS));

			// get order Detail And Packaging
			List<OrderDetail> findOrderDetails = orderDetailRepository.findByOrderId(orderId);

			List<OrderItem> collect = findOrderDetails.stream()
				.map(detail -> OrderItem.builder()
					.id(detail.getId())
					.name(detail.getName())
					.count(detail.getCount())
					.build())
				.collect(Collectors.toList());

			return new OrderToEmployeeResponse(collect);
		} else {
			return dispatchWaitedOrderToEmployee(employeeId);
		}
	}
}
