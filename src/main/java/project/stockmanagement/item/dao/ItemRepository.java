package project.stockmanagement.item.dao;

import java.util.List;

import project.stockmanagement.item.dao.domain.Item;

public interface ItemRepository {

	Item save(Item item);

	Item findById(Long id);

	List<Item> findByItemCategoryId(Integer id);

	Long update(Long id, Item updateItem);

	void delete(Long id);

}
