package com.VMReservation.CustomExceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VMNotAvailableException extends Exception {

    public VMNotAvailableException(String msg) {
        super(msg);
    }

}
