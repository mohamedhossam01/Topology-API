// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
package com.example.api;

import com.google.gson.JsonObject;
import java.util.ArrayList;

/**
 * TopologyList Class for the API that contains an ArrayList of JsonObjects and An instance of TopologyList.
 * @author Mohamed Hossam
 * @version 1.0
 */
public class TopologyList {
    /**
     * Single instance of TopologyList created by {@link #getInstance()} method using the Singleton design pattern.
     */
    private static TopologyList instance;
    /**
     * ArrayList of JsonObjects representing the topologies.
     * Topology is represented as a JsonObject rather than a separate class to be able to handle any number and type of tags in a topology. (e.g. "title", "description", "comments", "tags")
     */
    private final ArrayList<JsonObject> topologies;

    /**
     * Private constructor for TopologyList class to prevent instantiation from other classes.
     */
    private TopologyList() {
        topologies = new ArrayList<>();
    }

    /**
     * @return Instance of TopologyList class using the Singleton design pattern.
     */
    public static TopologyList getInstance() {
        if (instance == null) {
            instance = new TopologyList();
        }
        return instance;
    }

    /**
     * Adds a topology to the list.
     * @param topology JsonObject representing a topology
     */
    public void addTopology(JsonObject topology) {
        topologies.add(topology);
    }

    /**
     * Removes a topology from the list stored in memory.
     * @param topologyId Id of the topology to be removed
     * @return {@link com.example.api.Result Result} object containing the result of the operation
     */
    public Result deleteTopology(String topologyId) {
        if(topologies.isEmpty()) {
            return new Result(false, "No topologies in memory");
        }
        for (int i = 0; i < topologies.size(); i++) {
            if (topologies.get(i).get("id").getAsString().equals(topologyId)) {
                topologies.remove(i);
                return new Result(true, "Successfully deleted topology");
            }
        }
        return new Result(false, "Topology with this ID does not exist");
    }

    /**
     * Returns all topologies stored in memory.
     * @return ArrayList of JsonObjects representing the topologies
     */
    public ArrayList<JsonObject> getTopologies() {
        return topologies;
    }

    /**
     * Searches for a topology in memory by its ID.
     * @param topologyId Id of the topology to be retrieved
     * @return JsonObject representing the topology
     */
    public JsonObject getTopology(String topologyId) {
        for (JsonObject topology : topologies) {
            if (topology.get("id").getAsString().equals(topologyId)) {
                return topology;
            }
        }
        return null;
    }

    /**
     * Removes all topologies from the list stored in memory.
     */
    public void clear() {
        topologies.clear();
    }
}