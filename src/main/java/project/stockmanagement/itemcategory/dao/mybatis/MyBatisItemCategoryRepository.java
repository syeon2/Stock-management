package project.stockmanagement.itemcategory.dao.mybatis;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.stockmanagement.common.basewrapper.CombinedEntity;
import project.stockmanagement.itemcategory.dao.ItemCategoryRepository;
import project.stockmanagement.itemcategory.dao.domain.ItemCategory;

@Repository
@RequiredArgsConstructor
public class MyBatisItemCategoryRepository implements ItemCategoryRepository {

	private final ItemCategoryMapper itemCategoryMapper;

	@Override
	public ItemCategory save(ItemCategory itemCategory) {
		CombinedEntity<ItemCategory> createItemCategory = CombinedEntity.toCreateData(itemCategory);
		itemCategoryMapper.save(createItemCategory);

		return createItemCategory.getEntity();
	}

	@Override
	public ItemCategory findById(Integer id) {
		Optional<ItemCategory> findItemCategoryOptional = itemCategoryMapper.findById(id);

		return findItemCategoryOptional.orElseThrow(
			() -> new NoSuchElementException("상품 카테고리가 존재하지 않습니다."));
	}

	@Override
	public List<ItemCategory> findByCenterId(Integer centerId) {
		return itemCategoryMapper.findByCenterId(centerId);
	}

	@Override
	public void delete(Integer id) {
		itemCategoryMapper.delete(id);
	}

}
