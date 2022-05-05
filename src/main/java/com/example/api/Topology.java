package com.example.api;

import com.google.gson.JsonArray;

public class Topology {
    private String id;
    private JsonArray components;

    Topology(String id, JsonArray components) {
        this.id = id;
        this.components = components;
    }
    public String getId() {
        return id;
    }

    public JsonArray getComponents() {
        return components;
    }

}
