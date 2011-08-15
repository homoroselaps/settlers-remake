package jsettlers.common.map.shapes;

import java.util.Iterator;
import java.util.List;

import jsettlers.common.position.ISPosition2D;
import jsettlers.common.position.SRectangle;

/**
 * This class gives a fast lookup (in O(1)) for contains if a MapArea is given by a list of n positions.<br>
 * This class should only be used if the given positions are NOT distributed over big parts of the map. They should be positioned quite close to each
 * other.
 * 
 * @author Andreas Eberle
 * 
 */
public class FreeMapArea implements IMapArea {
	private final List<ISPosition2D> positions;
	private final short xOffset;
	private final short yOffset;
	private final boolean[][] areaMap;
	private final int width;
	private final int height;

	/**
	 * 
	 * @param positions
	 *            the positions this map area shall contain.
	 */
	public FreeMapArea(List<ISPosition2D> positions) {
		assert positions.size() > 0 : "positions must contain at least one value!!";

		this.positions = positions;
		SRectangle bounds = getBounds(positions);

		xOffset = bounds.getXMin();
		yOffset = bounds.getYMin();
		width = bounds.getWidth() + 1;
		height = bounds.getHeight() + 1;

		areaMap = new boolean[width][height];
		setPositionsToMap(areaMap, positions);
	}

	private void setPositionsToMap(boolean[][] areaMap, List<ISPosition2D> positions2) {
		for (ISPosition2D curr : positions) {
			areaMap[getMapX(curr)][getMapY(curr)] = true;
		}
	}

	private int getMapY(ISPosition2D pos) {
		return pos.getY() - yOffset;
	}

	private int getMapX(ISPosition2D pos) {
		return pos.getX() - xOffset;
	}

	private SRectangle getBounds(List<ISPosition2D> positions) {
		short xMin = Short.MAX_VALUE, xMax = 0, yMin = Short.MAX_VALUE, yMax = 0;

		for (ISPosition2D curr : positions) {
			short x = curr.getX();
			short y = curr.getY();
			if (x < xMin)
				xMin = x;
			if (x > xMax)
				xMax = x;

			if (y < yMin)
				yMin = y;
			if (y > yMax)
				yMax = y;
		}

		return new SRectangle(xMin, yMin, xMax, yMax);
	}

	@Override
	public boolean contains(ISPosition2D pos) {
		return isValidPos(pos) && areaMap[getMapX(pos)][getMapY(pos)];
	}

	private boolean isValidPos(ISPosition2D pos) {
		int dx = pos.getX() - xOffset;
		int dy = pos.getY() - yOffset;
		return dx >= 0 && dy >= 0 && dx < width && dy < height;
	}

	@Override
	public Iterator<ISPosition2D> iterator() {
		return positions.iterator();
	}

	public int size() {
		return positions.size();
	}

	public ISPosition2D get(int i) {
		return positions.get(i);
	}
}
