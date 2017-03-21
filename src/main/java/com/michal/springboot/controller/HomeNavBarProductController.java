package com.michal.springboot.controller;

import com.michal.springboot.domain.Car;
import com.michal.springboot.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mike on 2017-03-15.
 */
@Controller
public class HomeNavBarProductController {

    private ProductService productService;

    public HomeNavBarProductController(ProductService productSerivce) {
        this.productService = productSerivce;
    }

    @RequestMapping("/")
    public String welcome(Model model) {

        try {
            List<String> picsName = new ArrayList<>();
            List<Car> carList = productService.getCarForCarousel();
            carList.forEach(c->picsName.add("/cars/"+c.getCarId()+".png"));
            model.addAttribute("list",carList );
            model.addAttribute("pics",picsName);
            model.addAttribute("aa","name");

        } catch (Exception e) {
            return "welcome";
        }

        return "welcome";
    }

    @RequestMapping("/carrent/{min}/{max}/{name}")
    public String searchCar(Model model, @PathVariable int min, @PathVariable int max,
                            @RequestParam("name") String name) {

        model.addAttribute("cars", productService.getProductsByFilter(min, max, name));

        return "rent";

    }

    @RequestMapping("/contacts")
    public String contacts() {

        return "contacts";
    }

    @RequestMapping("/carrent")
    public String carRent(Model model) {

        try {
            model.addAttribute("cars", productService.findAll());
        } catch (Exception e) {
            return "rent";
        }

        return "rent";
    }
}
