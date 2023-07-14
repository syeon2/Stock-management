package project.stockmanagement.order.dao.domain.mybatis;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import project.stockmanagement.common.basewrapper.CombinedEntity;
import project.stockmanagement.order.dao.domain.Order;

@Mapper
public interface OrderMapper {

	void save(CombinedEntity<Order> orders);

	Optional<Order> findById(Long id);

	void update(@Param("id") Long id, @Param("updateParam") CombinedEntity<Order> updateOrders);

	void delete(Long id);
}
