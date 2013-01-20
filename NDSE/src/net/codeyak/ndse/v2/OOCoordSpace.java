package net.codeyak.ndse.v2;

/**
 * represents an n-dimensional Cartesian coordinate system
 * @author dave_blake
 *
 */
public class OOCoordSpace {

	private final OOCoordDimension[] dimensions;
	
	private int[] sizes;
	
	private int[] shifts;
	
	private OOCoord[] space;
	
	public OOCoordSpace(OOCoordDimension... dimensions) {
		this.dimensions = dimensions;
	}
	
	public void setSizes(int... sizes) {
		if (sizes.length != dimensions.length) throw new OOCoordException();
		this.sizes = sizes;
		build();
	}
	
	public int getSize(int dim) {
		return sizes[dim];
	}

	public int getTotalVolume() {
		int rv = 1;
		for (int s : sizes) {
			rv *= s;
		}
		return rv;
	}
	
	private void build() {
		this.shifts = new int[sizes.length];
		int m = 1;
		for (int i=0; i<sizes.length; i++) {
			shifts[i] = m;
			m *= sizes[i];
		}
		space = new OOCoord[getTotalVolume()];
		for (int i=0; i<space.length; i++) {
			int[] coords = new int[sizes.length];
			int id = i;
			for (int j=0; j<sizes.length; j++) {
				coords[j] = id % sizes[j];
				id /= sizes[j];
			}
			space[i] = new OOCoord(i, coords);
		}
	}
	
	public OOCoord getCoordById(int id) {
		return space[id];
	}
	
	public OOCoord getCoord(int... coords) {
		int id = getId(coords);
		return getCoordById(id);
	}
	
	public int getId(int... coords) {
		if (coords.length != dimensions.length) throw new OOCoordException();
		int id = 0;
		for (int d = dimensions.length - 1; d > 0; d--) {
			id += coords[d];
			id *= sizes[d-1];
		}
		id += coords[0];
		return id;
	}
	
	public OOCoord getAdjacent(final OOCoord coord, final OOCoordDimension dim, final OOCoordArrow arrow) {
		int pos = coord.getPos(dim.getOrder());
		pos += arrow.sign;
		if (pos < 0 || pos >= sizes[dim.getOrder()]) return null;
		//find neighbour
		int id = coord.getId();
		id += arrow.sign * shifts[dim.getOrder()];
		return space[id];
	}

	public OOCoord[] getNeighbours(final OOCoord coord) {
		OOCoord[] rv = new OOCoord[dimensions.length * 2];
		int p = 0;
		for (OOCoordDimension dim : dimensions) {
			rv[p++] = getAdjacent(coord, dim, OOCoordArrow.NEGATIVE);
			rv[p++] = getAdjacent(coord, dim, OOCoordArrow.POSITIVE);
		}
		return rv;
	}
}
