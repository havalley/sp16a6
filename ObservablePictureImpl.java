package a6;

import java.util.ArrayList;

public class ObservablePictureImpl implements ObservablePicture {
	private Picture p;
	private ArrayList<ObserverRegistered> observers;

	public ObservablePictureImpl(Picture p) {
		this.p = p;
	}

	@Override
	public int getWidth() {
		return p.getWidth();
	}

	@Override
	public int getHeight() {
		return p.getHeight();
	}

	@Override
	public Pixel getPixel(int x, int y) {
		return p.getPixel(x,y);
	}

	@Override
	public Pixel getPixel(Coordinate c) {
		return p.getPixel(c);
	}

	@Override
	public void setPixel(int x, int y, Pixel p) {
		this.p.setPixel(x, y, p);
		
	}

	@Override
	public void setPixel(Coordinate c, Pixel p) {
		this.p.setPixel(c, p);
	}

	@Override
	public SubPicture extract(int xoff, int yoff, int width, int height) {
		return p.extract(xoff, yoff, width, height);
	}

	@Override
	public SubPicture extract(Coordinate a, Coordinate b) {
		return p.extract(a, b);
	}

	@Override
	public void registerROIObserver(ROIObserver observer, Region r) {
		observers.add(new ObserverRegisteredImpl(observer, r));
	}

	@Override
	public void unregisterROIObservers(Region r) {
		for(ROIObserver o : this.findROIObservers(r)) {
		observers.remove(o);
		}
	}

	@Override
	public void unregisterROIObserver(ROIObserver observer) {
		observers.remove(observer);

	}

	@Override
	public ROIObserver[] findROIObservers(Region r) {
		ArrayList<ROIObserver> found = null;
		
		for(ObserverRegistered o : observers) {
			Region[] matchingregions = o.getRegions();
			for(int i = 0; i < matchingregions.length; i++) {
				if(matchingregions[i].equals(r)) {
					found.add(o);
				}
			}
		}
		ROIObserver[] foundobservers = found.toArray(new ROIObserver[found.size()]);
		return foundobservers;
	}

	@Override
	public void suspendObservable() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resumeObservable() {
		// TODO Auto-generated method stub
	}

}
