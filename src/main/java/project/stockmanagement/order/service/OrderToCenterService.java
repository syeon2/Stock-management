package project.stockmanagement.order.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.stockmanagement.order.dao.OrderDetailRepository;
import project.stockmanagement.order.dao.OrderRepository;
import project.stockmanagement.order.dao.domain.Order;
import project.stockmanagement.order.dao.domain.OrderDetail;
import project.stockmanagement.order.dao.domain.OrderStatus;
import project.stockmanagement.order.service.request.OrderCreateServiceRequest;
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

		return OrderResponse.combineOrderAndOrderDetailsToOrderResponse(findOrder, findOrderDetails);
	}

	public Long checkCompletedItemQuantity(Long itemId) {
		List<Long> completedOrdersId = orderRepository.findCompletedOrdersId();

		return (long)completedOrdersId.stream()
			.map(orderDetailRepository::findByOrderId)
			.flatMap(details -> details.stream()
				.filter(e -> Objects.equals(e.getItemId(), itemId)))
			.mapToInt(OrderDetail::getCount)
			.sum();
	}

	private Long saveOrderEntityToDB(OrderCreateServiceRequest request) {
		Order order = request.toOrderDomain(OrderStatus.WAITING);
		return orderRepository.save(order);
	}

	private void saveOrderDetailsToDB(OrderCreateServiceRequest request, Long orderId) {
		List<OrderDetail> orderDetails = OrderDetail.createFromServiceRequest(request.getOrderedItems(), orderId);
		orderDetails.forEach(orderDetailRepository::save);
	}
}
