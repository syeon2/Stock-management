package project.stockmanagement.order.dao;

import project.stockmanagement.order.dao.domain.Order;

public interface OrderRepository {

	Long save(Order orders);

	Order findById(Long id);

	Order findWaitingStatusOrder();

	Long update(Long id, Order updateOrders);

	void delete(Long id);
}
