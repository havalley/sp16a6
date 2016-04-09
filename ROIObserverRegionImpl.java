package a6;

public class ROIObserverRegionImpl implements ROIObserverRegion {
	private ROIObserver observer;
	private Region region;
	
	public ROIObserverRegionImpl(ROIObserver observer, Region region) {
		this.observer = observer;
		this.region = region;
	}

	@Override
	public void notify(ObservablePicture picture, Region changed_region) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Coordinate getUpperLeft() {
		return region.getUpperLeft();
	}

	@Override
	public Coordinate getLowerRight() {
		return region.getLowerRight();
	}

	@Override
	public int getTop() {
		return region.getTop();
	}

	@Override
	public int getBottom() {
		return region.getBottom();
	}

	@Override
	public int getLeft() {
		return region.getLeft();
	}

	@Override
	public int getRight() {
		return region.getRight();
	}

	@Override
	public Region intersect(Region other) throws NoIntersectionException {
		return region.intersect(other);
	}

	@Override
	public Region union(Region other) {
		return region.union(other);
	}

	@Override
	public ROIObserver getWrappedROIObserver() {
		return observer;
	}

	@Override
	public Region getWrappedRegion() {
		return region;
	}

}
