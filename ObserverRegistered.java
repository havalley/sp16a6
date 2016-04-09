package a6;

public interface ObserverRegistered extends ROIObserver {
	public ROIObserver getWrappedROIObserver();
	public Region[] getRegions();
}
