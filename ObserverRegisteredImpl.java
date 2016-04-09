package a6;

import java.util.ArrayList;

public class ObserverRegisteredImpl implements ObserverRegistered {
	private ROIObserver observer;
	private ArrayList<Region> regions;

	public ObserverRegisteredImpl(ROIObserver observer, Region r) {
		this.observer = observer;
		regions.add(r);
	}
	
	@Override
	public void notify(ObservablePicture picture, Region changed_region) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ROIObserver getWrappedROIObserver() {
		return observer;
	}

	@Override
	public Region[] getRegions() {
		return regions.toArray(new Region[regions.size()]);
	}

}
