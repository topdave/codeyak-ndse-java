package net.codeyak.ndse.v2;

public class OOCoordDimension {

	private final int order;
	private final String name;

	public OOCoordDimension(int order, String name) {
		super();
		this.order = order;
		this.name = name;
	}

	public int getOrder() {
		return order;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return order;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OOCoordDimension other = (OOCoordDimension) obj;
		if (order != other.order)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OOCoordDimension [order=" + order + ", name=" + name + "]";
	}

	public String toNiceString() {
		return order + "." + name;
	}

}
