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
import project.stockmanagement.order.service.request.OrderItem;
import project.stockmanagement.order.service.response.OrderResponse;

@Service
@RequiredArgsConstructor
public class OrderToCenterService {

	private final OrderRepository orderRepository;
	private final OrderDetailRepository orderDetailRepository;

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

	private Long saveOrderEntityToDB(OrderCreateServiceRequest request) {
		Order order = request.toOrderDomain(OrderStatus.WAITING);
		return orderRepository.save(order);
	}

	private void saveOrderDetailsToDB(OrderCreateServiceRequest request, Long orderId) {
		List<OrderDetail> orderDetails = request.toOrderDetails(orderId);
		orderDetails.forEach(orderDetailRepository::save);
	}

	private OrderResponse combineOrderAndOrderDetailsToOrderResponse(Order order, List<OrderDetail> orderDetails) {
		List<OrderItem> orderItems = orderDetails.stream()
			.map(detail -> OrderItem.builder()
				.id(detail.getId())
				.name(detail.getName())
				.count(detail.getCount())
				.build()
			).collect(Collectors.toList());

		Integer totalCount = orderDetails.stream()
			.mapToInt(OrderDetail::getCount)
			.sum();

		return OrderResponse.builder()
			.orderStatus(order.getOrderStatus())
			.totalCount(totalCount)
			.centerId(order.getCenterId())
			.employeeId(order.getEmployeeId())
			.orderedItems(orderItems)
			.build();
	}
}
