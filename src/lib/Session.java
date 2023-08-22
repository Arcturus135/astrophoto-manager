package lib;

import java.time.LocalDate;
import java.util.Random;

public class Session {

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
