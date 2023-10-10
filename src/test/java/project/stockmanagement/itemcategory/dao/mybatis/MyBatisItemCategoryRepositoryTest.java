package project.stockmanagement.itemcategory.dao.mybatis;

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

import project.stockmanagement.itemcategory.dao.ItemCategoryRepository;
import project.stockmanagement.itemcategory.dao.domain.ItemCategory;

@ActiveProfiles("test")
@SpringBootTest
class MyBatisItemCategoryRepositoryTest {

	@Autowired
	private ItemCategoryRepository itemCategoryRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@AfterEach
	public void after() {
		jdbcTemplate.execute("delete from item_category");
		jdbcTemplate.execute("alter table item_category auto_increment = 1");
	}

	@Test
	@DisplayName("상품 카테고리를 생성합니다.")
	void saveItemCategory() {
		// given
		String itemCategoryName = "신선 식품";
		ItemCategory itemCategory = createItemCategory(itemCategoryName, 1);

		// when
		ItemCategory savedItemCategory = itemCategoryRepository.save(itemCategory);

		// then
		assertThat(savedItemCategory)
			.extracting("id", "name", "centerId")
			.contains(1, itemCategoryName, 1);
	}

	@Test
	@DisplayName("아이디를 사용하여 상품 카테고리를 조회합니다.")
	void findById() {
		// given
		ItemCategory itemCategory = createItemCategory("신선 식품", 1);
		ItemCategory savedItemCategory = itemCategoryRepository.save(itemCategory);

		Integer id = savedItemCategory.getId();

		// when
		ItemCategory findItemCategory = itemCategoryRepository.findById(id);

		// then
		assertThat(findItemCategory)
			.extracting("id", "name", "centerId")
			.contains(1, "신선 식품", 1);
	}

	@Test
	@DisplayName("찾는 상품 카테고가 없으면 NoSuchElementException 예외를 던집니다.")
	void findByIdWhenNull() {
		// given  // when  // then
		assertThatThrownBy(() -> itemCategoryRepository.findById(1))
			.isInstanceOf(NoSuchElementException.class);
	}

	@Test
	@DisplayName("센터 아이디를 사용해 상품 카테고리들을 조회합니다.")
	void findByCenterId() {
		// given
		ItemCategory itemCategory1 = createItemCategory("신선 식품", 1);
		ItemCategory itemCategory2 = createItemCategory("냉동 식품", 1);
		ItemCategory itemCategory3 = createItemCategory("가전 제품", 1);

		itemCategoryRepository.save(itemCategory1);
		itemCategoryRepository.save(itemCategory2);
		itemCategoryRepository.save(itemCategory3);

		// when
		List<ItemCategory> findItemCategories = itemCategoryRepository.findByCenterId(1);

		// then
		assertThat(findItemCategories).hasSize(3)
			.extracting("id", "name", "centerId")
			.containsExactlyInAnyOrder(
				tuple(1, "신선 식품", 1),
				tuple(2, "냉동 식품", 1),
				tuple(3, "가전 제품", 1)
			);
	}

	@Test
	@DisplayName("상품 카테고리를 삭제합니다.")
	void delete() {
		// given
		ItemCategory itemCategory = createItemCategory("신선 식품", 1);
		itemCategoryRepository.save(itemCategory);

		// when
		itemCategoryRepository.delete(1);

		// then
		assertThatThrownBy(() -> itemCategoryRepository.findById(1))
			.isInstanceOf(NoSuchElementException.class);
	}

	private ItemCategory createItemCategory(String name, int centerId) {
		return ItemCategory.builder()
			.name(name)
			.centerId(centerId)
			.build();
	}
}
