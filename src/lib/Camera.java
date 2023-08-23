package lib;

import org.json.JSONObject;

import java.util.Random;

public class Camera extends Storeable {

    private long id;
    private String name;
    private int mgp;
    private int resX;
    private int resY;
    private double connection;
    private boolean color;

    public Camera(String name, int mgp, int resX, int resY, double connection, boolean color) {
        this.id = Camera.generateId();
        this.name = name;
        this.mgp = mgp;
        this.resX = resX;
        this.resY = resY;
        this.connection = connection;
        this.color = color;
    }

    public Camera(long id, String name, int mgp, int resX, int resY, double connection, boolean color) {
        this.id = id;
        this.name = name;
        this.mgp = mgp;
        this.resX = resX;
        this.resY = resY;
        this.connection = connection;
        this.color = color;
    }

    public JSONObject toJSONObject() {
        JSONObject object = new JSONObject();
        object.put("id", id);
        object.put("name", name);
        object.put("mgp", mgp);
        object.put("resX", resX);
        object.put("resY", resY);
        object.put("connection", connection);
        object.put("color", color);
        return object;
    }

    public static Camera fromJSONObject(JSONObject object) {
        return new Camera(
                object.getLong("id"),
                object.getString("name"),
                object.getInt("mgp"),
                object.getInt("resX"),
                object.getInt("resY"),
                object.getDouble("connection"),
                object.getBoolean("color")
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Camera)
            return ((Camera) obj).id == id;
        return false;
    }

    public static long generateId() {
        return new Random().nextLong();
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMgp() {
        return mgp;
    }

    public int getResX() {
        return resX;
    }

    public int getResY() {
        return resY;
    }

    public double getConnection() {
        return connection;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMgp(int mgp) {
        this.mgp = mgp;
    }

    public void setResX(int resX) {
        this.resX = resX;
    }

    public void setResY(int resY) {
        this.resY = resY;
    }

    public void setConnection(double connection) {
        this.connection = connection;
    }

    public boolean isColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }
}
