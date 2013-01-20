package net.codeyak.ndse.v2;

/**
 * represents a coordinate in an n-dimensional Cartesian coordinate system
 * 
 * @author dave_blake
 *
 */
public class OOCoord {

	private final int id;
	
	private final int[] pos;
	
	public OOCoord(int id, int... pos) {
		super();
		this.id = id;
		this.pos = pos;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for (int i=0; i<pos.length; i++) {
			if (i > 0) sb.append(",");
			sb.append(pos[i]);
		}
		sb.append(")");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OOCoord other = (OOCoord) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public int getId() {
		return id;
	}
	
	public int getPos(int dim) {
		return pos[dim];
	}
	
}
