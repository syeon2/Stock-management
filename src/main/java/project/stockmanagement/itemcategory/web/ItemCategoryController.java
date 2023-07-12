package project.stockmanagement.itemcategory.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.stockmanagement.common.basewrapper.ApiResult;
import project.stockmanagement.itemcategory.service.ItemCategoryService;
import project.stockmanagement.itemcategory.service.response.ItemCategoryResponse;
import project.stockmanagement.itemcategory.web.request.ItemCategoryCreateRequest;

@RestController
@RequiredArgsConstructor
public class ItemCategoryController {

	private final ItemCategoryService itemCategoryService;

	@PostMapping("/api/v1/itemcategory")
	public ApiResult<ItemCategoryResponse> createItemCategory(@Valid @RequestBody ItemCategoryCreateRequest request) {
		ItemCategoryResponse itemCategory = itemCategoryService.createItemCategory(request.toServiceRequest());

		return ApiResult.onSuccess(itemCategory);
	}

	@GetMapping("/api/v1/itemcategory/{id}")
	public ApiResult<ItemCategoryResponse> findItemCategoryById(@PathVariable Integer id) {
		ItemCategoryResponse itemCategory = itemCategoryService.findItemCategory(id);

		return ApiResult.onSuccess(itemCategory);
	}

	@GetMapping("/api/v1/itemcategory/center/{id}")
	public ApiResult<List<ItemCategoryResponse>> findItemCategoryByCenterId(@PathVariable Integer id) {
		List<ItemCategoryResponse> categories = itemCategoryService.findItemCategoriesByCenterId(id);

		return ApiResult.onSuccess(categories);
	}
}
