package project.stockmanagement.itemcategory.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.stockmanagement.itemcategory.dao.ItemCategoryRepository;
import project.stockmanagement.itemcategory.dao.domain.ItemCategory;
import project.stockmanagement.itemcategory.service.request.ItemCategoryCreateServiceRequest;
import project.stockmanagement.itemcategory.service.response.ItemCategoryResponse;

@Service
@RequiredArgsConstructor
public class ItemCategoryService {

	private final ItemCategoryRepository itemCategoryRepository;

	public ItemCategoryResponse createItemCategory(ItemCategoryCreateServiceRequest request) {
		ItemCategory itemCategory = ItemCategory.createFromServiceRequest(request);
		ItemCategory savedItemCategory = itemCategoryRepository.save(itemCategory);

		return ItemCategoryResponse.of(savedItemCategory);
	}

	public ItemCategoryResponse findItemCategory(Integer id) {
		ItemCategory findItemCategory = itemCategoryRepository.findById(id);

		return ItemCategoryResponse.of(findItemCategory);
	}

	public List<ItemCategoryResponse> findItemCategoriesByCenterId(Integer id) {
		List<ItemCategory> findItemCategories = itemCategoryRepository.findByCenterId(id);

		return findItemCategories.stream()
			.map(ItemCategoryResponse::of)
			.collect(Collectors.toList());
	}
}
