// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
package com.example.api;

import com.google.gson.JsonElement;

/**
 * APIInterface class for the {@link com.example.api.API} class.
 * @author Mohamed Hossam
 * @version 1.0
 */
public interface APIInterface {

    /**
     * Read a topology from a given JSON file and store it in the memory
     * @param fileName Name of the file to be read from file to memory
     * @param overwrite <ul>
     *                      <li> If true and topology with same ID already exists in memory, it will be overwritten </li>
     *                      <li> If false and topology with same ID already exists in memory, reading will be ignored and memory will not be changed </li>
     *                      <li> Has no effect if topology does not exist in memory </li>
     *                  </ul>
     * @return {@link com.example.api.Result Result} object with success/failure status and message
     */
    Result readJSON(String fileName, boolean overwrite);

    /**
     * Calls {@link #readJSON(String,boolean) readJson} with overwrite set to false
     * @param fileName Name of the file to be read from file to memory
     * @return {@link com.example.api.Result Result} object with success/failure status and message
     */
    Result readJSON(String fileName);

    /**
     * Write a given topology from the memory to a JSON file with a given name.
     * @param TopologyID ID of the topology in memory to be written to file
     * @param fileName <ul>
     *                     <li> Name of the file to be written to </li>
     *                     <li> If the filename doesn't end with .json, it will be added </li>
     *                     <li> If fileName is empty string, it will be equal to TopologyID.json </li>
     *                 </ul>
     * @param overwrite <ul>
     *                     <li> If true and file with same name already exists, it will be overwritten </li>
     *                     <li> If false and file with same name already exists, writing will be ignored and memory will not be changed </li>
     *                     <li> Has no effect if file with given file name does not exist </li>
     *                  </ul>
     * @return {@link com.example.api.Result Result} object with success/failure status and message
     */
    Result writeJSON(String TopologyID, String fileName, boolean overwrite);

    /**
     * Calls {@link #writeJSON(String,String,boolean) writeJson} with overwrite set to false
     * @param TopologyID ID of the topology in memory to be written to file
     * @param fileName <ul>
     *                     <li> Name of the file to be written to </li>
     *                     <li> If the filename doesn't end with .json, it will be added </li>
     *                     <li> If fileName is empty string, it will be equal to TopologyID.json </li>
     *                 </ul>
     * @return {@link com.example.api.Result Result} object with success/failure status and message
     */
    Result writeJSON(String TopologyID, String fileName);

    /**
     * Query about all topologies in memory
     * @return {@link com.example.api.TopologyList Topology List} of topologies in memory
     */
    TopologyList queryTopologies();

    /**
     * Delete a topology with given topologyID from memory
     * @param topologyID ID of the topology in memory to be returned
     * @return {@link com.example.api.Result Result} object with success/failure status and message
     */
    Result deleteTopology(String topologyID);

    /**
     * Get a list of devices from a given topology
     * @param topologyID ID of the topology in memory from which devices will be returned
     * @return {@link com.example.api.DeviceList Device List} of devices in a given topology
     */
    DeviceList queryDevices(String topologyID);

    /**
     * Get a list of devices from a given topology that are connected to a given node
     * @param topologyID ID of the topology in memory from which devices will be returned
     * @param netlistNodeID ID of the netlist node from which devices that are connected to it will be returned
     * @return {@link com.example.api.DeviceList Device List} of devices that are connected to a given netlist node
     */
    DeviceList queryDevicesWithNetlistNode(String topologyID, String netlistNodeID);

    /**
     * Converts a given JsonElement to a pretty printed string representation. Pretty String is a string with new lines and proper indentation.
     * @param jsonElement JSON element to be converted to a pretty string
     * @return pretty string representation of a given JSON element
     */
    String prettyPrint(JsonElement jsonElement);
}
