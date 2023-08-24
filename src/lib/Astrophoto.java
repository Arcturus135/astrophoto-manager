package lib;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings({"unused", "unchecked"})
public class Astrophoto extends Storeable {

    private long id;
    private String name;
    private List<Session> sessions;
    private Camera camera;
    private List<Filter> filters;
    private Telescope telescope;
    private Lens lens;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private List<Double> temps;
    private double exposure;
    private List<Integer> gains;
    private boolean finished;
    private String path_to_img;
    private String programs;

    public Astrophoto(String name, boolean finished, String path_to_img, String programs) {
        this.id = Astrophoto.generateId();
        this.name = name;
        this.sessions = new ArrayList<>();
        this.camera = null;
        this.filters = new ArrayList<>();
        this.telescope = null;
        this.lens = null;
        this.dateStart = LocalDate.now();
        this.dateEnd = LocalDate.now();
        this.temps = new ArrayList<>();
        this.exposure = 0;
        this.gains = new ArrayList<>();
        this.finished = finished;
        this.path_to_img = path_to_img;
        this.programs = programs;
    }

    public Astrophoto(long id, String name, List<Session> sessions, Camera camera, List<Filter> filters,
                      Telescope telescope, Lens lens, LocalDate dateStart, LocalDate dateEnd, List<Double> temps,
                      double exposure, List<Integer> gains, boolean finished, String path_to_img, String programs) {
        this.id = id;
        this.name = name;
        this.sessions = sessions;
        this.camera = camera;
        this.filters = filters;
        this.telescope = telescope;
        this.lens = lens;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.temps = temps;
        this.exposure = exposure;
        this.gains = gains;
        this.finished = finished;
        this.path_to_img = path_to_img;
        this.programs = programs;
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject object = new JSONObject();
        object.put("id", id);
        object.put("name", name);
        object.put("sessions", getAsJSONArray(sessions));
        object.put("camera", camera != null ? camera.getId() : 0);
        object.put("filters", getAsJSONArray(filters));
        object.put("telescope", telescope != null ? telescope.getId() : 0);
        object.put("lens", lens != null ? lens.getId() : 0);
        object.put("dateStart", getDateAsString(dateStart));
        object.put("dateEnd", getDateAsString(dateEnd));
        object.put("temps", getTempsAsJSONArray());
        object.put("exposure", exposure);
        object.put("gains", getGainsAsJSONArray());
        object.put("finished", finished);
        object.put("path_to_img", path_to_img);
        object.put("programs", programs);
        return object;
    }

    public static Astrophoto fromJSONObject(JSONObject object) {
        List<Session> s = (List<Session>) getFromJSONArray(object.getJSONArray("sessions"), Manager.sessions);
        Camera c = object.getLong("camera") != 0 ? (Camera) Manager.getObject(object.getLong("camera"), Manager.cameras) : null;
        List<Filter> f = (List<Filter>) getFromJSONArray(object.getJSONArray("filters"), Manager.filters);
        Telescope t = object.getLong("telescope") != 0 ? (Telescope) Manager.getObject(object.getLong("telescope"), Manager.telescopes) : null;
        Lens l = object.getLong("lens") != 0 ? (Lens) Manager.getObject(object.getLong("lens"), Manager.lenses) : null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate ds = LocalDate.now();
        LocalDate de = LocalDate.now();
        try {
            ds = LocalDate.parse(object.getString("dateStart"), formatter);
            de = LocalDate.parse(object.getString("dateEnd"), formatter);
        } catch (DateTimeParseException ignored) {}
        List<Double> ts = getTempsFromJSONArray(object.getJSONArray("temps"));
        List<Integer> gs = getGainsFromJSONArray(object.getJSONArray("gains"));


        return new Astrophoto(
                object.getLong("id"),
                object.getString("name"),
                s, c, f, t, l, ds, de, ts,
                object.getDouble("exposure"), gs,
                object.getBoolean("finished"),
                object.getString("path_to_img"),
                object.getString("programs")
        );
    }

    private JSONArray getAsJSONArray(List<? extends Storeable> list) {
        JSONArray array = new JSONArray();
        for (Storeable storeable : list) array.put(storeable.getId());
        return array;
    }

    private JSONArray getTempsAsJSONArray() {
        JSONArray array = new JSONArray();
        for (double d : temps) array.put(d);
        return array;
    }

    private JSONArray getGainsAsJSONArray() {
        JSONArray array = new JSONArray();
        for (int g : gains) array.put(g);
        return array;
    }

    private static List<? extends Storeable> getFromJSONArray(JSONArray array, List<? extends Storeable> l) {
        List<Storeable> list = new ArrayList<>();
        for (int i=0;i<array.length();i++) {
            Storeable storeable = Manager.getObject(array.getLong(i), l);
            if (storeable != null) list.add(storeable);
        }
        return list;
    }

    private static List<Double> getTempsFromJSONArray(JSONArray array) {
        List<Double> list = new ArrayList<>();
        for (int i=0;i<array.length();i++) {
            list.add(array.getDouble(i));
        }
        return list;
    }

    private static List<Integer> getGainsFromJSONArray(JSONArray array) {
        List<Integer> list = new ArrayList<>();
        for (int i=0;i<array.length();i++) {
            list.add(array.getInt(i));
        }
        return list;
    }

    public String getDateAsString(LocalDate date) {
        String day = date.getDayOfMonth() + "";
        if (date.getDayOfMonth()<10) day = "0" + day;
        String month = date.getMonthValue() + "";
        if (date.getMonthValue()<10) month = "0" + month;
        String year = date.getYear() + "";
        return day + "." + month + "." + year;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Astrophoto)
            return ((Astrophoto) obj).id == id;
        return false;
    }

    public static long generateId() {
        return new Random().nextLong();
    }

    public void update() {
        if (sessions.isEmpty()) {
            this.camera = null;
            this.filters = new ArrayList<>();
            this.telescope = null;
            this.lens = null;
            this.dateStart = LocalDate.now();
            this.dateEnd = LocalDate.now();
            this.temps = new ArrayList<>();
            this.exposure = 0;
            this.gains = new ArrayList<>();
        } else {
            this.camera = sessions.get(0).getCamera();
            this.filters = new ArrayList<>();
            for (Session session : sessions) if (session.getFilter() != null)
                if (!filters.contains(session.getFilter()))
                    filters.add(session.getFilter());
            this.telescope = sessions.get(0).getTelescope();
            this.lens = sessions.get(0).getLens();
            this.dateStart = getLowestDate();
            this.dateEnd = getHighestDate();
            this.temps = new ArrayList<>();
            for (Session session : sessions) if (!temps.contains(session.getTemp())) temps.add(session.getTemp());
            this.exposure = 0;
            for (Session session : sessions) exposure = exposure + (session.getExposure()*session.getNumber());
            this.gains = new ArrayList<>();
            for (Session session : sessions) if (!gains.contains(session.getGain())) gains.add(session.getGain());
        }
    }

    private LocalDate getLowestDate() {
        if (sessions.isEmpty()) return LocalDate.now();
        LocalDate lowest = LocalDate.MAX;
        for (Session session : sessions) if (session.getDate().isBefore(lowest)) lowest = session.getDate();
        return lowest;
    }

    private LocalDate getHighestDate() {
        if (sessions.isEmpty()) return LocalDate.now();
        LocalDate highest = LocalDate.MIN;
        for (Session session : sessions) if (session.getDate().isAfter(highest)) highest = session.getDate();
        return highest;
    }

    public void addSession(Session session) {
        sessions.add(session);
        update();
    }

    public void removeSession(Session session) {
        sessions.remove(session);
        update();
    }

    public void removeSession(int index) {
        sessions.remove(index);
        update();
    }

    public List<String> getTempsWithUnit() {
        List<String> list = new ArrayList<>();
        for (double temp : temps) list.add(temp + "°C");
        return list;
    }

    public String getExposureSimple() {
        if (exposure >= 1) {
            int mil = (int) ((exposure % 1)*1000);
            int sec = (int) (exposure);
            if (sec >= 60) {
                sec = (int) (exposure % 60);
                int min = (int) (exposure / 60);
                if (min >= 60) {
                    min = (int) (exposure % 3600);
                    int h = (int) (exposure / 3600);
                    if (h >= 24) {
                        h = (int) (exposure % (3600*24));
                        int d = (int) (exposure /(3600*24));
                        return expRemoveZero(d, "d ", h, "h ", min, "m ", sec, "s ", mil, "ms");
                    } else return expRemoveZero(h, "h ", min, "m ", sec, "s ", mil, "ms");
                } else return expRemoveZero(min, "m ", sec, "s ", mil, "ms");
            } else return expRemoveZero(sec, "s ", mil, "ms");
        } else return expRemoveZero(exposure*1000, "ms");
    }

    private String expRemoveZero(Object... args) {
        StringBuilder out = new StringBuilder();
        for (int i=0;i<args.length;i=i+2)
            if (!args[i].toString().equalsIgnoreCase("0"))
                out.append(args[i].toString()).append(args[i + 1].toString());
        return out.toString();
    }

    public Telescope getTelescope() {
        return telescope;
    }

    public Lens getLens() {
        return lens;
    }

    public List<Double> getTemps() {
        return temps;
    }

    public List<Integer> getGains() {
        return gains;
    }

    public double getExposure() {
        return exposure;
    }

    public Camera getCamera() {
        return camera;
    }

    public String getName() {
        return name;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public String getPath_to_img() {
        return path_to_img;
    }

    public String getPrograms() {
        return programs;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setTemps(List<Double> temps) {
        this.temps = temps;
    }

    public void setTelescope(Telescope telescope) {
        this.telescope = telescope;
    }

    public void setLens(Lens lens) {
        this.lens = lens;
    }

    public void setGains(List<Integer> gains) {
        this.gains = gains;
    }

    public void setExposure(double exposure) {
        this.exposure = exposure;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void setPath_to_img(String path_to_img) {
        this.path_to_img = path_to_img;
    }

    public void setPrograms(String programs) {
        this.programs = programs;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }
}
