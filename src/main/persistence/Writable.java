package persistence;

import org.json.JSONObject;

// Writable interface with the method toJson to create Json representation of objects
public interface Writable {

    // EFFECTS: return this as a Json object
    public JSONObject toJson();
}
