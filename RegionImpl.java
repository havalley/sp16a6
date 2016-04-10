package a6;

public class RegionImpl implements Region {
	/*
	 * Coordinates given as parameters to the constructor that indicate
	 * two opposite corners of the region object
	 */
	Coordinate a;
	Coordinate b;
	
	/*
	 * The x or y values associated with the appropriate corners:
	 * top: the y value associated with both of the top corners
	 * bottom: the y value associated with both of the bottom corners
	 * right: the x value associated with both of the right corners
	 * left: the x value associated with both of the left corners
	 */
	int top;
	int bottom;
	int right;
	int left;
	
	/*
	 * Takes two coordinate objects that represent opposite corners and 
	 * compares each individual x and y value to find the region's dimensions.
	 */
	public RegionImpl(Coordinate a, Coordinate b) {
		this.a = a;
		this.b = b;
		int ax = a.getX();
		int ay = a.getY();
		int bx = b.getX();
		int by = b.getY();
		this.left = ax < bx ? ax : bx;
		this.right = ax < bx ? bx : ax;
		this.top = ay < by ? ay : by;
		this.bottom = ay < by ? by : ay;
	}
	
	/*
	 * Checks whether a coordinate object falls within 
	 * a certain region, taking in a coordinate object as a 
	 * parameter and returning a boolean value.
	 */
	public boolean isWithinRegion(Coordinate coord) {
		boolean in = coord.getY() < bottom && coord.getY() > top 
				|| coord.getX() < right && coord.getX() > left 
				? true : false;
		return in;
	}
	
	/*
	 * Checks whether a coordinate object falls within
	 * a certain region, taking in a coordinate object and
	 * a region object as parameters and returning a boolean value.
	 */
	public boolean isWithinRegion(Coordinate coord, Region reg) {
		boolean in = coord.getY() < reg.getBottom() && coord.getY() > reg.getTop() 
				|| coord.getX() < reg.getRight() && coord.getX() > reg.getLeft()
				? true : false;
		return in;
	}

	@Override
	public Coordinate getUpperLeft() {
		Coordinate tlcorner = new Coordinate(left, top);
		return tlcorner;
	}

	@Override
	public Coordinate getLowerRight() {
		Coordinate brcorner = new Coordinate(right, bottom);
		return brcorner;
	}

	@Override
	public int getTop() {
		return top;
	}

	@Override
	public int getBottom() {
		return bottom;
	}

	@Override
	public int getLeft() {
		return left;
	}

	@Override
	public int getRight() {
		return right;
	}
	
	/*
	 * Throws an exception if there is no intersection or if 
	 * the parameter value is null. Only returns the intersecting 
	 * region as opposed to the union method returning the smallest region 
	 * encompassing both intersecting regions.
	 */
	@Override
	public Region intersect(Region other) throws NoIntersectionException {

		if(other==null) {
			throw new NoIntersectionException();
		}
		if(this.getBottom() < other.getTop() 
				|| other.getBottom() < this.getTop()) {
			throw new NoIntersectionException();
		}
		if(this.getRight() < other.getLeft() 
				|| other.getRight() < this.getLeft()) {
			throw new NoIntersectionException();
		}

		Region intersected;
		int aL = this.getLeft();
		int aR = this.getRight();
		int aT = this.getTop();
		int aB = this.getBottom();
		int bL = other.getLeft();
		int bR = other.getRight();
		int bT = other.getTop();
		int bB = other.getBottom();
		int intersectedL = (aL > bL) && (aL < bR || aL == bR) ? aL : bL;
		int intersectedR = (aR > bL || aR == bL) && (aR < bR) ? aR : bR;
		int intersectedT = (aT < bB || aT == bB) && (aT > bT) ? aT : bT;
		int intersectedB = (aB < bB) && (aB > bT || aB == bT ) ? aB : bB;
		Coordinate topleftcorner = new Coordinate(intersectedL, intersectedT);
		Coordinate bottomrightcorner = new Coordinate(intersectedR, intersectedB);
		intersected = new RegionImpl(topleftcorner, bottomrightcorner);
		return intersected;
	}


	/*
	 * Should return a region object that is the smallest
	 * area that encompasses both intersecting regions, as opposed to
	 * the intersect method returning only the region of intersection.
	 */
	@Override
	public Region union(Region other) {
		if(other==null) {
			return this;
		}
		int aL = this.getLeft();
		int aR = this.getRight();
		int aT = this.getTop();
		int aB = this.getBottom();
		int bL = other.getLeft();
		int bR = other.getRight();
		int bT = other.getTop();
		int bB = other.getBottom();
		Region united;
		int unitedl = aL < bL ? aL : bL;
		int unitedr = aR > bR ? aR : bR;
		int unitedt = aT < bT ? aT : bT;
		int unitedb = aB > bB ? aB : bB;
		Coordinate topleftcorner = new Coordinate(unitedl, unitedt);
		Coordinate bottomrightcorner = new Coordinate(unitedr, unitedb);
		united = new RegionImpl(topleftcorner, bottomrightcorner);
		return united;
	}

}
