package trail;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Location implements Comparable<Location>{
	
    private String name;
	private int orderNum;
	Map<String, Delegate> delegates = new TreeMap<>();
	Map<Runner, Long> passaggio = new HashMap<>();
	Map<Long, Runner> passaggioPerTempo = new TreeMap<>();
	
	public Location(String name, int orderNum) {
		this.name = name;
		this.orderNum = orderNum;
	}

	public String getName(){
        return name;
    }

    public int getOrderNum(){
        return orderNum;
    }

	public void addDelegate(String id, Delegate d) {
		delegates.put(id, d);
	}

	public void addPassaggio(Runner r, long oraPass) {
		passaggio.put(r, oraPass);
	}
	
	public void addPassaggioPerTempo(long oraPass, Runner r) {
		passaggioPerTempo.put(oraPass, r);
	}

	public int compareTo(Location o) {
		return this.orderNum-o.orderNum;
	}
	
}
