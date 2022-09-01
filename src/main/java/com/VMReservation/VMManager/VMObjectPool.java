package com.VMReservation.VMManager;

import com.VMReservation.CustomExceptions.VMNotAvailableException;
import com.VMReservation.Utils.Configs;
import com.VMReservation.Utils.ShutdownHook;
import com.VMReservation.Utils.Utility;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.aeonbits.owner.ConfigFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

@Getter
@Setter
public class VMObjectPool implements Serializable {

    private static VMObjectPool vmObjectPool;

    /**
     * This static block serialize usedPool object in case of system restart.
     * While reboot, it de-serializes usedPool object
     */
    static {
        //Uncomment below method to read used VM details when system is crashed
        //TODO check if content is not null in .ser file then only call below method
        //getUsedVM();

        Runtime current = Runtime.getRuntime();
        current.addShutdownHook(new ShutdownHook());
    }

    private List<VirtualMachine> availablePool;
    private List<VirtualMachine> usedPool;
    private Configs frameworkConfig;
    private int maxPoolSize;

    private VMObjectPool() {
        availablePool = new ArrayList<>();
        usedPool = new ArrayList<>();
        frameworkConfig = ConfigFactory.create(Configs.class);
        maxPoolSize = frameworkConfig.maxPoolSize();
    }

    /**
     * Creates VM pool and forces to create only one object of VMObjectPool class
     *
     * @return Object of VMObjectPool
     */

    public static VMObjectPool getInstance() {
        if (vmObjectPool == null) {
            vmObjectPool = new VMObjectPool();
            vmObjectPool.createPool();
        }
        return vmObjectPool;
    }

    /**
     * Method to get/deserialize used VM details if system restarted
     */
    private static void getUsedVM() {
        getInstance().setUsedPool(Utility.deSerializeObject());
        //TODO add in logger
        List<VirtualMachine> v = getInstance().getUsedPool();
        System.out.println("Checked out VMs");
        for (VirtualMachine vm : v) {
            System.out.println(vm.getIP() + vm.getCPU() + vm.getRAM());

        }
    }

    /**
     * Creates pool of size maxPoolSize.
     * If program restarts after crash then creates pool size of (maxPoolSize - usedPoolSize)
     */

    public synchronized void createPool() {
        int usedPoolSize = getUsedPool().size();
        Hashtable<String, String> vmConfig = Utility.getVmConfig();
        for (int i = 1; i <= (maxPoolSize - usedPoolSize); i++) {
            VirtualMachine vm = new VirtualMachine();
            vm.setIP(Utility.getRandomIP());
            vm.setCPU(vmConfig.get("CPU"));
            vm.setRAM(vmConfig.get("RAM"));
            vm.setOS(vmConfig.get("OS"));
            availablePool.add(vm);
        }
        System.out.println("VM pool created : " + availablePool.size()); //TODO Implement logger

    }

    /**
     * If availablePool is not empty then returns vm object,
     * Remove that object from availablePool and
     * Add that VM object in usedPool
     * If availablePool is empty then throws VMNotAvailableException
     *
     * @return VM Object
     */
    @SneakyThrows
    public synchronized VirtualMachine checkOut() {
        if (availablePool.isEmpty())
            throw new VMNotAvailableException("VM Pool is empty, Please try after sometime");
        VirtualMachine vm = availablePool.get(0);
        usedPool.add(vm);
        availablePool.remove(vm);
        return vm;
    }

    /**
     * Remove VM from used pool and add into available pool
     * Before removing the VM, cleanup method called to clean the VM
     *
     * @param vm
     */
    public synchronized void checkIn(VirtualMachine vm) {
        VirtualMachine cleaned_vm = cleanup(vm);
        usedPool.remove(cleaned_vm);
        availablePool.add(cleaned_vm);

    }

    /**
     * Clean the VM
     *
     * @param vm
     * @return
     */
    private synchronized VirtualMachine cleanup(VirtualMachine vm) {
        System.out.println(vm.getIP() + ": Cleaning");
        vm.setIP("0.0.0.0");
        return vm;
    }


}
