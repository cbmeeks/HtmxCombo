package dev.cbmeeks.combo.hotel;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hotels")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping({"/search", "/search/"})
    public String search(@RequestParam @Nullable String search, Model model, Pageable pageable) {
        Page<Hotel> hotels = hotelService.search(search, pageable);
        model.addAttribute("records", hotels);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("search", search);

        return "fragments/combo-items";
    }


    @GetMapping({"/select/{id}", "/select/{id}/"})
    public String select(@PathVariable int id, Model model, HttpServletResponse response) {
        model.addAttribute("id", id);
        model.addAttribute("value", hotelService.findById(id).getName());
        response.addHeader("HX-Trigger-After-Swap", "bindCombos");

        return "fragments/combo";
    }
}
