package project.stockmanagement.item.dao.mybatis;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import project.stockmanagement.common.basewrapper.CombinedEntity;
import project.stockmanagement.item.dao.domain.Item;

@Mapper
public interface ItemMapper {

	void save(CombinedEntity<Item> item);

	Optional<Item> findById(Long id);

	List<Item> findByItemCategoryId(Integer id);

	void update(@Param("id") Long id, @Param("updateParam") CombinedEntity<Item> updateItem);

	void delete(Long id);
}
