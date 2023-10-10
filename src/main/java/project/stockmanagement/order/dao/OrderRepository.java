package project.stockmanagement.order.dao;

import java.util.List;

import project.stockmanagement.order.dao.domain.Order;

public interface OrderRepository {

	Long save(Order orders);

	Order findById(Long id);

	List<Long> findCompletedOrdersId();

	Long update(Long id, Order updateOrders);

	void delete(Long id);
}
