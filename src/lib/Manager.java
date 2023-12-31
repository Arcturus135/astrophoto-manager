package lib;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked"})
public class Manager {

    public static List<Camera> cameras;
    public static List<Telescope> telescopes;
    public static List<Lens> lenses;
    public static List<Filter> filters;
    public static List<Session> sessions;
    public static List<Astrophoto> astrophotos;

    public static void init() {
        cameras = reloadCameras();
        telescopes = reloadTelescopes();
        lenses = reloadLenses();
        filters = reloadFilters();
        sessions = reloadSessions();
        astrophotos = reloadAstrophotos();
    }

    public static Storeable getObject(long id, List<? extends Storeable> list) {
        for (Storeable storeable : list) if (storeable.getId() == id) return storeable;
        return null;
    }

    private static JSONArray loadArray(String path) {
        try {
            FileReader fileReader = new FileReader(path);
            JSONTokener tokener = new JSONTokener(fileReader);
            return new JSONArray(tokener);
        } catch (FileNotFoundException e) {
            try {
                PrintWriter writer = new PrintWriter(path);
                writer.flush();
                writer.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } catch (Exception ignored) {
        }
        return new JSONArray();
    }

    public static List<Camera> reloadCameras() {
        return (List<Camera>) reload("cameras.json", Camera.class);
    }

    public static List<Telescope> reloadTelescopes() {
        return (List<Telescope>) reload("telescopes.json", Telescope.class);
    }

    public static List<Lens> reloadLenses() {
        return (List<Lens>) reload("lenses.json", Lens.class);
    }

    public static List<Filter> reloadFilters() {
        return (List<Filter>) reload("filters.json", Filter.class);
    }

    public static List<Session> reloadSessions() {
        return (List<Session>) reload("sessions.json", Session.class);
    }

    public static List<Astrophoto> reloadAstrophotos() {
        return (List<Astrophoto>) reload("astrophotos.json", Astrophoto.class);
    }

    public static List<? extends Storeable> reload(String path, Class<? extends Storeable> clazz) {
        JSONArray array = loadArray(path);
        List<Storeable> list = new ArrayList<>();
        if (array.isEmpty()) return list;

        for (int i=0;i<array.length();i++) {
            JSONObject object = array.getJSONObject(i);
            try {
                Method m = clazz.getMethod("fromJSONObject", JSONObject.class);
                Storeable storeable = (Storeable) m.invoke(null, object);
                list.add(clazz.cast(storeable));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        return list;
    }

    public static void saveCameras() {
        save("cameras.json", cameras);
    }

    public static void saveTelescopes() {
        save("telescopes.json", telescopes);
    }

    public static void saveLenses() {
        save("lenses.json", lenses);
    }

    public static void saveFilters() {
        save("filters.json", filters);
    }

    public static void saveSessions() {
        save("sessions.json", sessions);
    }

    public static void saveAstrophotos() {
        save("astrophotos.json", astrophotos);
    }

    public static void save(String path, List<? extends Storeable> list) {
        JSONArray array = new JSONArray(list.size());
        if (!list.isEmpty())
            for (Storeable storeable : list) {
                array.put(storeable.toJSONObject());
            }

        try {
            FileWriter fileWriter = new FileWriter(path);

            fileWriter.write(array.toString());

            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
