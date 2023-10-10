package project.stockmanagement.item.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.stockmanagement.common.basewrapper.ApiResult;
import project.stockmanagement.item.service.ItemService;
import project.stockmanagement.item.service.response.ItemResponse;
import project.stockmanagement.item.web.request.ItemCreateRequest;
import project.stockmanagement.item.web.request.ItemUpdateRequest;

@RestController
@RequiredArgsConstructor
public class ItemController {

	private final ItemService itemService;

	@PostMapping("/api/v1/item")
	public ApiResult<ItemResponse> createItem(@Valid @RequestBody ItemCreateRequest request) {
		ItemResponse itemResponse = itemService.createItem(request.toServiceRequest());

		return ApiResult.onSuccess(itemResponse);
	}

	@GetMapping("/api/v1/item/{id}")
	public ApiResult<ItemResponse> findItem(@PathVariable Long id) {
		ItemResponse itemResponse = itemService.findItem(id);

		return ApiResult.onSuccess(itemResponse);
	}

	@GetMapping("/api/v1/item/item_category_id/{id}")
	public ApiResult<List<ItemResponse>> findItemByItemCategoryId(@PathVariable Integer id) {
		List<ItemResponse> itemResponses = itemService.findItemByItemCategoryId(id);

		return ApiResult.onSuccess(itemResponses);
	}

	@PostMapping("/api/v1/item/{id}")
	public ApiResult<Long> changeItemInfo(@PathVariable Long id, @Valid @RequestBody ItemUpdateRequest request) {
		Long updateId = itemService.changeItemInfo(id, request.toServiceRequest());

		return ApiResult.onSuccess(updateId);
	}
}
