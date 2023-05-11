package dam.main;

public class ColumnOrder {
	public final static int ASC =1;
	public final static int DESC =2;
	private int index;
	private String order;

	public ColumnOrder(int index, String order) {
		this.index = index;
		this.order = order;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	


}
