package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.SaveCheck;
import hello.itemservice.domain.item.UpdateCheck;
import hello.itemservice.web.validation.form.ItemSaveForm;
import hello.itemservice.web.validation.form.ItemUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/validation/v4/items")
@RequiredArgsConstructor
public class ValidationItemControllerV4 {

    private final ItemRepository itemRepository;


    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v4/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v4/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v4/addForm";
    }




    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute("item") ItemSaveForm saveForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        // 특정한 필드가 하닌 복합 룰 검증
        if (saveForm.getPrice() != null && saveForm.getQuantity() != null) {
            int resultPrice = saveForm.getPrice() * saveForm.getQuantity();

            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin",new Object[]{10000, resultPrice},null);
            }
        }



        // 검증 실패 -> 다시 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            /*model.addAttribute("errors", errors);*/ // -> model에 담지 않아도 자동으로 뷰로 넘어감 !
            return "validation/v4/addForm";
        }

        // 성공 로직
        Item savedItem = itemRepository.save(
                new Item(saveForm.getItemName(), saveForm.getPrice(), saveForm.getQuantity())
        );
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v4/items/{itemId}";
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v4/editForm";
    }



    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @Validated(UpdateCheck.class) @ModelAttribute("item") ItemUpdateForm updateForm, BindingResult bindingResult) {
        // 특정한 필드가 하닌 복합 룰 검증
        if (updateForm.getPrice() != null && updateForm.getQuantity() != null) {
            int resultPrice = updateForm.getPrice() * updateForm.getQuantity();

            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin",new Object[]{10000, resultPrice},null);
            }
        }

        if (bindingResult.hasErrors()) {
            return "validation/v4/editForm";
        }


        itemRepository.update(itemId, new Item(updateForm.getItemName(), updateForm.getPrice(), updateForm.getQuantity()));
        return "redirect:/validation/v4/items/{itemId}";
    }


}

