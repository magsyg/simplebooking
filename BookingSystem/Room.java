package BookingSystem;

public class Room {
    private String name;
    private boolean isDropIn;
    private boolean hasWindow;
    private float ovenTemperature;

    public Room(String name, boolean isDropIn, boolean hasWindow, float ovenTemperature) {
        this.name = name;
        this.isDropIn = isDropIn;
        this.hasWindow = hasWindow;
        this.ovenTemperature = ovenTemperature;
    };

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getHasWindow() {
        return this.hasWindow;
    }

    public boolean getIsDropIn() {
        return this.isDropIn;
    }

    public float getOvenTemperature() {
        return this.ovenTemperature;
    }

    public float getTemperature(float outDoorTemperature) {
        if (this.getHasWindow()) {
            return (this.getOvenTemperature() + outDoorTemperature) * 0.5f; // use multiplication since faster than division
        }
        return (this.getOvenTemperature() + 2) * 0.1f * outDoorTemperature;
    };

    @Override
    public boolean equals(Object object)
    {
        if (object != null && object instanceof Room)
        {
            return this.getName().equals(((Room) object).getName());
        }
        return false;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
