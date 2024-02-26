package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {
    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Item item1 = new Item("item1", 1, 10);
        Item item2 = new Item("item1", 2, 20);
        Item item3 = new Item("item1", 3, 30);

        //when
        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);
        //then

        Item findItem1 = itemRepository.findById(item1.getId());
        assertEquals(item1, findItem1);
    }

    @Test
    void findAll() {
        //given
        Item item1 = new Item("item1", 1, 10);
        Item item2 = new Item("item1", 2, 20);
        Item item3 = new Item("item1", 3, 30);

        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);
        //when
        List<Item> result = itemRepository.findAll();
        //then
        Assertions.assertThat(result).contains(item1);
        Assertions.assertThat(result).contains(item2);
        Assertions.assertThat(result).contains(item3);
    }

    @Test
    void updateItem() {
        //given
        Item item1 = new Item("item1", 1, 10);
        Item savedItem = itemRepository.save(item1);
        Long itemId = savedItem.getId();
        //when
        Item updateParam = new Item("item2", 2, 20);
        itemRepository.update(itemId, updateParam);
        //then
        Item findItem = itemRepository.findById(itemId);
        assertEquals(findItem.getItemName(), "item2");
        assertEquals(findItem.getPrice(), 2);
        assertEquals(findItem.getQuantity(), 20);
    }
}