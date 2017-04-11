package com.michal.springboot.controller;

import com.michal.springboot.domain.Car;
import com.michal.springboot.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String welcome() {
        return "welcome";
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

    @RequestMapping(value="/rest/cars" ,method = RequestMethod.GET)
    public @ResponseBody List<Car> getCars(){

        return productService.getCarForCarousel();
    }
}
