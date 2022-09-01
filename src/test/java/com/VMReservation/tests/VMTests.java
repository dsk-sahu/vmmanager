package com.VMReservation.tests;

import com.VMReservation.VMManager.VirtualMachine;
import lombok.SneakyThrows;
import org.testng.annotations.Test;

public class VMTests extends BaseTest {

    @Test
    @SneakyThrows
    public void user1() {

        VirtualMachine vm = vmObjectPool.checkOut();
        System.out.println(vm.getIP() + ": checked out");


        System.out.println(vm.getIP() + ": Operations done");
        Thread.sleep(10000);
        vmObjectPool.checkIn(vm);
        System.out.println(vm.getIP() + ": Checking in");


    }

    @Test
    public void user2() {
        VirtualMachine vm = vmObjectPool.checkOut();
        System.out.println(vm.getIP() + ": checked out");


        System.out.println(vm.getIP() + ": Operations done");

        vmObjectPool.checkIn(vm);
        System.out.println(vm.getIP() + ": Checking in");
    }

    @Test
    public void user3() {
        VirtualMachine vm = vmObjectPool.checkOut();
        System.out.println(vm.getIP() + ": checked out");


        System.out.println(vm.getIP() + ": Operations done");

        vmObjectPool.checkIn(vm);
        System.out.println(vm.getIP() + ": Checking in");
    }


}
