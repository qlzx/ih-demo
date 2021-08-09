
package com.example.demo.validation.vo;

import javax.validation.GroupSequence;
import javax.validation.constraints.AssertFalse;

@GroupSequence({RentalCar.class})
public class RentalCar extends Car {
    @AssertFalse(message = "The car is currently rented out")
    private boolean rentalStation = true;
    // get set......

    public boolean isRentalStation() {
        return rentalStation;
    }

    public void setRentalStation(boolean rentalStation) {
        this.rentalStation = rentalStation;
    }
}