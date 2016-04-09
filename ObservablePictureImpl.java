package a6;

import java.util.ArrayList;

public class ObservablePictureImpl implements ObservablePicture {
	private Picture p;
	private ArrayList<ROIObserverRegionImpl> observers;

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
		observers.add(new ROIObserverRegionImpl(observer, r));
	}

	@Override
	public void unregisterROIObservers(Region r) {
		observers.remove(this.findROIObservers(r));
	
	}

	@Override
	public void unregisterROIObserver(ROIObserver observer) {
		observers.remove(observer);

	}

	@Override
	public ROIObserver[] findROIObservers(Region r) {
		ROIObserver[] found;
		for(ROIObserverRegionImpl o : observers) {
			//trying to see how to find if there are observers
			//within the region of interest
			if(o.getBottom() < r.getTop() || r.getBottom() < o.getTop()) {
				//do nothing to this observer because there's no intersection
			} else if(o.getRight() < r.getLeft() || r.getRight() < o.getLeft()) {
				//do nothing to this observer because there's no intersection
			} else {
				observers.remove(o);
			}
		}
		// TODO Auto-generated method stub
		return null;
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
