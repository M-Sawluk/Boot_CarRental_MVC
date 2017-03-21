package com.michal.springboot.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.michal.springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;


public class CarIdValidator implements ConstraintValidator<CarId, String> {


    private ProductService productService;

    public CarIdValidator(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void initialize(CarId constraintAnnotation) {
    }

    @Override
    public boolean isValid(String carId, ConstraintValidatorContext context) {

        try {
            if (productService.findByCarId(carId) == null) {

                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return true;
        }


    }

}
