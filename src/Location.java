class Location {
    float x;
    float y;

    public Location(float x, float y) {
	super();

	this.x = x;
	this.y = y;
    }

    @Override
    public String toString() {
	return "Location [x=" + x + ", y=" + y + "]";
    }
    
    static public float getDist(float x_center, float y_center, float x_axis,
	    float y_axis) {
	// double result = 0;
	float dist = (float) Math.sqrt((x_axis - x_center) * (x_axis - x_center)
		+ (y_axis - y_center) * (y_axis - y_center));
	// double distmax =

	return dist;
    }
    
}