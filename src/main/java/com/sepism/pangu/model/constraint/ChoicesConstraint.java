package com.sepism.pangu.model.constraint;

import com.sepism.pangu.exception.InvalidInputException;

public class ChoicesConstraint extends DataConstraint {
    @Override
    protected void furtherValidate(String value) throws InvalidInputException {
        //TODO: Check whether the choices are in the database;
    }
}
