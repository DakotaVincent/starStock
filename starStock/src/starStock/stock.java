package starStock;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class stock {

	public Color Color;
	String url = "";
	String stockName = "";
	double prevClose;
	double currentPrice;
	double open;
	double rangeTodayLow;
	double rangeTodayHigh;
	double range52low;
	double range52high;
	double fluctuation;
	public boolean negative = false;
	double potential = 1; 
	public double[] stockInfo;

	public void addToList() {
		stockInfo = new double[] {currentPrice,open,prevClose,fluctuation,potential};
	}

	public double currentMax;
	public double openMax;
	public double closeMax;
	public double fluxMax;
	public double potMax;

	public boolean ready = false;

	public void getRels() {
		for(int i = 0; i < stockInfo.length; i++) {
			if(i == 0) {
				stockInfo[0] = currentPrice/currentMax;
			}
			if(i == 1) {
				stockInfo[1]= open/openMax;

			}
			if(i == 2) {
				stockInfo[2] = prevClose/closeMax;

			}
			if(i == 3) {
				stockInfo[3] = fluctuation/fluxMax;

			}
			if(i == 4) {
				stockInfo[4] = potential/potMax;

			}

		}
	}


}
