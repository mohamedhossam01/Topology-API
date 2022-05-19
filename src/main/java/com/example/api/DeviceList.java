// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
package com.example.api;

import com.google.gson.JsonArray;

/**
 * DeviceList Class for the API that contains a list of devices and {@link com.example.api.Result Result} for the operation involving this list.
 * @author Mohamed Hossam
 * @version 1.0
 */
public class DeviceList {
    /**
     * {@link com.example.api.Result Result} object to get the result of the operation involving the device list class
     */
    private final Result result;
    /**
     * JsonArray of devices, devices are NOT represented as a separate class to be able to handle any number and type of tags in a device.
     */
    private final JsonArray devices;

    /**
     * @param result {@link com.example.api.Result Result} of the operation
     * @param devices List of devices
     */
    public DeviceList(Result result, JsonArray devices) {
        this.result = result;
        this.devices = devices;
    }

    /**
     * @return {@link com.example.api.Result Result} of the operation
     */
    public Result getResult() {
        return result;
    }

    /**
     * @return List of devices
     */
    public JsonArray getDevices() {
        return devices;
    }

}
