package com.michal.springboot.validators;

import com.michal.springboot.domain.Order;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;





public class RentingDateRangeValidator implements ConstraintValidator<RentingDateRange, Order> {

    public void initialize(RentingDateRange constraintAnnotation) {
    }

    public boolean isValid(Order order, ConstraintValidatorContext context) {


	if ((order.getRentStart() != null) && (order.getRentEnd() != null)
		&& order.getRentEnd().before(order.getRentStart())) {
	    return false;
	}
	return true;
    }

}
