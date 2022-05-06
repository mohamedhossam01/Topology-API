// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
package com.example.api;

import com.google.gson.JsonElement;

public interface APIInterface {
    //Read a topology from a given JSON file and store it in the memory.
    Result readJSON(String fileName);

    //Write a given topology from the memory to a JSON file with a given name.
    Result writeJSON(String TopologyID, String fileName);

    //Query about which topologies are currently in the memory.
    TopologyList queryTopologies();

    //Delete a given topology from memory.
    Result deleteTopology(String topologyID);

    //Query about which devices are in a given topology.
    DeviceList queryDevices(String topologyID);

    //Query about which devices are connected to a given netlist node in a given topology.
    DeviceList queryDevicesWithNetlistNode(String topologyID, String netlistNodeID);

    //Helper function to pretty print a given JSON object.
    String prettyPrint(JsonElement jsonObject);
}
