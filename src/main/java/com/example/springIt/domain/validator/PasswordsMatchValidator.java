package com.example.springIt.domain.validator;

import com.example.springIt.domain.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordsMatchValidator implements ConstraintValidator<com.example.springIt.domain.validator.PasswordsMatch, User> {

    @Override
    public void initialize(PasswordsMatch constraintAnnotation){
    }

    public boolean isValid(User user, ConstraintValidatorContext context) {
        return user.getPassword().equals(user.getConfirmPassword());
    }



}