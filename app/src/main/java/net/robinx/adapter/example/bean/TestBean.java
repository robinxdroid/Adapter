package net.robinx.adapter.example.bean;

public class TestBean {

	public static final int VIEW_TYPE_1 = 0; // view类型0
	public static final int VIEW_TYPE_2 = 1; // view类型2

	public TestBean() {
		super();
	}

	public TestBean(String name) {
		this.name = name;
	}

	public TestBean(String name, int type) {
		super();
		this.name = name;
		this.type = type;
	}

	private String name;

	private int type;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "TestBean [name=" + name + "]";
	}
	
}
