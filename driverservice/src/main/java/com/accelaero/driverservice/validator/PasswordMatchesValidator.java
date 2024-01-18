package com.accelaero.driverservice.validator;

import com.accelaero.driverservice.entity.User;
import com.accelaero.driverservice.requestdto.UserRegisterRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        UserRegisterRequest user = (UserRegisterRequest) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}