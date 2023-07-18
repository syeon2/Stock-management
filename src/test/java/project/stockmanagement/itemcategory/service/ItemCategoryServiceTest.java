package project.stockmanagement.itemcategory.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import project.stockmanagement.itemcategory.dao.ItemCategoryRepository;
import project.stockmanagement.itemcategory.dao.domain.ItemCategory;
import project.stockmanagement.itemcategory.service.request.ItemCategoryCreateServiceRequest;
import project.stockmanagement.itemcategory.service.response.ItemCategoryResponse;

@ActiveProfiles("test")
@SpringBootTest
class wItemCategoryServiceTest {

	@Autowired
	private ItemCategoryService itemCategoryService;

	@Autowired
	private ItemCategoryRepository itemCategoryRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@AfterEach
	void after() {
		jdbcTemplate.execute("delete from item_category");
		jdbcTemplate.execute("alter table item_category auto_increment = 1");
	}

	@Test
	@DisplayName("상품 카테고리를 생성하는 요청을 받습니다.")
	void createItemCategoryRequest() {
		// given
		ItemCategoryCreateServiceRequest request = ItemCategoryCreateServiceRequest.builder()
			.name("신선 식품")
			.centerId(1)
			.build();

		// when
		ItemCategoryResponse savedItemCategory = itemCategoryService.createItemCategory(request);

		// then
		assertThat(savedItemCategory)
			.extracting("id", "name", "centerId")
			.contains(1, "신선 식품", 1);
	}

	@Test
	@DisplayName("아이디를 사용하여 상품 카테고리를 조회합니다.")
	void findItemCategoryById() {
		// given
		ItemCategory itemCategory = createItemCategory("신선 식품", 1);

		itemCategoryRepository.save(itemCategory);

		// when
		ItemCategoryResponse findItemCategory = itemCategoryService.findItemCategory(1);

		// then
		assertThat(findItemCategory)
			.extracting("id", "name", "centerId")
			.contains(1, "신선 식품", 1);
	}

	@Test
	@DisplayName("센터 아이디를 사용하여 상품 카테고리 목록을 조회합니다.")
	void findCategoriesByCenterId() {
		// given
		int centerId = 1;
		ItemCategory itemCategory1 = createItemCategory("신선 식품", centerId);
		ItemCategory itemCategory2 = createItemCategory("냉동 식품", centerId);
		ItemCategory itemCategory3 = createItemCategory("가전 제품", centerId);

		itemCategoryRepository.save(itemCategory1);
		itemCategoryRepository.save(itemCategory2);
		itemCategoryRepository.save(itemCategory3);

		// when
		List<ItemCategoryResponse> categories = itemCategoryService.findItemCategoriesByCenterId(centerId);

		// then
		assertThat(categories).hasSize(3)
			.extracting("id", "name", "centerId")
			.containsExactlyInAnyOrder(
				tuple(1, "신선 식품", centerId),
				tuple(2, "냉동 식품", centerId),
				tuple(3, "가전 제품", centerId)
			);
	}

	private static ItemCategory createItemCategory(String name, int centerId) {
		ItemCategory itemCategory = ItemCategory.builder()
			.name(name)
			.centerId(centerId)
			.build();
		return itemCategory;
	}
}
