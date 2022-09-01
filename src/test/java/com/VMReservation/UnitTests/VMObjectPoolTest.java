package com.VMReservation.UnitTests;

import com.VMReservation.CustomExceptions.VMNotAvailableException;
import com.VMReservation.VMManager.VMObjectPool;
import com.VMReservation.VMManager.VirtualMachine;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.catchThrowable;

public class VMObjectPoolTest {

    private volatile VMObjectPool vmObjectPool = VMObjectPool.getInstance();

    @Test
    public void testPoolMaxSize() {
        Assertions.assertThat(vmObjectPool.getAvailablePool().size())
                .withFailMessage("Pool size does not match")
                .isEqualTo(vmObjectPool.getMaxPoolSize());
    }

    @Test
    public void testVMRemovedFromPoolAfterCheckout() {
        VirtualMachine vm = vmObjectPool.checkOut();
        Assertions.assertThat(vmObjectPool.getAvailablePool().contains(vm))
                .withFailMessage("VM does not removed from pool after checkout")
                .isFalse();

    }

    @Test
    public void testVMAddedToUsedPoolAfterCheckout() {
        VirtualMachine vm = vmObjectPool.checkOut();
        Assertions.assertThat(vmObjectPool.getUsedPool().contains(vm))
                .withFailMessage("VM does not added to used pool after checkout")
                .isTrue();

    }

    @Test
    public void testVMRemovedFromUsedPoolAfterCheckin() {
        VirtualMachine vm = vmObjectPool.checkOut();
        vmObjectPool.checkIn(vm);
        Assertions.assertThat(vmObjectPool.getUsedPool().contains(vm))
                .withFailMessage("VM does not removed from used pool after check-in")
                .isFalse();

    }

    @Test
    public void testVMAddedToAvailablePoolAfterCheckin() {
        VirtualMachine vm = vmObjectPool.checkOut();
        vmObjectPool.checkIn(vm);
        Assertions.assertThat(vmObjectPool.getAvailablePool().contains(vm))
                .withFailMessage("VM does not added to available pool after check-in")
                .isTrue();

    }

    @Test
    public void testIPResetInCleanup() {
        VirtualMachine vm = vmObjectPool.checkOut();
        vmObjectPool.checkIn(vm);
        Assertions.assertThat(vm.getIP())
                .withFailMessage("IP does not reset to 0.0.0.0 in clean up")
                .isEqualTo("0.0.0.0");

    }

    @Test(priority = 10)
    public void testVMNotAvailableException() {

        int maxPoolSize = vmObjectPool.getAvailablePool().size();
        for (int i = 1; i <= maxPoolSize; i++) {
            vmObjectPool.checkOut();
        }
        Throwable thrown = catchThrowable(() -> {
            vmObjectPool.checkOut();
        });

        Assertions.assertThat(thrown)
                .isInstanceOf(VMNotAvailableException.class)
                .hasMessage("VM Pool is empty, Please try after sometime");

    }


}
