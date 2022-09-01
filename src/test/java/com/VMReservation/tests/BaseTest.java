package com.VMReservation.tests;

import com.VMReservation.VMManager.VMObjectPool;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    protected VMObjectPool vmObjectPool;


    @BeforeSuite
    public void createVMPool() {
        vmObjectPool = VMObjectPool.getInstance();

    }
}
