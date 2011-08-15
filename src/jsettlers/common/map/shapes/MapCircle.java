package jsettlers.common.map.shapes;

import java.util.Iterator;

import jsettlers.common.position.ISPosition2D;

/**
 * This class represents a circular area of the map.
 * <p>
 * It contains all elements whose distance to the center is smaller or equal to radius.
 * <p>
 * Geometry calculations (a is the constant {@link MapCircle#Y_SCALE}:
 * <p>
 * <img src="doc-files/MapCircle-1.png">
 * 
 * @author michael
 */
public class MapCircle implements IMapArea {
	private final float radius;
	private final short cy;
	private final short cx;

	/**
	 * Factor so that d((0,0), (1,1)) is almoast 1.
	 */
	final static float Y_SCALE = (float) Math.sqrt(3) / 2.0f * .999999f;

	public MapCircle(ISPosition2D pos, float radius) {
		this(pos.getX(), pos.getY(), radius);
	}

	public MapCircle(short cx, short cy, float radius) {
		this.cx = cx;
		this.cy = cy;
		this.radius = radius;
	}

	@Override
	public boolean contains(ISPosition2D position) {
		return contains(position.getX(), position.getY());
	}

	public boolean contains(int x, int y) {
		float distance = squaredDistanceToCenter(x, y);
		return distance <= radius * radius;
	}

	@Override
	public Iterator<ISPosition2D> iterator() {
		return new CircleIterator(this);
	}

	/**
	 * Gets the distance of map coordinates to the center.
	 * 
	 * @param x
	 *            The x coordinate.
	 * @param y
	 *            The y coordinate
	 * @return The distance to the center of this circle, so that the tiles around the center all have distance 1.
	 */
	private float squaredDistanceToCenter(int x, int y) {
		int dx = x - getCenterX();
		int dy = y - getCenterY();
		return (.25f + Y_SCALE * Y_SCALE) * dy * dy + dx * dx - dx * dy;
	}

	/**
	 * Gets the half with of a line, roundend.
	 * @param relativey The x coordinate of the line relative to the center
	 * @return The width of the line, NAN if the line is outside the circle.
	 */
	protected float getHalfLineWidth(int relativey) {
		double maximum =
		        Math.sqrt(radius * radius - relativey * MapCircle.Y_SCALE
		                * relativey * MapCircle.Y_SCALE);
		if (relativey % 2 == 0) {
			// round to tiles.
			return (float) Math.floor(maximum);
		} else {
			// uneven line, round x to *.5
			return (float) (Math.floor(maximum + .5) - .5);
		}
	}

	public double distanceToCenter(int x, int y) {
		return Math.sqrt(squaredDistanceToCenter(x, y));
	}

	public double getRadius() {
	    return radius;
    }

	public short getCenterY() {
	    return cy;
    }

	public short getCenterX() {
	    return cx;
    }
}
