package lib;

import org.json.JSONObject;

import java.util.Objects;
import java.util.Random;

public class Filter extends Equipment {

    private long id;
    private String name;
    private double connection;

    public Filter(String name, double connection) {
        this.id = Filter.generateId();
        this.name = name;
        this.connection = connection;
    }

    public Filter(long id, String name, double connection) {
        this.id = id;
        this.name = name;
        this.connection = connection;
    }

    public JSONObject toJSONObject() {
        JSONObject object = new JSONObject();
        object.put("id", id);
        object.put("name", name);
        object.put("connection", connection);
        return object;
    }

    public static Filter fromJSONObject(JSONObject object) {
        return new Filter(
                object.getLong("id"),
                object.getString("name"),
                object.getInt("connection")
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Filter)
            return ((Filter) obj).id == id;
        return false;
    }

    public static long generateId() {
        return new Random().nextLong();
    }


    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public double getConnection() {
        return connection;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setConnection(double connection) {
        this.connection = connection;
    }

}
