// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
package com.example.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class API implements APIInterface {

    @Override
    public boolean readJSON(String fileName) {
        try {
            FileReader reader = new FileReader(fileName);
            JsonObject jsonObject = (JsonObject) JsonParser.parseReader(reader);
            reader.close();
            TopologyList.getInstance().addTopology(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean writeJSON(String TopologyID, String fileName) {
        if(fileName.endsWith(".json")) {
            fileName = fileName.substring(0, fileName.length() - 5);
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject topology = TopologyList.getInstance().getTopology(TopologyID);
        if(topology == null) {
            return false;
        }
        try {
            FileWriter writer = new FileWriter("src/main/resources/" +fileName + ".json");
            gson.toJson(topology, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean writeJSON(String TopologyID) {
        return writeJSON(TopologyID, TopologyID);
    }

    @Override
    public ArrayList<JsonObject> queryTopologies() {
        return TopologyList.getInstance().getTopologies();
    }

    @Override
    public boolean deleteTopology(String TopologyID) {
        return TopologyList.getInstance().deleteTopology(TopologyID);
    }

    @Override
    public DeviceList queryDevices(String TopologyID) {
        return null;
    }

    @Override
    public DeviceList queryDevicesWithNetlistNode(int TopologyID, int NetlistNodeID) {
        return null;
    }

    //Temporary test function
    public static void main(String[] args) {
        API api = new API();
        api.readJSON("src/main/resources/topologies2.json");
        api.readJSON("src/main/resources/topologies1.json");
        api.writeJSON("top1","circuit1.json");
    }
}