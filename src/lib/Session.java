package lib;

import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;

@SuppressWarnings({"unused"})
public class Session extends Storeable {

    private long id;
    private String name;
    private LocalDate date;
    private Camera camera;
    private Filter filter;
    private Telescope telescope;
    private Lens lens;
    private double temp;
    private double exposure;
    private int number;
    private int gain;


    public Session(String name, LocalDate date, Camera camera, Filter filter, Telescope telescope, Lens lens,
                   double temp, double exposure, int number, int gain) {
        this.id = Session.generateId();
        this.name = name;
        this.date = date;
        this.camera = camera;
        this.filter = filter;
        this.telescope = telescope;
        this.lens = lens;
        this.temp = temp;
        this.exposure = exposure;
        this.number = number;
        this.gain = gain;
    }

    public Session(long id, String name, LocalDate date, Camera camera, Filter filter, Telescope telescope, Lens lens,
                   double temp, double exposure, int number, int gain) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.camera = camera;
        this.filter = filter;
        this.telescope = telescope;
        this.lens = lens;
        this.temp = temp;
        this.exposure = exposure;
        this.number = number;
        this.gain = gain;
    }

    public JSONObject toJSONObject() {
        JSONObject object = new JSONObject();
        object.put("id", id);
        object.put("name", name);
        object.put("date", getDateAsString());
        object.put("camera", camera != null ? camera.getId() : 0);
        object.put("filter", filter != null ? filter.getId() : 0);
        object.put("telescope", telescope != null ? telescope.getId() : 0);
        object.put("lens", lens != null ? lens.getId() : 0);
        object.put("temp", temp);
        object.put("exposure", exposure);
        object.put("number", number);
        object.put("gain", gain);
        return object;
    }

    public static Session fromJSONObject(JSONObject object) {
        Camera c = (Camera) Manager.getObject(object.getLong("camera"), Manager.cameras);
        Telescope t = (Telescope) Manager.getObject(object.getLong("telescope"), Manager.telescopes);
        Lens l = (Lens) Manager.getObject(object.getLong("lens"), Manager.lenses);
        Filter f = (Filter) Manager.getObject(object.getLong("filter"), Manager.filters);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate d = LocalDate.now();
        try {
            d = LocalDate.parse(object.getString("date"), formatter);
        } catch (DateTimeParseException ignored) {}

        return new Session(
                object.getLong("id"),
                object.getString("name"),
                d, c, f, t, l,
                object.getDouble("temp"),
                object.getDouble("exposure"),
                object.getInt("number"),
                object.getInt("gain")
        );
    }

    public String getDateAsString() {
        String day = date.getDayOfMonth() + "";
        if (date.getDayOfMonth()<10) day = "0" + day;
        String month = date.getMonthValue() + "";
        if (date.getMonthValue()<10) month = "0" + month;
        String year = date.getYear() + "";
        return day + "." + month + "." + year;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Session)
            return ((Session) obj).id == id;
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

    public Camera getCamera() {
        return camera;
    }

    public double getExposure() {
        return exposure;
    }

    public double getTemp() {
        return temp;
    }

    public Filter getFilter() {
        return filter;
    }

    public int getGain() {
        return gain;
    }

    public int getNumber() {
        return number;
    }

    public Lens getLens() {
        return lens;
    }

    public LocalDate getDate() {
        return date;
    }

    public Telescope getTelescope() {
        return telescope;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setExposure(double exposure) {
        this.exposure = exposure;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public void setGain(int gain) {
        this.gain = gain;
    }

    public void setLens(Lens lens) {
        this.lens = lens;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setTelescope(Telescope telescope) {
        this.telescope = telescope;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }
}
