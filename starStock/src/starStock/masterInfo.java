package starStock;

import java.util.*;

public class masterInfo {

	public List<Double> currentMaster;
	public List<Double> openMaster;
	public List<Double> closeMaster;
	public List<Double> fluxMaster;
	public List<Double> potMaster;
	public List<ArrayList<Double>> masterMaster;
	public double currentMax;
	public double openMax;
	public double closeMax;
	public double fluxMax;
	public double potMax;
	public double currentMin;
	public double openMin;
	public double closeMin;
	public double fluxMin;
	public double potMin;
	double max;
	double min;



	public masterInfo() {
		currentMaster = new ArrayList<Double>();
		openMaster = new ArrayList<Double>();
		closeMaster = new ArrayList<Double>();
		fluxMaster = new ArrayList<Double>();
		potMaster = new ArrayList<Double>();
		masterMaster = new ArrayList<>();
	}

	public void populateRel() {
		setRel(currentMaster,0);
		setRel(openMaster,1);
		setRel(closeMaster,2);
		setRel(fluxMaster,3);
		setRel(potMaster,4);
	}

	public void setRel(List<Double> d, int y) {
		max = findMax(d);
		if(y == 0) {
			currentMax = max;
			}
		if(y == 1) {
			openMax = max;
		}
		if(y == 2) {
			closeMax = max;
		}
		if(y == 3) {
			fluxMax = max;
		}
		if(y == 4) {
			potMax = max;
		}


	}



	public Double findMax(List<Double> list) 
	{ 
		if (list == null || list.size() == 0) { 
			return Double.MIN_VALUE; 
		} 

		List<Double> sortedlist = new ArrayList<>(list);
		Collections.sort(sortedlist); 
		return sortedlist.get(sortedlist.size() - 1); 
	} 



}

