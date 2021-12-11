package nanocovax;

public class Hospital {
    String id, name;
    int capacity, occupancy;

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getCapacity() {
        return capacity;
    }
    public int getStatus() {
        return occupancy;
    }

    public void setId(String srcId) {
        id = srcId;
    }
    public void setName(String srcName) {
        name = srcName;
    }
    public void setCapacity(int srcCapacity) {
        capacity = srcCapacity;
    }
    public void setOccupancy(int srcOccupancy) {
        occupancy = srcOccupancy;
    }
}
