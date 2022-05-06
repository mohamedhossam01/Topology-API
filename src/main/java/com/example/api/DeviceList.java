// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
package com.example.api;

import com.google.gson.JsonArray;

public class DeviceList {
    private final Result result;
    private final JsonArray devices;

    DeviceList(Result result, JsonArray devices) {
        this.result = result;
        this.devices = devices;
    }

    public Result getResult() {
        return result;
    }

    public JsonArray getDevices() {
        return devices;
    }
}
