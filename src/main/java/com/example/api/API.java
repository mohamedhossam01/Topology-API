// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
package com.example.api;

import com.google.gson.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class API implements APIInterface {

    @Override
    public Result readJSON(String fileName) {
        try {
            FileReader reader = new FileReader(fileName);
            JsonElement jsonElement = JsonParser.parseReader(reader);
            reader.close();
            if(jsonElement.isJsonNull())
            {
                return new Result(false, "File is empty");
            }
            JsonObject jsonObject = (JsonObject) jsonElement;
            if(queryDevices(jsonObject.get("id").getAsString()) != null) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Topology already exists. Do you want to overwrite it? (y/n)");
                String answer = scanner.nextLine();
                if(answer.equals("y")||answer.equals("Y")) {
                    deleteTopology(jsonObject.get("id").getAsString());
                }
                else
                {
                    return new Result(false, "Topology not overwritten");
                }
            }
            TopologyList.getInstance().addTopology(jsonObject);
        } catch (FileNotFoundException e) {
            return new Result(false,"File not found" );
        } catch (IOException e) {
            return new Result(false,"IOException" );
        } catch (JsonParseException e) {
            return new Result(false,"Input is not a valid JSON file" );
        } catch (NullPointerException e) {
            return new Result(false,"Topology doesn't contain ID field" );
        }

        return new Result(true, "Successfully read JSON from file");
    }

    @Override
    public Result writeJSON(String TopologyID, String fileName) {
        if(fileName.isEmpty()) {
            fileName = TopologyID + ".json";
        }
        if(!fileName.endsWith(".json")) {
            fileName += ".json";
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject topology = TopologyList.getInstance().getTopology(TopologyID);
        if(topology == null) {
            return new Result(false, "Topology with this ID does not exist");
        }
        try {
            File file = new File(fileName);
            if(file.exists()) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("File already exists. Do you want to overwrite it? (y/n)");
                String answer = scanner.nextLine();
                if(answer.equals("y")||answer.equals("Y")) {
                    boolean delete = file.delete();
                    if(!delete) {
                        return new Result(false, "File not deleted");
                    }
                }
                else {
                    return new Result(false, "File not overwritten");
                }
            }
            FileWriter writer = new FileWriter(file);
            gson.toJson(topology, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            return new Result(false,"IOException" );
        }
        return new Result(true, "Successfully wrote JSON to file");
    }

    @Override
    public TopologyList queryTopologies() {
        return TopologyList.getInstance();
    }

    @Override
    public Result deleteTopology(String topologyID) {
        return TopologyList.getInstance().deleteTopology(topologyID);
    }

    @Override
    public DeviceList queryDevices(String topologyID) {
        JsonObject topology = TopologyList.getInstance().getTopology(topologyID);
        if(topology == null) {
            return new DeviceList(new Result(false, "Could not find topology with ID: " + topologyID), null);
        }
        try
        {
            return new DeviceList(new Result(true, "Successfully read JSON from file"), topology.get("components").getAsJsonArray());
        }
        catch (NullPointerException e)
        {
            return new DeviceList(new Result(false, "Topology doesn't contain components field"), null);
        }
    }

    @Override
    public DeviceList queryDevicesWithNetlistNode(String topologyID, String netlistNodeID) {
        DeviceList deviceList = queryDevices(topologyID);
        JsonArray deviceArray = deviceList.getDevices();
        if(deviceArray == null) {
            return new DeviceList(new Result(false, "Topology with id = " + topologyID + " does not exist"), null);
        }
        JsonArray devices = new JsonArray();
        for(JsonElement jsonElement : deviceArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonObject netlistJson;
            try
            {
                netlistJson = jsonObject.get("netlist").getAsJsonObject();
            }
            catch (NullPointerException e)
            {
                continue;
            }
            Map<String,Object> netlist = netlistJson.entrySet().stream().collect(HashMap::new, (m,e) -> m.put(e.getKey(), e.getValue()), HashMap::putAll);
            for(Map.Entry<String,Object> entry : netlist.entrySet()) {
                String value = entry.getValue().toString();
                value = value.substring(1, value.length() - 1);
                if(value.equals(netlistNodeID)) {
                    devices.add(jsonObject);
                }
            }
        }
        if(devices.isEmpty()) {
            return new DeviceList(new Result(false, "No devices with netlist node id = " +netlistNodeID+ " found"), null);
        }
        else
        {
            return new DeviceList(new Result(true, "Successfully found devices"), devices);
        }
    }

    @Override
    public String prettyPrint(JsonElement jsonObject) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jsonObject);
    }

    public static void main(String[] args) {
        API api = new API();
        Scanner scanner = new Scanner(System.in);
        loop:
        while(true) {
            System.out.println("1. Read JSON from file to topology list");
            System.out.println("2. Write JSON from topology list to file");
            System.out.println("3. Get all topologies from topology list");
            System.out.println("4. Delete a topology from topology list");
            System.out.println("5. Get all devices from a topology");
            System.out.println("6. Get all devices with a netlist node from a given topology");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            //check if user entered a number
            while(!scanner.hasNextInt()) {
                System.out.println("Please enter a number");
                scanner.next();
            }
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter the file name: ");
                    String fileName = scanner.next();
                    System.out.println(api.readJSON(fileName).getMessage());
                break;
                case 2:
                    ArrayList<JsonObject> topologies = api.queryTopologies().getTopologies();
                    if(topologies.isEmpty()) {
                        System.out.println("No topologies exist");
                        break;
                    }
                    System.out.println("Enter topology ID: ");
                    String topologyID = scanner.next();

                    //check if topology exists
                    DeviceList devices = api.queryDevices(topologyID);
                    if(!devices.getResult().getStatus()) {
                        System.out.println(devices.getResult().getMessage());
                        break;
                    }

                    System.out.println("Do you want to enter file name? (y/n)");
                    String answer = scanner.next();
                    fileName = "";
                    if(answer.equals("y")||answer.equals("Y")) {
                        System.out.print("Enter file name: ");
                        fileName = scanner.next();
                    }
                    System.out.println(api.writeJSON(topologyID, fileName).getMessage());
                break;
                case 3:
                    topologies = api.queryTopologies().getTopologies();
                    if(topologies.isEmpty()) {
                        System.out.println("No topologies exist");
                        break;
                    }
                    System.out.println("Number of topologies: "+topologies.size());
                    for(JsonObject topology : topologies) {
                        System.out.println(api.prettyPrint(topology));
                        System.out.println("********************************************************");
                    }
                break;
                case 4:
                    topologies = api.queryTopologies().getTopologies();
                    if(topologies.isEmpty()) {
                        System.out.println("No topologies exist");
                        break;
                    }
                    System.out.println("Enter the topology ID: ");
                    topologyID = scanner.next();
                    System.out.println(api.deleteTopology(topologyID).getMessage());
                break;
                case 5:
                    topologies = api.queryTopologies().getTopologies();
                    if(topologies.isEmpty()) {
                        System.out.println("No topologies exist");
                        break;
                    }
                    System.out.println("Enter the topology ID: ");
                    topologyID = scanner.next();

                    //check if topology exists
                    devices = api.queryDevices(topologyID);
                    if(!devices.getResult().getStatus()) {
                        System.out.println(devices.getResult().getMessage());
                        break;
                    }

                    else if(devices.getDevices().size() == 0) {
                        System.out.println("Topology with ID: " + topologyID + " exists but has no devices");
                    }
                    else {
                        System.out.println("Number of devices: "+devices.getDevices().size());
                        for(JsonElement device : devices.getDevices()) {
                            System.out.println(api.prettyPrint(device));
                            System.out.println("********************************************************");
                        }
                    }
                break;
                case 6:
                    topologies = api.queryTopologies().getTopologies();
                    if(topologies.isEmpty()) {
                        System.out.println("No topologies exist");
                        break;
                    }
                    System.out.println("Enter the topology ID: ");
                    topologyID = scanner.next();

                    //check if topology exists
                    devices = api.queryDevices(topologyID);
                    if(!devices.getResult().getStatus()) {
                        System.out.println(devices.getResult().getMessage());
                        break;
                    }

                    System.out.println("Enter the netlist node: ");
                    String node = scanner.next();
                    System.out.println("Devices: ");
                    Result result = new Result();
                    DeviceList deviceList = api.queryDevicesWithNetlistNode(topologyID, node);
                    if(!result.getStatus()) {
                        System.out.println(result.getMessage());
                    }
                    else
                    {
                        for(JsonElement device : deviceList.getDevices()) {
                            System.out.println(api.prettyPrint(device));
                            System.out.println("********************************************************");
                        }
                    }
                break;
                case 7:
                    break loop;
                default:
                    System.out.println("Invalid choice\nPlease try again");
                    break;
            }
        }
    }
}