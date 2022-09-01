package com.VMReservation.VMManager;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class VirtualMachine implements Serializable {
    private String IP;
    private String CPU;
    private String RAM;
    private String OS;

}
