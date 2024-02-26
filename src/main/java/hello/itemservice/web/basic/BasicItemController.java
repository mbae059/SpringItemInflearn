package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
@Slf4j
public class BasicItemController {
    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        log.info("item = {}", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName, @RequestParam Integer price, @RequestParam Integer quantity, Model model) {
        Item item = new Item(itemName, price, quantity);
        Item savedItem = itemRepository.save(item);
        log.info("item saved = {}", item);

        model.addAttribute("item", item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item) {
        Item savedItem = itemRepository.save(item);

        //생략 가능
        //model.addAttribute("item", item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {
        itemRepository.save(item);
        //생략 가능
        //model.addAttribute("item", item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV4(Item item) {
        Item savedItem = itemRepository.save(item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV5(Item item) {
        Item savedItem = itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }
    /**
     * For test
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
