package project.stockmanagement.item.dao.mybatis;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.stockmanagement.common.basewrapper.CombinedEntity;
import project.stockmanagement.item.dao.ItemRepository;
import project.stockmanagement.item.dao.domain.Item;

@Repository
@RequiredArgsConstructor
public class MyBatisItemRepository implements ItemRepository {

	private final ItemMapper itemMapper;

	@Override
	public Item save(Item item) {
		CombinedEntity<Item> createItem = CombinedEntity.toCreateData(item);
		itemMapper.save(createItem);

		return createItem.getEntity();
	}

	@Override
	public Item findById(Long id) {
		Optional<Item> findItemOptional = itemMapper.findById(id);

		return findItemOptional.orElseThrow(
			() -> new NoSuchElementException("아이템이 존재하지 않습니다."));
	}

	@Override
	public List<Item> findByItemCategoryId(Integer id) {
		return itemMapper.findByItemCategoryId(id);
	}

	@Override
	public Long update(Long id, Item updateItem) {
		CombinedEntity<Item> updateData = CombinedEntity.toUpdateData(updateItem);
		itemMapper.update(id, updateData);

		return id;
	}

	@Override
	public void delete(Long id) {
		itemMapper.delete(id);
	}
}
