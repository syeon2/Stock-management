package project.stockmanagement.order.dao.mybatis;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import project.stockmanagement.common.basewrapper.CombinedEntity;
import project.stockmanagement.order.dao.domain.Order;

@Mapper
public interface OrderMapper {

	void save(CombinedEntity<Order> orders);

	Optional<Order> findById(Long id);

	@Select("select id, order_status, total_count, center_id, employee_id from orders where order_status = 'WAITING' limit 1 for update")
	Optional<Order> findWaitingStatusOrder();

	void update(@Param("id") Long id, @Param("updateParam") CombinedEntity<Order> updateOrders);

	void delete(Long id);
}
