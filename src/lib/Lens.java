package lib;

import org.json.JSONObject;

import java.util.Random;

@SuppressWarnings({"unused"})
public class Lens extends Storeable {

    private long id;
    private String name;
    private double factor;
    private double connection;

    public Lens(String name, double factor, double connection) {
        this.id = Lens.generateId();
        this.name = name;
        this.factor = factor;
        this.connection = connection;
    }

    public Lens(long id, String name, double factor, double connection) {
        this.id = id;
        this.name = name;
        this.factor = factor;
        this.connection = connection;
    }

    public JSONObject toJSONObject() {
        JSONObject object = new JSONObject();
        object.put("id", id);
        object.put("name", name);
        object.put("factor", factor);
        object.put("connection", connection);
        return object;
    }

    public static Lens fromJSONObject(JSONObject object) {
        return new Lens(
                object.getLong("id"),
                object.getString("name"),
                object.getInt("factor"),
                object.getInt("connection")
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Lens)
            return ((Lens) obj).id == id;
        return false;
    }

    public static long generateId() {
        return new Random().nextLong();
    }

    public double getConnection() {
        return connection;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public double getFactor() {
        return factor;
    }

    public void setConnection(double connection) {
        this.connection = connection;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFactor(double factor) {
        this.factor = factor;
    }
}
