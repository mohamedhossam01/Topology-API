// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
package com.example.api;

import com.google.gson.JsonObject;

import java.util.ArrayList;

public class TopologyList {
    private static TopologyList instance;
    private ArrayList<JsonObject> topologies;

    private TopologyList() {
        topologies = new ArrayList<>();
    }

    public static TopologyList getInstance() {
        if (instance == null) {
            instance = new TopologyList();
        }
        return instance;
    }

    public void addTopology(JsonObject topology) {
        topologies.add(topology);
    }

    public boolean deleteTopology(String topologyId) {
        for (int i = 0; i < topologies.size(); i++) {
            if (topologies.get(i).get("id").getAsString().equals(topologyId)) {
                topologies.remove(i);
                return true;
            }
        }
        return false;
    }

    public ArrayList<JsonObject> getTopologies() {
        return topologies;
    }

    public JsonObject getTopology(String topologyId) {
        for (JsonObject topology : topologies) {
            if (topology.get("id").getAsString().equals(topologyId)) {
                return topology;
            }
        }
        return null;
    }
}