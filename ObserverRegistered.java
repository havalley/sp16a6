package a6;

import java.util.ArrayList;

public interface ObserverRegistered extends ROIObserver {
	public ROIObserver getWrappedROIObserver();
	public Region[] getRegions();
	ArrayList<Region> getRegionsAL();
}
