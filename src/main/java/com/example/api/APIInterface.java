// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
package com.example.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public interface APIInterface {
    //Read a topology from a given JSON file and store it in the memory.
    boolean readJSON(String fileName);

    //Write a given topology from the memory to a JSON file with a given name.
    boolean writeJSON(String TopologyID, String fileName);

    //Write a given topology from the memory to a JSON file with name = TopologyID.json.
    boolean writeJSON(String TopologyID);

    //Query about which topologies are currently in the memory.
    //TODO: figure out how to prettify the output.
    ArrayList<JsonObject> queryTopologies();

    //Delete a given topology from memory.
    boolean deleteTopology(String TopologyID);

    //Query about which devices are in a given topology.
    //TODO: figure out how to prettify the output.
    JsonArray queryDevices(String TopologyID);

    //Query about which devices are connected to a given netlist node in a given topology.
    ArrayList<JsonObject> queryDevicesWithNetlistNode(String TopologyID, String NetlistNodeID);
}
