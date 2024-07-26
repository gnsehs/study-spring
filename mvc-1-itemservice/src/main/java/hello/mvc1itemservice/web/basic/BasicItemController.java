package hello.mvc1itemservice.web.basic;

import hello.mvc1itemservice.domain.item.Item;
import hello.mvc1itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    /**
     * addForm에 있는 name으로 넘어옴
     *
     * @return
     */
//    @PostMapping("/add")
    public String addItemV1(
            @RequestParam String itemName,
            @RequestParam Integer price,
            @RequestParam Integer quantity,
            Model model
    ) {
        Item item = new Item(itemName, price, quantity);
        itemRepository.save(item);
        model.addAttribute("item", item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model) {

        itemRepository.save(item);
//        model.addAttribute("item", item); -> @ModelAttribute에 name속성을 주면 자동으로 처리 // Model 객체도 파라미터로 받지 않아도 됨
        return "basic/item";
    }

    /**
     * Class 명 Item -> item 으로 바꿔서 모델 명으로 넣는다 e.g. HelloData -> helloData
     * @param item
     * @return
     */
//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {

        itemRepository.save(item);
//        model.addAttribute("item", item); -> @ModelAttribute에 name속성을 주면 자동으로 처리 // Model 객체도 파라미터로 받지 않아도 됨
        return "basic/item";
    }

    /**
     * @ModelAttribute 조차도 생략 가능
     * @param item
     * @return
     */
//    @PostMapping("/add")
    public String addItemV4(Item item) {

        itemRepository.save(item);
//        model.addAttribute("item", item); -> @ModelAttribute에 name속성을 주면 자동으로 처리 // Model 객체도 파라미터로 받지 않아도 됨
        return "basic/item";
    }

    /**
     * PRG 패턴 적용하기
     * @param item
     * @return
     */
//    @PostMapping("/add")
    public String addItemV5(Item item) {

        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    /**
     * redierctAttirbute사용하기
     * @param item
     * @return
     */
    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {

        Item saveditem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", saveditem.getId());
        redirectAttributes.addAttribute("status", true); // url에 들어가지 않은 attribute는 쿼리 파라미터로 들어감
        return "redirect:/basic/items/{itemId}";
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    /**
     * 실제 edit 로직은 여기서
     * redirect를 했으므로 경로가 바뀐다.
     * redirect를 했으므로 모델을 따로 만들 필요가없다.
     * 상품 하나의 정보를 보여주는 컨트롤러 쪽에서 모델을 만든다.
     */

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, Item item) {
        itemRepository.update(itemId,item);
        return "redirect:/basic/items/{itemId}"; // pathvariable에 있는거 여기서도 사용 가능
    }


    /*
    테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {

        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
