package lib;

import org.json.JSONObject;

public abstract class Storeable {

    public abstract JSONObject toJSONObject();
    public abstract long getId();
}
