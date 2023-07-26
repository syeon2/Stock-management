package project.stockmanagement.item.dao.mybatis;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import project.stockmanagement.item.dao.ItemRepository;
import project.stockmanagement.item.dao.domain.Item;

@ActiveProfiles("test")
@SpringBootTest
class MyBatisItemRepositoryTest {

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@AfterEach
	public void after() {
		jdbcTemplate.execute("delete from item");
		jdbcTemplate.execute("alter table item auto_increment = 1");
	}

	@Test
	@DisplayName("상품을 저장합니다.")
	void saveItem() {
		// given
		Item item = createItem("itemA", 0, 1);

		// when
		Item savedItem = itemRepository.save(item);

		// then
		assertThat(savedItem)
			.extracting("id", "name", "quantity", "itemCategoryId")
			.contains(1L, "itemA", 0, 1);
	}

	@Test
	@DisplayName("상품 아이디로 상품을 조회합니다.")
	void findById() {
		// given
		Item item = createItem("itemA", 0, 1);
		Item savedItem = itemRepository.save(item);

		Long id = savedItem.getId();

		// when
		Item findItem = itemRepository.findById(id);

		// then
		assertThat(findItem)
			.extracting("id", "name", "quantity", "itemCategoryId")
			.contains(id, "itemA", 0, 1);
	}

	@Test
	@DisplayName("상품이 없을 경우 예외를 반환합니다.")
	void findByIdWithoutItem() {
		// given  // when  // then
		assertThatThrownBy(() -> itemRepository.findById(1L))
			.isInstanceOf(NoSuchElementException.class);
	}

	@Test
	@DisplayName("상품 카테고리로 상품들을 조회합니다.")
	void findByItemCategoryId() {
		// given
		int itemCategoryId = 1;
		Item item1 = createItem("itemA", 0, itemCategoryId);
		Item item2 = createItem("itemB", 0, itemCategoryId);
		Item item3 = createItem("itemC", 0, itemCategoryId);

		itemRepository.save(item1);
		itemRepository.save(item2);
		itemRepository.save(item3);

		// when
		List<Item> findItemsByCategoryId = itemRepository.findByItemCategoryId(itemCategoryId);

		// then
		assertThat(findItemsByCategoryId).hasSize(3)
			.extracting("id", "name", "quantity", "itemCategoryId")
			.containsExactlyInAnyOrder(
				tuple(1L, "itemA", 0, itemCategoryId),
				tuple(2L, "itemB", 0, itemCategoryId),
				tuple(3L, "itemC", 0, itemCategoryId)
			);
	}

	@Test
	@DisplayName("상품 정보를 수정합니다.")
	void update() {
		// given
		Item item = createItem("itemA", 1, 1);
		Item savedItem = itemRepository.save(item);

		Long updateItemId = savedItem.getId();
		String updateItemName = "itemB";
		Item updateItem = createItem(updateItemName, 1, 1);

		// when
		Long updated = itemRepository.update(updateItemId, updateItem);

		// then
		assertThat(updated).isEqualTo(updateItemId);

		Item findItem = itemRepository.findById(updateItemId);
		assertThat(findItem)
			.extracting("id", "name", "quantity", "itemCategoryId")
			.contains(1L, updateItemName, 1, 1);
	}

	@Test
	@DisplayName("상품 정보를 삭제합니다.")
	void delete() {
		// given
		Item item = createItem("itemA", 1, 1);
		Item savedItem = itemRepository.save(item);

		Long id = savedItem.getId();

		Item findItem = itemRepository.findById(id);
		assertThat(findItem)
			.extracting("id", "name", "quantity", "itemCategoryId")
			.contains(1L, "itemA", 1, 1);
		
		// when
		itemRepository.delete(id);

		// then
		assertThatThrownBy(() -> itemRepository.findById(id))
			.isInstanceOf(NoSuchElementException.class);
	}

	private Item createItem(String name, int quantity, int itemCategoryId) {
		return Item.builder()
			.name(name)
			.quantity(quantity)
			.itemCategoryId(itemCategoryId)
			.build();
	}
}
