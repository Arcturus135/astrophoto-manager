package gui;

import java.util.ArrayList;
import java.util.List;

public class Frames {

    public static List<CustomFrame> frames;

    public static void init() {
        frames = new ArrayList<>();
    }

    public static void update() {
        for (CustomFrame frame : frames) frame.update();
    }

}
