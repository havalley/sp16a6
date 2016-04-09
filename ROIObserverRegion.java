package a6;

public interface ROIObserverRegion extends ROIObserver, Region {
	public ROIObserver getWrappedROIObserver();
	public Region getWrappedRegion();
}
