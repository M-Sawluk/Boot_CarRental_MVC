package com.michal.springboot.controller;

import java.io.File;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.michal.springboot.domain.*;
import com.michal.springboot.forms.CarStorageForm;
import com.michal.springboot.forms.OrderStatus;
import com.michal.springboot.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/adminPage")
public class AdminController {

    private ProductService productService;

    private UserService userService;

    private RentingPlaceService rentingPlaceService;

    private OrderService orderService;

    private CarStorageService carStorageService;

    public AdminController(ProductService productService, UserService userService,
                           RentingPlaceService rentingPlaceService,
                           OrderService orderService, CarStorageService carStorageService) {
        this.productService = productService;
        this.userService = userService;
        this.rentingPlaceService = rentingPlaceService;
        this.orderService = orderService;
        this.carStorageService = carStorageService;
    }

    @RequestMapping()
    public String adminHome(Model model) {

        model.addAttribute("newOrders", orderService.getOrdersToManage());

        return "adminPage";
    }

    @RequestMapping(value = "adminPage/addCar", method = RequestMethod.GET)
    public String addCar(Model model) {
        model.addAttribute("newCar", new Car());

        return "addCar";
    }

    @RequestMapping(value = "adminPage/addCar", method = RequestMethod.POST)
    public String processCar(@ModelAttribute("newCar") @Valid Car car, BindingResult bindingResult,
                             HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "adminPage/addCar";
        }

        MultipartFile productImage = car.getProductImage();
        String rootDirectory = request.getSession().getServletContext().getRealPath("/");

        if (productImage != null && !productImage.isEmpty()) {
            try {

                productImage.transferTo(new File(rootDirectory + "resources\\cars\\" + car.getCarId() + ".png"));
            } catch (Exception e) {
                throw new RuntimeException("Niepowodzenie podczas pr�by zapisu obrazka samochodu", e);
            }
        }

        productService.save(car);

        return "redirect:/adminPage";

    }

    @RequestMapping("adminPage/users/{page}")
    public String users(Model model, @RequestParam int page) {

        model.addAttribute("users", userService.getUserList(page));

        return "users";
    }

    @RequestMapping(value = "adminPage/orders", method = RequestMethod.GET)
    public String admin(Model model) {

        model.addAttribute("orders", orderService.getAllOrders());

        return "adminPage/orders";
    }

    @RequestMapping("/delete")
    public String deleteUser(@RequestParam("name") String name) {

        userService.deleteUser(name);

        return "redirect:/adminPage";
    }

    @RequestMapping("/change")
    public String changeRole(@RequestParam("name") String name) {
        User user = userService.findByUserName(name);
        String role;

        userService.changeUserRole(user);

        return "redirect:/adminPage";
    }

    @RequestMapping("/block")
    public String block(@RequestParam("name") String name) {
        User user = userService.findByUserName(name);
        userService.blockUser(user);

        return "redirect:/adminPage?name=";
    }

    @RequestMapping(value = "/addPlace", method = RequestMethod.GET)
    public String adminAddPlace(Model model) {
        model.addAttribute("place", new RentingPlace());

        return "addPlace";
    }

    @RequestMapping(value = "/addPlace", method = RequestMethod.POST)
    public String adminProcessPlace(@ModelAttribute("place") @Valid RentingPlace place, BindingResult result) {

        if (result.hasErrors()) {
            return "addPlace";
        }
        rentingPlaceService.save(place);

        return "redirect:/adminPage";
    }

    @RequestMapping("/carrent/delete")
    public String deleteCar(@RequestParam("car") String name) {

        productService.deleteCar(name);

        return "redirect:/carrent";

    }

    @RequestMapping(value = "/carrent/edit", method = RequestMethod.GET)
    public String editCar(Model model, @RequestParam("car") String name) {
        model.addAttribute("newCar", productService.findByCarId(name));

        return "editCar";
    }

    @RequestMapping(value = "/carrent/edit", method = RequestMethod.POST)
    public String processEditCar(@ModelAttribute("newCar") @Valid Car newCar, BindingResult result,
                                 @RequestParam("car") String name) {
        if (result.hasErrors()) {
            return "editCar";
        }

        Car oldCar = productService.findByCarId(name);
        newCar.setCarId(oldCar.getCarId());
        newCar.setId(oldCar.getId());

        productService.updateCar(newCar);

        return "redirect:/carrent";
    }

    @RequestMapping(value = "/carrent/setUnits", method = RequestMethod.GET)
    public String selectCarToAddUnits(Model model, @RequestParam("car") String carId) {
        Car car = productService.findByCarId(carId);

        List<RentingPlace> places = rentingPlaceService.findAll();

        model.addAttribute("car", car);

        CarStorageForm cSF = new CarStorageForm();

        for (int i = 0; i < places.size(); i++) {
            CarStorage carS = new CarStorage();
            carS.setRentingPlace(places.get(i));
            carS.setPlaceName(places.get(i).getCity());
            carS.setCar(car);
            cSF.getStorages().add(carS);

        }

        model.addAttribute("CarStorageForm", cSF);

        return "setUnits";

    }

    @RequestMapping(value = "/carrent/setUnits", method = RequestMethod.POST)
    public String processCarToAddUnits(Model model, @RequestParam("car") String carId,
                                       @ModelAttribute("CarStorageForm") CarStorageForm cSF) {

        Car car = productService.findByCarId(carId);

        List<RentingPlace> places = rentingPlaceService.findAll();

        for (int i = 0; i < cSF.getStorages().size(); i++) {
            cSF.getStorages().get(i).setCar(car);
            cSF.getStorages().get(i).setRentingPlace(places.get(i));

        }

        carStorageService.setUnits(cSF);

        return "redirect:/carrent";

    }

    @RequestMapping("/deleteOrder")
    public String deleteOrder(@RequestParam("order") long order) {

        orderService.deleteOrder(order);

        return "redirect:/adminPage";
    }

    @RequestMapping("/changeOrder")
    public String changeOrder(@RequestParam("order") long order) {

        Order ord = orderService.findOrderById(order);

        ord.setStatus(OrderStatus.INACTIVE);

        orderService.updateOrder(ord);

        return "redirect:/adminPage";
    }
}
