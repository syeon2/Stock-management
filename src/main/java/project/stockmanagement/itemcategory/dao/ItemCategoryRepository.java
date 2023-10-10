package project.stockmanagement.itemcategory.dao;

import java.util.List;

import project.stockmanagement.itemcategory.dao.domain.ItemCategory;

public interface ItemCategoryRepository {

	ItemCategory save(ItemCategory itemCategory);

	ItemCategory findById(Integer id);

	List<ItemCategory> findByCenterId(Integer centerId);

	void delete(Integer id);
}
