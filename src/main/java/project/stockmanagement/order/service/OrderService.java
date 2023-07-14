package project.stockmanagement.order.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.stockmanagement.order.dao.OrderDetailRepository;
import project.stockmanagement.order.dao.OrderRepository;
import project.stockmanagement.order.dao.domain.Order;
import project.stockmanagement.order.dao.domain.OrderDetail;
import project.stockmanagement.order.dao.domain.OrderStatus;
import project.stockmanagement.order.service.request.OrderCreateServiceRequest;
import project.stockmanagement.order.service.request.OrderedItem;
import project.stockmanagement.order.service.response.OrderResponse;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final OrderDetailRepository orderDetailRepository;

	// TODO:: 성능 분석을 위해 단계별로 적용해볼 예정입니다.

	/**
	 * 1. 모든 처리를 단순한 하나의 트랜젝션으로 처리해보기
	 * 2. 하나의 트랜젝션으로 처리하되 주문 생성은 동기적으로 처리하고 (id값을 받아야서 주문 상세 내역에 추가해야하기 때문) 나머지 상세 내역은 비동기로 처리하기
	 */
	@Transactional
	public Long createOrder(OrderCreateServiceRequest request) {
		Long orderId = saveOrderEntityToDB(request);
		saveOrderDetailsToDB(request, orderId);

		return orderId;
	}

	public OrderResponse findOrderInfo(Long id) {
		Order findOrder = orderRepository.findById(id);
		List<OrderDetail> findOrderDetails = orderDetailRepository.findByOrderId(id);

		return combineOrderAndOrderDetailsToOrderResponse(findOrder, findOrderDetails);
	}

	// TODO:: 재고 처리 기능 (Employee -> Orders Stock Count)

	private void saveOrderDetailsToDB(OrderCreateServiceRequest request, Long orderId) {
		List<OrderDetail> orderDetails = request.toOrderDetails(orderId);
		orderDetails.forEach(orderDetailRepository::save);
	}

	private Long saveOrderEntityToDB(OrderCreateServiceRequest request) {
		Order order = request.toOrderDomain(OrderStatus.WAITING);
		return orderRepository.save(order);
	}

	private OrderResponse combineOrderAndOrderDetailsToOrderResponse(Order order, List<OrderDetail> orderDetails) {
		List<OrderedItem> orderItems = orderDetails.stream()
			.map(detail -> OrderedItem.builder()
				.id(detail.getId())
				.name(detail.getName())
				.count(detail.getCount())
				.build()
			).collect(Collectors.toList());

		return OrderResponse.builder()
			.orderStatus(order.getOrderStatus())
			.totalCount(orderDetails.size())
			.centerId(order.getCenterId())
			.employeeId(order.getEmployeeId())
			.orderedItems(orderItems)
			.build();
	}
}
