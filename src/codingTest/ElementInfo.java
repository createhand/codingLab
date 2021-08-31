package codingTest;

public class ElementInfo {
	public static final int TYPE_STRING = 1;
	public static final int TYPE_INTEGER = 2;
	public static final int TYPE_LONG = 3;
	public static final int TYPE_FLOAT = 4;
	public static final int TYPE_DOUBLE = 5;
	public static final int TYPE_BIN = 6;
	public static final int TYPE_ARRAY = 7;
	public static final int TYPE_ARRAY_INFO = 8;
	public static final int REF_INFO = 1;
	public static final int REF_LENGTH = 2;
	String name;
	public int length;
	int type;
	String arrayName;
	ElementInfo[] element;
	int refType;
	String arrayCntName;

	public ElementInfo(String name, int length) {
		this(name, length, 1, null);
	}

	public ElementInfo(String name, int length, int type) {
		this(name, length, type, null);
	}

	public ElementInfo(String name, int length, int type, String arrayName) {
		this.name = name;
		this.length = length;
		this.type = type;
		this.arrayName = arrayName;
	}

	public ElementInfo(String name, int length, int type, ElementInfo[] element, int refType) {
		this.name = name;
		this.length = length;
		this.type = type;
		this.element = element;
		this.refType = refType;
	}
	
	public ElementInfo(String name, int length, int type, ElementInfo[] element, int refType, String arrayCntName) {
		this.name = name;
		this.length = length;
		this.type = type;
		this.element = element;
		this.refType = refType;
		this.arrayCntName = arrayCntName;
	}

	public String getName() {
		return this.name;
	}
}
