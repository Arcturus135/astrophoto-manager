package lib;

import org.json.JSONObject;

import java.util.Random;

public class Telescope extends Equipment {

    private long id;
    private String name;
    private int aperture;
    private int focal_length;

    public Telescope(String name, int aperture, int focal_length) {
        this.id = Telescope.generateId();
        this.name = name;
        this.aperture = aperture;
        this.focal_length = focal_length;
    }

    public Telescope(long id, String name, int aperture, int focal_length) {
        this.id = id;
        this.name = name;
        this.aperture = aperture;
        this.focal_length = focal_length;
    }

    public JSONObject toJSONObject() {
        JSONObject object = new JSONObject();
        object.put("id", id);
        object.put("name", name);
        object.put("aperture", aperture);
        object.put("focal_length", focal_length);
        return object;
    }

    public static Telescope fromJSONObject(JSONObject object) {
        return new Telescope(
                object.getLong("id"),
                object.getString("name"),
                object.getInt("aperture"),
                object.getInt("focal_length")
        );
    }

    public int getAperture() {
        return aperture;
    }

    public int getFocal_length() {
        return focal_length;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setAperture(int aperture) {
        this.aperture = aperture;
    }

    public void setFocal_length(int focal_length) {
        this.focal_length = focal_length;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Telescope)
            return ((Telescope) obj).id == id;
        return false;
    }

    public static long generateId() {
        return new Random().nextLong();
    }
}
