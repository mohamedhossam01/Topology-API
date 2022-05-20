// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
package test.com.example.api;

import com.example.api.TopologyList;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.testng.Assert.assertEquals;

class TopologyListTest {

    void reset()
    {
        TopologyList.getInstance().clear();
    }

    @Test
    void getInstanceTest() {
        reset();

        //Create two instances of TopologyList
        TopologyList instance1 = TopologyList.getInstance();
        TopologyList instance2 = TopologyList.getInstance();

        //Check if the instances are the same
        assertEquals(instance1, instance2);
    }

    @Test
    void addTopologyTest() {
        reset();

        //Create a new TopologyList object
        TopologyList topologyList = TopologyList.getInstance();

        //Parse a top1.json
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = null;
        try {
            jsonObject = (JsonObject) parser.parseReader(new FileReader("src/test/resources/top1.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Add a new topology
        topologyList.addTopology(jsonObject);

        //Check if the size of the list is 1
        assertEquals(1, topologyList.getTopologies().size());

        //Check if the topology is the same as the one we created
        assertEquals(jsonObject, topologyList.getTopologies().get(0));
    }

    @Test
    void deleteTopologyTest() {
        reset();

        //Create a new TopologyList object
        TopologyList topologyList = TopologyList.getInstance();

        //Parse a top1.json
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = null;
        try {
            jsonObject = (JsonObject) parser.parseReader(new FileReader("src/test/resources/top1.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Add a new topology
        topologyList.addTopology(jsonObject);

        //Check if the size of the list is 1
        assertEquals(1, topologyList.getTopologies().size());

        //Check if the topology is the same as the one we created
        assertEquals(jsonObject, topologyList.getTopologies().get(0));

        //Delete the topology
        topologyList.deleteTopology("top1");

        //Check if the size of the list is 0
        assertEquals(0, topologyList.getTopologies().size());
    }

    @Test
    void getTopologiesTest() {
        reset();

        //Create a new TopologyList object
        TopologyList topologyList = TopologyList.getInstance();

        //Parse a top1.json
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = null;
        try {
            jsonObject = (JsonObject) parser.parseReader(new FileReader("src/test/resources/top1.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Add a new topology
        topologyList.addTopology(jsonObject);

        //Check if the size of the list is 1
        assertEquals(1, topologyList.getTopologies().size());

        //Check if the topology is the same as the one we created
        assertEquals(jsonObject, topologyList.getTopologies().get(0));

        //Parse a top2.json
        JsonObject jsonObject2 = null;
        try {
            jsonObject2 = (JsonObject) parser.parseReader(new FileReader("src/test/resources/top2.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Add a new topology
        topologyList.addTopology(jsonObject2);

        //Check if the size of the list is 2
        assertEquals(2, topologyList.getTopologies().size());

        //Check if the topology is the same as the one we created
        assertEquals(jsonObject2, topologyList.getTopologies().get(1));
    }

    @Test
    void getTopologyTest() {
        reset();

        //Create a new TopologyList object
        TopologyList topologyList = TopologyList.getInstance();

        //Parse a top1.json
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = null;
        try {
            jsonObject = (JsonObject) parser.parseReader(new FileReader("src/test/resources/top1.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Add a new topology
        topologyList.addTopology(jsonObject);

        //Check if the size of the list is 1
        assertEquals(1, topologyList.getTopologies().size());

        //Check if the topology is the same as the one we created
        assertEquals(jsonObject, topologyList.getTopology("top1"));
    }

    @Test
    void clearTest() {
        reset();

        //Create a new TopologyList object
        TopologyList topologyList = TopologyList.getInstance();

        //Parse a top1.json
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = null;
        try {
            jsonObject = (JsonObject) parser.parseReader(new FileReader("src/test/resources/top1.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Add a new topology
        topologyList.addTopology(jsonObject);

        //Check if the size of the list is 1
        assertEquals(1, topologyList.getTopologies().size());

        //Add another topology
        JsonObject jsonObject2 = null;
        try {
            jsonObject2 = (JsonObject) parser.parseReader(new FileReader("src/test/resources/top2.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        topologyList.addTopology(jsonObject2);

        //Check if the size of the list is 2
        assertEquals(2, topologyList.getTopologies().size());

        //Clear the list
        topologyList.clear();

        //Check if the size of the list is 0
        assertEquals(0, topologyList.getTopologies().size());
    }
}