// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
package com.example.api;

import com.google.gson.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    public JsonArray queryDevices(String TopologyID) {
        TopologyList topologyList = TopologyList.getInstance();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray jsonArray = new JsonArray();
        String resultArray = gson.toJson(topologyList.getTopology(TopologyID).getAsJsonArray("components"));
        jsonArray.addAll(gson.fromJson(resultArray, JsonArray.class));
        return jsonArray;
    }

    @Override
    public ArrayList<JsonObject> queryDevicesWithNetlistNode(String TopologyID, String NetlistNodeID) {
        JsonArray jsonArray = queryDevices(TopologyID);
        ArrayList<JsonObject> jsonObjectArray = new ArrayList<>();
        for(JsonElement jsonElement : jsonArray) {
            JsonObject jsonObject = (JsonObject) jsonElement.getAsJsonObject();
            JsonObject netList = (JsonObject) jsonObject.get("netlist");
            Map<String,Object> devices = netList.entrySet().stream().collect(HashMap::new, (m,e) -> m.put(e.getKey(), e.getValue()), HashMap::putAll);
            for(Map.Entry<String,Object> entry : devices.entrySet()) {
                String value = entry.getValue().toString();
                value = value.substring(1, value.length() - 1);
                if(value.equals(NetlistNodeID)) {
                    jsonObjectArray.add(jsonObject);
                }
            }
        }
        return jsonObjectArray;
    }

    //Temporary test function
    public static void main(String[] args) {
        API api = new API();
        api.readJSON("src/main/resources/topologies2.json");
        api.readJSON("src/main/resources/topologies1.json");
        ArrayList<JsonObject> topologies = api.queryTopologies();
        for(JsonObject topology : topologies) {
            System.out.println(topology.toString());
        }
//        api.writeJSON("top1","circuit1.json");
//        JsonArray jsonArray = api.queryDevices("top1");
//        //System.out.println(jsonArray.toString());
//        ArrayList<JsonObject> devices = api.queryDevicesWithNetlistNode("top1","n1");
//        for(JsonObject jsonObject : devices) {
//            System.out.println(jsonObject.toString());
//        }
    }
}