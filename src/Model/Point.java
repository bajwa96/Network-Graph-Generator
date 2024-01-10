package Model;

public class Point implements Cloneable {
	private Integer vertex;
	private Double x;
	private Double y;

	public Point(Integer pointId, Double x, Double y) {
		this.vertex = pointId;
		this.x = x;
		this.y = y;
	}

	public Integer getVertex() {
		return vertex;
	}

	public void setVertex(Integer vertex) {
		this.vertex = vertex;
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	@Override
	public Object clone() {
		return new Point(this.vertex, this.x, this.y);
	}

	@Override
	public String toString() {
		return this.vertex + Constants.DELIMETER + this.x + Constants.DELIMETER + this.y;
	}

}
