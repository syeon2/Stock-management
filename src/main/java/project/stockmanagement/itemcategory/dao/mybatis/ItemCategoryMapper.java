package project.stockmanagement.itemcategory.dao.mybatis;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import project.stockmanagement.common.basewrapper.CombinedEntity;
import project.stockmanagement.itemcategory.dao.domain.ItemCategory;

@Mapper
public interface ItemCategoryMapper {

	void save(CombinedEntity<ItemCategory> combinedEntity);

	Optional<ItemCategory> findById(Integer id);

	List<ItemCategory> findByCenterId(Integer centerId);

	void delete(Integer id);
}
