package com.VMReservation.Utils;

import com.VMReservation.VMManager.VirtualMachine;
import com.google.gson.Gson;
import lombok.SneakyThrows;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

public class Utility {
    final static File rootDir = new File(System.getProperty("user.dir"));
    final static File pathResources = new File(rootDir + File.separator
            + "src" + File.separator
            + "main" + File.separator
            + "resources" + File.separator);

    /**
     * This method will serialize given object in fileName location
     *
     * @param obj
     */
    @SneakyThrows
    public static void serializeObject(Object obj) {
        File fileName = new File(pathResources, "output.ser");
        FileOutputStream file = new FileOutputStream(fileName);
        ObjectOutputStream out = new ObjectOutputStream(file);
        out.writeObject(obj);
        out.close();
        file.close();
        System.out.println("Object has been serialized");

    }

    /**
     * This method will de-serialize the file ar fileName location
     *
     * @return List<VirtualMachine>
     */
    @SneakyThrows
    public static List<VirtualMachine> deSerializeObject() {
        File fileName = new File(pathResources, "output.ser");
        FileInputStream file = new FileInputStream(fileName);
        ObjectInputStream in = new ObjectInputStream(file);
        List<VirtualMachine> listOfVMs = (ArrayList<VirtualMachine>) in.readObject();
        in.close();
        file.close();
        System.out.println("Object has been deserialized");
        return listOfVMs;
    }

    /**
     * Read VM config from VMConfig.json
     *
     * @return Hashtable<String, String> VM config
     */
    @SneakyThrows
    public static Hashtable<String, String> getVmConfig() {
        File fileName = new File(pathResources, "VMConfig.json");
        Reader reader = Files.newBufferedReader(Paths.get(fileName.toURI()));
        Gson gson = new Gson();
        VirtualMachine vm = gson.fromJson(reader, VirtualMachine.class);
        Hashtable<String, String> vm_config = new Hashtable<>();
        vm_config.put("IP", vm.getIP());
        vm_config.put("CPU", vm.getCPU());
        vm_config.put("RAM", vm.getRAM());
        vm_config.put("OS", vm.getOS());
        return vm_config;
    }

    /**
     * Generate random IP
     *
     * @return IP in string format
     */
    public static String getRandomIP() {
        Random random = new Random();
        int last = random.nextInt(254) + 1;
        return String.format("192.168.1.%d", last);
    }


}

