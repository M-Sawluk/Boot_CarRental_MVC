package com.michal.springboot.controller;

import com.michal.springboot.domain.Car;
import com.michal.springboot.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(HomeNavBarProductController.class);

    @RequestMapping("/")
    public String welcome(Model model) {

        try {
            List<Car> carList = productService.getCarForCarousel();
            model.addAttribute("list",carList );
        } catch (Exception e) {
            return "welcome";
        }

        return "welcome";
    }

    @RequestMapping("/carrent/search/min/{min}/max/{max}/name/{name}")
    public String searchCar(Model model, @PathVariable int min, @PathVariable int max,
                            @PathVariable("name") String name) {


        model.addAttribute("cars", productService.getProductsByFilter(max, min, name));

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
