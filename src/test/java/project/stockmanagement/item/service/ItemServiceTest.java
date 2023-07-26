package project.stockmanagement.item.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import project.stockmanagement.item.dao.ItemRepository;
import project.stockmanagement.item.dao.domain.Item;
import project.stockmanagement.item.service.request.ItemCreateServiceRequest;
import project.stockmanagement.item.service.request.ItemUpdateServiceRequest;
import project.stockmanagement.item.service.response.ItemResponse;

@ActiveProfiles("test")
@SpringBootTest
class ItemServiceTest {

	@Autowired
	private ItemService itemService;

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
	@DisplayName("상품을 생성합니다.")
	void saveItem() {
		// given
		ItemCreateServiceRequest request = ItemCreateServiceRequest.builder()
			.name("itemA")
			.itemCategoryId(1)
			.build();

		// when
		ItemResponse itemResponse = itemService.createItem(request);

		// then
		assertThat(itemResponse)
			.extracting("id", "name", "quantity", "itemCategoryId")
			.contains(1L, "itemA", 0, 1);
	}

	@Test
	@DisplayName("상품을 조회합니다.")
	void findItem() {
		// given
		Item item = createItem("itemA", 0, 1);
		Item savedItem = itemRepository.save(item);

		Long id = savedItem.getId();

		// when
		ItemResponse itemResponse = itemService.findItem(id);

		// then
		assertThat(itemResponse)
			.extracting("id", "name", "quantity", "itemCategoryId")
			.contains(1L, "itemA", 0, 1);
	}

	@Test
	@DisplayName("상품 카테고리 아이디를 사용하여 상품들을 조회합니다.")
	void findItemsByCategoryId() {
		// given
		int itemCategoryId = 1;
		Item item1 = createItem("itemA", 0, itemCategoryId);
		Item item2 = createItem("itemB", 0, itemCategoryId);
		Item item3 = createItem("itemC", 0, itemCategoryId);

		itemRepository.save(item1);
		itemRepository.save(item2);
		itemRepository.save(item3);

		// when
		List<ItemResponse> itemResponses = itemService.findItemByItemCategoryId(itemCategoryId);

		// then
		assertThat(itemResponses)
			.extracting("id", "name", "quantity", "itemCategoryId")
			.containsExactlyInAnyOrder(
				tuple(1L, "itemA", 0, 1),
				tuple(2L, "itemB", 0, 1),
				tuple(3L, "itemC", 0, 1)
			);
	}

	@Test
	@DisplayName("상품 정보를 수정합니다.")
	void changeItemInfo() {
		// given
		Item item = createItem("itemA", 0, 1);
		Item savedItem = itemRepository.save(item);

		Long id = savedItem.getId();
		ItemUpdateServiceRequest request = ItemUpdateServiceRequest.builder()
			.name("itemB")
			.quantity(100)
			.itemCategoryId(2)
			.build();

		// when
		Long updateId = itemService.changeItemInfo(id, request);

		// then
		assertThat(updateId).isEqualTo(id);

		Item findItem = itemRepository.findById(id);
		assertThat(findItem)
			.extracting("id", "name", "quantity", "itemCategoryId")
			.contains(1L, "itemB", 100, 2);
	}

	private Item createItem(String name, int quantity, int itemCategoryId) {
		return Item.builder()
			.name(name)
			.quantity(quantity)
			.itemCategoryId(itemCategoryId)
			.build();
	}
}
