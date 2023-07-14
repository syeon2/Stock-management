package project.stockmanagement.order.dao;

import project.stockmanagement.order.dao.domain.Order;

public interface OrderRepository {

	Order save(Order orders);

	Order findById(Long id);

	Long update(Long id, Order updateOrders);

	void delete(Long id);
}
