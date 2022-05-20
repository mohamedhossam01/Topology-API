// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
package test.com.example.api;

import com.example.api.API;
import com.example.api.DeviceList;
import com.example.api.Result;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import static org.testng.Assert.*;

class DeviceListTest {

    API api = new API();

    @Test
    void getResultTest() {
        //Create a new DeviceList object
        DeviceList deviceList = new DeviceList(new Result(true, "Successfully deleted topology"), new JsonArray());

        //Check if the status is the same as the one we created
        assertTrue(deviceList.getResult().getStatus());

        //Check if the message is the same as the one we created
        assertEquals("Successfully deleted topology", deviceList.getResult().getMessage());

        //Check if the result is the same as the one we created
        assertEquals(new Result(true, "Successfully deleted topology"), deviceList.getResult());

        //Create a new DeviceList object
        deviceList = new DeviceList(new Result(false, "Failed to delete topology"), new JsonArray());

        //Check if the status is the same as the one we created
        assertFalse(deviceList.getResult().getStatus());

        //Check if the message is the same as the one we created
        assertEquals("Failed to delete topology", deviceList.getResult().getMessage());

        //Check if the result is the same as the one we created
        assertEquals(new Result(false, "Failed to delete topology"), deviceList.getResult());
    }

    @Test
    void getDevicesTest() {
        //Add top1 to memory
        api.readJSON("src/test/resources/top1.json");

        //Create a new DeviceList object
        DeviceList deviceList = new DeviceList(new Result(true, "Successfully deleted topology"), new JsonArray());

        //Check if the devices are the same as the one we created
        assertEquals(new JsonArray(), deviceList.getDevices());

        //Create a new DeviceList object
        deviceList = new DeviceList(new Result(false, "Failed to delete topology"), api.queryDevices("top1").getDevices());

        //Parse result with JsonParser
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse("[{\"type\":\"resistance\",\"id\":\"res1\",\"resistance\":{\"default\":100,\"min\":10,\"max\":1000},\"netlist\":{\"t1\":\"vdd\",\"t2\":\"n1\"}},{\"type\":\"nmos\",\"id\":\"m1\",\"m(l)\":{\"default\":1.5,\"min\":1,\"max\":2},\"netlist\":{\"drain\":\"n1\",\"gate\":\"vin\",\"source\":\"vss\"}}]");
        JsonArray expectedJsonArray = jsonElement.getAsJsonArray();

        //Check if the devices are the same as the one we created
        assertEquals(expectedJsonArray, deviceList.getDevices());
    }
}