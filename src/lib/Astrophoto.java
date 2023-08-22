package lib;

import java.time.LocalDateTime;

public class Astrophoto {

    private long id;
    private String name;
    private Session[] sessions;
    private Camera camera;
    private Filter[] filter;
    private Telescope telescope;
    private Lens lens;
    private double temp;
    private LocalDateTime dateTime;
    private double exposure;
    private int gain;
    private String path_to_img;
    private String programs;

}
