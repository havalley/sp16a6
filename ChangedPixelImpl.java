package a6;

public class ChangedPixelImpl implements ChangedPixel {
	private Pixel p;
	private Coordinate c;
	
	public ChangedPixelImpl(Pixel p, Coordinate c) {
		this.p = p;
		this.c = c;
	}

	@Override
	public double getRed() {
		return p.getRed();
	}

	@Override
	public double getBlue() {
		return p.getBlue();
	}

	@Override
	public double getGreen() {
		return p.getGreen();
	}

	@Override
	public double getIntensity() {
		return p.getIntensity();
	}

	@Override
	public char getChar() {
		return p.getChar();
	}

	@Override
	public Pixel getPixel() {
		return p;
	}

	@Override
	public Coordinate getCoordinate() {
		return c;
	}

}
