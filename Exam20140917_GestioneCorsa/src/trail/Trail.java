package trail;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Trail {

	int pettorale = 1;
	int orderNum = 0;
	Map<Integer, Runner> runners = new TreeMap<>();
	Map<String, Location> locations = new HashMap<>();
	Map<String, Delegate> delegates = new TreeMap<>();
	
    public int newRunner(String name, String surname){
        Runner r = new Runner(pettorale, name, surname);
        runners.put(pettorale, r);
        pettorale++;
    	return pettorale-1;
    }
    
    public Runner getRunner(int bibNumber){
        return runners.get(bibNumber);
    }
    
    public Collection<Runner> getRunner(String surname){
        return runners.values().stream()
        		.filter(r -> r.getSurname().equals(surname))
        		.collect(Collectors.toList());
    }
    
    public List<Runner> getRunners(){
        return runners.values().stream()
        		.collect(Collectors.toList());
    }

    public List<Runner> getRunnersByName(){
        return runners.values().stream()
        		.sorted(Comparator.comparing(Runner::getSurname)
        				.thenComparing(Runner::getName))
        		.collect(Collectors.toList());
    }
    
    public void addLocation(String location){
        Location l = new Location(location, orderNum);
        locations.put(location, l);
        orderNum++;
    }
//    public void addLocation(String name, double lat, double lon, double elevation){
//        
//    }

    public Location getLocation(String location){
        return locations.get(location);
    }

    public List<Location> getPath(){
        return locations.values().stream()
        		.sorted(Comparator.comparing(Location::getOrderNum))
        		.collect(Collectors.toList());
    }
    
    public void newDelegate(String name, String surname, String id){
        Delegate d = new Delegate(name, surname, id);
        delegates.put(id, d);
    }

    public List<String> getDelegates(){
        return delegates.values().stream()
        		.sorted(Comparator.comparing(Delegate::getSurname)
        				.thenComparing(Delegate::getName))
        		.map(Delegate::toString)
        		.collect(Collectors.toList());
    }
    

    public void assignDelegate(String location, String delegate) throws TrailException {
    	if(!locations.containsKey(location)) throw new TrailException();
    	if(!delegates.containsKey(delegate)) throw new TrailException();
    	Location l = locations.get(location);
    	Delegate d = delegates.get(delegate);
    	l.addDelegate(delegate, d);
    	d.addLocation(l);
    }
 
    public List<String> getDelegates(String location){
        Location l = locations.get(location);
        return l.delegates.values().stream()
        		.sorted(Comparator.comparing(Delegate::getSurname)
        				.thenComparing(Comparator.comparing(Delegate::getName)))
        		.map(Delegate::toString)
        		.collect(Collectors.toList());
    }
    
    public long recordPassage(String delegate, String location, int bibNumber) throws TrailException {
        if(!runners.containsKey(bibNumber)) throw new TrailException();
        if(!locations.containsKey(location)) throw new TrailException();
        if(!delegates.containsKey(delegate)) throw new TrailException();
        Delegate d = delegates.get(delegate);
        Location l = locations.get(location);
        Runner r = runners.get(bibNumber);
        long oraPass = System.currentTimeMillis(); 
        l.addPassaggio(r, oraPass);
        l.addPassaggioPerTempo(oraPass, r);
    	return oraPass;
    }
    
    public long getPassTime(String position, int bibNumber) throws TrailException {
    	if(!runners.containsKey(bibNumber)) throw new TrailException();
        if(!locations.containsKey(position)) throw new TrailException();
    	Location l = locations.get(position);
        if(l.passaggio.containsKey(runners.get(bibNumber)))
        {
        	return l.passaggio.get(runners.get(bibNumber));
        }
        else return -1;
    }
    
    public List<Runner> getRanking(String location){
        Location l = locations.get(location);
    	return l.passaggioPerTempo.values().stream()
    			.collect(Collectors.toList());
    }

    public List<Runner> getRanking(){
        List<Location> locationList = new LinkedList<>(locations.values());
        Collections.sort(locationList);
        //System.out.println("Da mo cond");
        List<Runner> ris = new LinkedList<>();
        for(int i=locationList.size()-1; i>=0; i--)
        {
        	//System.out.println(locationList.get(i).getName());
        	Location l = locationList.get(i);
        	List<Runner> runnersList = new LinkedList<>(l.passaggioPerTempo.values());
        	for(int j=0; j<runnersList.size(); j++)
        	{
        		Runner r = runnersList.get(j);
        		ris.add(r);
        	}
        }
        //System.out.println("ciao");
        //System.out.println(ris.toString());
    	return ris;
    }
}
