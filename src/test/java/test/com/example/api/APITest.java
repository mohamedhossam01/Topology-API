// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
package test.com.example.api;

import com.example.api.*;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class APITest {
    API api = new API();

    //clears the memory
    void reset()
    {
        TopologyList.getInstance().clear();
    }

    @Test
    void readJSONTest() {
        reset();
        Result expectedResult;

        //read a valid topology (number of topologies in memory = 1)
        expectedResult = api.readJSON("src/test/resources/top1.json");
        assertEquals(expectedResult,new Result(true, "Successfully read JSON from file"));

        //read an empty topology (number of topologies in memory = 1)
        expectedResult = api.readJSON("src/test/resources/empty.json");
        assertEquals(expectedResult,new Result(false, "File is empty"));

        //read an invalid topology (missing topologyID) (number of topologies in memory = 1)
        expectedResult = api.readJSON("src/test/resources/malformed.json");
        assertEquals(expectedResult,new Result(false,"Topology doesn't contain ID field" ));

        //read an existing topology with the same ID and overwrite it (number of topologies in memory = 1)
        expectedResult = api.readJSON("src/test/resources/topologies1.json", true);
        assertEquals(expectedResult,new Result(true, "Successfully read JSON from file"));

        //read an existing topology with the same ID and NOT overwriting it (number of topologies in memory = 1)
        expectedResult = api.readJSON("src/test/resources/topologies1.json");
        assertEquals(expectedResult,new Result(false, "Topology not overwritten"));

        //reading another valid topology (number of topologies in memory = 2)
        expectedResult = api.readJSON("src/test/resources/topologies2.json");
        assertEquals(expectedResult,new Result(true, "Successfully read JSON from file"));

        //reading an xml file (number of topologies in memory = 2)
        expectedResult = api.readJSON("src/test/resources/topologies1.xml");
        assertEquals(expectedResult,new Result(false, "Input is not a valid JSON file"));

        //reading a non-existing file (number of topologies in memory = 2)
        expectedResult = api.readJSON("src/test/resources/non-existing.json");
        assertEquals(expectedResult,new Result(false, "File not found"));

        //checking if number of topologies in memory = 2
        TopologyList topologies = api.queryTopologies();
        assertEquals(topologies.getTopologies().size(), 2);
    }

    @Test
    void writeJSONTest() {
        reset();
        //delete top1-output.json, topologies1-output.json if it exists
        Path path = new File("src/test/resources/top1-output.json").toPath();
        if (Files.exists(path)) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        path = new File("src/test/resources/topologies1-output.json").toPath();
        if (Files.exists(path)) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Changing system input
        System.setIn(new ByteArrayInputStream("Y\nN\n".getBytes()));
        Result expectedResult;

        //Writing topology from memory while it is empty (number of topologies in memory = 0)
        expectedResult = api.writeJSON("top1", "src/test/resources/top1.json");
        assertEquals(expectedResult,new Result(false, "No topologies in memory"));

        //reading two valid topologies (number of topologies in memory = 2)
        api.readJSON("src/test/resources/topologies1.json");
        api.readJSON("src/test/resources/topologies2.json");

        //Writing a topology to a file (number of topologies in memory = 2)
        expectedResult = api.writeJSON("top1", "src/test/resources/topologies1-output.json");
        assertEquals(expectedResult,new Result(true, "Successfully wrote JSON to file"));

        //checking if top1.json content is the same as top1-output.json content
        try {
            String top1 = Files.readString(Path.of("src/test/resources/topologies1.json"));
            String top1Output = Files.readString(Path.of("src/test/resources/topologies1-output.json"));
            //remove all \r and \n characters
            top1 = top1.replaceAll("[\\r\\n]", "");
            top1Output = top1Output.replaceAll("[\\r\\n]", "");
            assertEquals(top1, top1Output);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Writing a non-existing topology to a file (number of topologies in memory = 2)
        expectedResult = api.writeJSON("top3", "src/test/resources/top3-output.json");
        assertEquals(expectedResult,new Result(false, "Topology with this ID does not exist"));

        //checking if top3-output.json file does not exist
        assertThrows(FileNotFoundException.class, () -> new FileReader("src/test/resources/top3-output.json"));

        //Writing a topology to a file while overwriting it (number of topologies in memory = 2)
        expectedResult = api.writeJSON("top1", "src/test/resources/topologies1-output.json", true);
        assertEquals(expectedResult,new Result(true, "Successfully wrote JSON to file"));

        //Writing a topology to a file while NOT overwriting it (number of topologies in memory = 2)
        expectedResult = api.writeJSON("top1", "src/test/resources/topologies1-output.json");
        assertEquals(expectedResult,new Result(false, "File not overwritten"));
    }

    @Test
    void queryTopologiesTest() {
        reset();
        //checking if number of topologies in memory = 0
        TopologyList topologies = api.queryTopologies();
        assertEquals(topologies.getTopologies().size(), 0);

        //reading two valid topologies (number of topologies in memory = 2)
        api.readJSON("src/test/resources/topologies1.json");
        api.readJSON("src/test/resources/topologies2.json");

        //checking if number of topologies in memory = 2
        topologies = api.queryTopologies();
        assertEquals(topologies.getTopologies().size(), 2);
    }

    @Test
    void deleteTopology() {
        reset();
        //checking if number of topologies in memory = 0
        TopologyList topologies = api.queryTopologies();
        assertEquals(topologies.getTopologies().size(), 0);

        //deleting from memory while it is empty (number of topologies in memory = 0)
        Result expectedResult = api.deleteTopology("top1");
        assertEquals(expectedResult,new Result(false, "No topologies in memory"));

        //reading two valid topologies (number of topologies in memory = 2)
        api.readJSON("src/test/resources/topologies1.json");
        api.readJSON("src/test/resources/topologies2.json");

        //checking if number of topologies in memory = 2
        topologies = api.queryTopologies();
        assertEquals(topologies.getTopologies().size(), 2);

        //deleting a topology (number of topologies in memory = 1)
        expectedResult = api.deleteTopology("top1");
        assertEquals(expectedResult,new Result(true, "Successfully deleted topology"));

        //checking if number of topologies in memory = 1
        topologies = api.queryTopologies();
        assertEquals(topologies.getTopologies().size(), 1);

        //deleting a non-existing topology (number of topologies in memory = 1)
        expectedResult = api.deleteTopology("top3");
        assertEquals(expectedResult,new Result(false, "Topology with this ID does not exist"));

        //checking if number of topologies in memory = 1
        topologies = api.queryTopologies();
        assertEquals(topologies.getTopologies().size(), 1);

        //deleting a non-existing topology (number of topologies in memory = 1)
        expectedResult = api.deleteTopology("top3");
        assertEquals(expectedResult,new Result(false, "Topology with this ID does not exist"));

        //deleting topology 2 (number of topologies in memory = 0)
        expectedResult = api.deleteTopology("top2");
        assertEquals(expectedResult,new Result(true, "Successfully deleted topology"));

        //checking if number of topologies in memory = 0
        topologies = api.queryTopologies();
        assertEquals(topologies.getTopologies().size(), 0);
    }

    @Test
    void queryDevices() {
        reset();
        //checking if number of devices in empty memory = 0
        DeviceList expectedDevices = api.queryDevices("top1");
        assertEquals(expectedDevices.getResult(),new Result(false,"No topologies in memory"));
        assertNull(expectedDevices.getDevices());

        //reading two valid topologies
        api.readJSON("src/test/resources/topologies1.json");
        api.readJSON("src/test/resources/topologies2.json");

        //checking if number of devices in topology1 = 2
        expectedDevices = api.queryDevices("top1");
        assertEquals(expectedDevices.getResult(),new Result(true,"Successfully queried devices"));
        assertEquals(expectedDevices.getDevices().size(), 2);

        //checking if number of devices in non-existing topology = 0
        expectedDevices = api.queryDevices("top3");
        assertEquals(expectedDevices.getResult(),new Result(false,"Could not find topology with ID: top3"));
        assertNull(expectedDevices.getDevices());
    }

    @Test
    void queryDevicesWithNetlistNodeTest() {
        reset();
        //checking if number of devices in empty memory = 0
        DeviceList expectedDevices = api.queryDevicesWithNetlistNode("top1", "node1");
        assertEquals(expectedDevices.getResult(),new Result(false,"No topologies in memory"));
        assertNull(expectedDevices.getDevices());

        //reading two valid topologies
        api.readJSON("src/test/resources/topologies1.json");
        api.readJSON("src/test/resources/topologies2.json");

        //checking devices with netlist node = "vdd"
        expectedDevices = api.queryDevicesWithNetlistNode("top1", "vdd");
        assertEquals(expectedDevices.getResult(),new Result(true,"Successfully found devices"));
        assertEquals(expectedDevices.getDevices().size(), 1);
        Gson gson = new Gson();
        String expectedDevice = gson.toJson(expectedDevices.getDevices().get(0));
        assertEquals(expectedDevice, "{\"type\":\"resistor\",\"id\":\"res1\",\"resistance\":{\"default\":100,\"min\":10,\"max\":1000},\"netlist\":{\"t1\":\"vdd\",\"t2\":\"n1\"}}");
    }
}