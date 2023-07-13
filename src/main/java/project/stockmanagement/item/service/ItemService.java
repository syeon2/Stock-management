package project.stockmanagement.item.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.stockmanagement.item.dao.ItemRepository;
import project.stockmanagement.item.dao.domain.Item;
import project.stockmanagement.item.service.request.ItemCreateServiceRequest;
import project.stockmanagement.item.service.request.ItemUpdateServiceRequest;
import project.stockmanagement.item.service.response.ItemResponse;

@Service
@RequiredArgsConstructor
public class ItemService {

	private final ItemRepository itemRepository;

	public ItemResponse createItem(ItemCreateServiceRequest request) {
		Item item = request.toDomain(0);
		Item savedItem = itemRepository.save(item);

		return ItemResponse.of(savedItem);
	}

	public ItemResponse findItem(Long id) {
		Item findItem = itemRepository.findById(id);

		return ItemResponse.of(findItem);
	}

	public List<ItemResponse> findItemByItemCategoryId(Integer id) {
		List<Item> findItemByItemCategoryId = itemRepository.findByItemCategoryId(id);

		return findItemByItemCategoryId.stream()
			.map(ItemResponse::of)
			.collect(Collectors.toList());
	}

	public Long changeItemInfo(Long id, ItemUpdateServiceRequest request) {
		Item item = request.toDomain();

		return itemRepository.update(id, item);
	}
}
