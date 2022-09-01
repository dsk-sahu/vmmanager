package com.VMReservation.Utils;

import com.VMReservation.VMManager.VMObjectPool;


public class ShutdownHook extends Thread {

    public void run() {

        VMObjectPool vmObjectPool = VMObjectPool.getInstance();
        Utility.serializeObject(vmObjectPool.getUsedPool());
        System.out.println("In shutdown hook");

    }
}
