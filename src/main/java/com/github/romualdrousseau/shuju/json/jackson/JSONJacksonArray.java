package com.github.romualdrousseau.shuju.json.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.romualdrousseau.shuju.json.JSONArray;
import com.github.romualdrousseau.shuju.json.JSONObject;

public class JSONJacksonArray implements JSONArray {
    private ObjectMapper mapper;
    protected ArrayNode arrayNode;

    public JSONJacksonArray(ObjectMapper mapper, JsonNode node) {
        this.mapper = mapper;
        if (node == null) {
            this.arrayNode = mapper.createArrayNode();
        } else {
            this.arrayNode = (ArrayNode) node;
        }
    }

    public int size() {
        return (this.arrayNode == null) ? 0 : this.arrayNode.size();
    }

    @SuppressWarnings("unchecked")
    public <T> T get(int i) {
        JsonNode node = this.arrayNode.get(i);
        if (node.isObject()) {
            return (T) new JSONJacksonObject(this.mapper, node);
        } else if (node.isArray()) {
            return (T) new JSONJacksonArray(this.mapper, node);
        } else if (node.isInt()) {
            return (T) Integer.valueOf(node.intValue());
        } else if (node.isFloat()) {
            return (T) Float.valueOf(node.floatValue());
        } else {
            return (T) node.textValue();
        }
    }

    public void set(int i, Object o) {
        if (o instanceof JSONObject) {
            this.arrayNode.set(i, (JsonNode) ((JSONJacksonObject) o).objectNode);
        } else if (o instanceof JSONArray) {
            this.arrayNode.set(i, (JsonNode) ((JSONJacksonArray) o).arrayNode);
        } else {
            this.arrayNode.set(i, this.mapper.convertValue(o, JsonNode.class));
        }
    }

    public int getInt(int i) {
        return this.arrayNode.get(i).intValue();
    }

    public void setInt(int i, int n) {
        this.arrayNode.set(i, n);
    }

    public float getFloat(int i) {
        return this.arrayNode.get(i).floatValue();
    }

    public void setFloat(int i, float f) {
        this.arrayNode.set(i, f);
    }

    public String getString(int i) {
        return this.arrayNode.get(i).textValue();
    }

    public void setString(int i, String s) {
        this.arrayNode.set(i, s);
    }

    public JSONArray getArray(int i) {
        return new JSONJacksonArray(this.mapper, this.arrayNode.get(i));
    }

    public void setArray(int i, JSONArray a) {
        this.arrayNode.set(i, ((JSONJacksonArray) a).arrayNode);
    }

    public JSONObject getObject(int i) {
        return new JSONJacksonObject(this.mapper, this.arrayNode.get(i));
    }

    public void setOject(int i, JSONObject o) {
        this.arrayNode.set(i, ((JSONJacksonObject) o).objectNode);
    }

    public void append(int i) {
        this.arrayNode.add(i);
    }

    public void append(float f) {
        this.arrayNode.add(f);
    }

    public void append(String s) {
        this.arrayNode.add(s);
    }

    public void append(JSONArray a) {
        this.arrayNode.add(((JSONJacksonArray) a).arrayNode);
    }

    public void append(JSONObject o) {
        this.arrayNode.add(((JSONJacksonObject) o).objectNode);
    }

    public void append(Object o) {
        if (o instanceof JSONObject) {
            this.arrayNode.add(((JSONJacksonObject) o).objectNode);
        } else if (o instanceof JSONArray) {
            this.arrayNode.add(((JSONJacksonArray) o).arrayNode);
        } else if (o instanceof Integer) {
            this.arrayNode.add((Integer) o);
        } else if (o instanceof Float) {
            this.arrayNode.add((Float) o);
        } else {
            this.arrayNode.add(o.toString());
        }
    }

    public void remove(int i) {
        this.arrayNode.remove(i);
    }

    @Override
    public String toString() {
        return this.arrayNode.toString();
    }
}
