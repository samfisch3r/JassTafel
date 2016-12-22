package ch.zhaw.jasstafel;

public class Limit {

	private final String[] limits = {"1000", "1500", "2000", "2500", "5000"};

	public String[] getLimits() {
		return limits;
	}
	
	public String getLimits(int value) {
		return limits[value];
	}
	
	public int getLimitsValue(int value) {
		return Integer.parseInt(limits[value]);
	}
	
	public int getLength() {
		return limits.length;
	}
}
