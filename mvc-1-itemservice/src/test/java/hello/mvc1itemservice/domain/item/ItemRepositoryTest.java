package hello.mvc1itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {
    ItemRepository itemRepository = new ItemRepository();


    @AfterEach
    void afterEach() {
        itemRepository.clearStore(); // 매 테스트후 store clear
    }

    @Test
    void save() {
        // given
        Item item = new Item("itemA", 10000, 10);

        // when
        Item savedItem = itemRepository.save(item);

        // then
        Item findItem = itemRepository.findById(savedItem.getId());
        assertThat(savedItem).isEqualTo(findItem);
    }
    @Test
    void findAll() {
        // given
        Item item1 = new Item("itemA", 10000, 10);
        Item item2 = new Item("itemB", 10500, 15);

        itemRepository.save(item1);
        itemRepository.save(item2);
        // when
        List<Item> items = itemRepository.findAll();

        // then
        assertThat(items.size()).isEqualTo(2);
        assertThat(items).contains(item1, item2);

    }
    @Test
    void updateItem() {
        // given
        String newItemName = "newItem";
        Integer newPrice = 10200;
        Integer newQuantity = 30;

        Item item1 = new Item("itemA", 10000, 10);

        Item saved = itemRepository.save(item1);
        Long savedId = saved.getId();
        // when
        Item updateItem = new Item(newItemName, newPrice, newQuantity);

        itemRepository.update(saved.getId(), updateItem);
        // then
        Item findItem = itemRepository.findById(savedId);
        assertThat(findItem.getItemName()).isEqualTo(newItemName);
        assertThat(findItem.getPrice()).isEqualTo(newPrice);
        assertThat(findItem.getQuantity()).isEqualTo(newQuantity);
    }


}