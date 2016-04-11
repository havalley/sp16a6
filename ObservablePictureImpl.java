package a6;

import java.util.ArrayList;

public class ObservablePictureImpl implements ObservablePicture {
	private Picture p;
	private ArrayList<ObserverRegistered> observers;
	private ArrayList<Pixel> changedPixels;
	private boolean isSuspended;

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
		Coordinate coord = new Coordinate(x,y);
		for(ObserverRegistered o : observers) {
			changedPixels.add(new ChangedPixelImpl(p,coord));
			RegionImpl[] roi = (RegionImpl[])o.getRegions();
			for(int i = 0; i < roi.length; i++) {
				if(roi[i].isWithinRegion(coord) && !isSuspended) {
					o.notify();
				}
			}
		}
	}

	@Override
	public void setPixel(Coordinate c, Pixel p) {
		this.p.setPixel(c, p);
		/*
		 * loops through the list of observers registered
		 * with particular regions
		 */
		for(ObserverRegistered o : observers) {
			/*
			 * creates a new instance of a ChangedPixel that
			 * stores references to individual pixel and coordinate objects
			 */
			changedPixels.add(new ChangedPixelImpl(p,c));
			/*
			 * an arraylist of each observer's regions
			 */
			ArrayList<Region> roi = o.getRegionsAL();
			/*
			 * iterates through the collection of regions associated with each
			 * registered observer object and notifies the observers if pixels 
			 * have changed within the region
			 */
			for(Region r : roi) {
				if(((RegionImpl) r).isWithinRegion(c) && !isSuspended) {
					o.notify();
				}
			}
		}
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
		observers.add(new ObserverRegisteredImpl((ROIObserver)observer, r));
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

	@SuppressWarnings("null")
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

	/*
	 * Creates a union of all changed pixels while
	 * the observable is suspended
	 */
	@Override
	public void suspendObservable() {
		isSuspended = true;
		changedPixels.removeAll(changedPixels);
	}

	/*
	 * helper methods for 
	 */
	private Coordinate findSmallest(ChangedPixel[] cp) {
		int smx = 0;
		int smy = 0;
		for(int i = 0; i < cp.length; i ++) {
			smx = cp[i].getCoordinate().getX() < smx ? cp[i].getCoordinate().getX() : smx;
			smy = cp[i].getCoordinate().getY() < smy ? cp[i].getCoordinate().getY() : smy;			
		}
		Coordinate smallest = new Coordinate(smx, smy);
		return smallest;
	}

	private Coordinate findLargest(ChangedPixel[] cp) {
		int lgx = 0;
		int lgy = 0;	
		for(int i = 0; i < cp.length; i ++) {
			lgx = cp[i].getCoordinate().getX() > lgx ? cp[i].getCoordinate().getX() : lgx;
			lgy = cp[i].getCoordinate().getY() > lgy ? cp[i].getCoordinate().getY() : lgy;
		}
		Coordinate largest = new Coordinate(lgx, lgy);
		return largest;
	}

	/*
	 * Notifies the intersection of the ROI and
	 * the union of all the changed pixels while
	 * the observable was suspended intersection 
	 * of the changed and the observed region
	 */
	@Override
	public void resumeObservable() {
		isSuspended = false;
		ChangedPixel[] cpix = changedPixels.toArray(new ChangedPixel[changedPixels.size()]);
		Region changedRegion = new RegionImpl(this.findSmallest(cpix),this.findLargest(cpix));
		/*
		 * Loops through the observers ArrayList of ObserverRegistered objects
		 * and creates an array of RegionImpl objects per region, filling it with
		 * the regions retrieved from each observer's getRegions() method. Then for
		 * every region the observer is registered with, it needs to check for 
		 * changed pixels and notify.
		 */
		for(ROIObserver o : this.findROIObservers(changedRegion)) {
			o.notify();
		}
	}

}
