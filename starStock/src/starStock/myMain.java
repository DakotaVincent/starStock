package starStock;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;


public class myMain extends JFrame{

	stock stock1 = new stock();
	stock stock2 = new stock();
	stock stock3 = new stock();
	stock stock4 = new stock();
	stock stock5 = new stock(); 
	stockVis vis;
	static List<stock> myStocks;
	double low = 0;
	double high = 0;
	String response = "";
	String response2 = "";
	String response3 = "";
	double invest = 1;
	double alternate;
	boolean myAlternate = false;
	masterInfo myInfo;



	public myMain() {
		vis = new stockVis();
		myInfo = new masterInfo();
		myStocks = new ArrayList<stock>();
		setContentPane(vis);
		setSize(1100,1100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("STARSTOCK");
		setJMenuBar(setupMenu());
		setVisible(true);
		getData();

	}



	public JMenuBar setupMenu() {
		JMenuBar menu = new JMenuBar();
		JMenu item1 = new JMenu("File");
		JMenu item2 = new JMenu("Color");
		JMenuItem item3 = new JMenuItem("Add Stock");
		JMenuItem item4 = new JMenuItem("Reset Values");
		JMenuItem item5 = new JMenuItem("Invest Potential");
		JMenuItem item6 = new JMenuItem("Enter Alternate Value");
		JMenuItem item7 = new JMenuItem("Reset Alternate Value");

		myStocks.add(stock1);
		myStocks.add(stock2);
		myStocks.add(stock3);
		myStocks.add(stock4);
		myStocks.add(stock5);

		stock1.url = "https://www.investing.com/equities/boeing-co";
		stock2.url = "https://www.investing.com/equities/microsoft-corp";
		stock3.url = "https://www.investing.com/equities/apple-computer-inc";
		stock4.url = "https://www.investing.com/equities/ibm";
		stock5.url = "https://www.investing.com/equities/twitter-inc";

		stock1.Color = Color.red;
		stock2.Color = Color.orange;
		stock3.Color = Color.magenta;
		stock4.Color = Color.green;
		stock5.Color = Color.blue;



		item1.add(item3);
		item1.add(item4);
		item1.add(item5);
		item1.add(item6);
		//item1.add(item7);

		item3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				response = JOptionPane.showInputDialog(null, "Please enter stock's URL from Investing.com: ","New Stock",  JOptionPane.QUESTION_MESSAGE);
				if(response != null){
					if(!response.isEmpty()){
						myStocks.add(new stock());
						getData();				    
					}
				}
			}

		});item4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				invest = 1;
				myAlternate = false;
				getData();
			}

		});item5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				response2 = JOptionPane.showInputDialog(null, "Please enter the amount you would like to invest: ","Invest Potential",  JOptionPane.QUESTION_MESSAGE);
				if(response2 != null){
					if(!response2.isEmpty()){
						invest = Double.parseDouble(response2);
						System.out.println(invest);
						getData();
					}
				}
			}

		});item6.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				response3 = JOptionPane.showInputDialog(null, "Please enter an alternate stock value for today: ","Alternate Value",  JOptionPane.QUESTION_MESSAGE);
				if(response3 != null){
					if(!response3.isEmpty()){
						alternate = Double.parseDouble(response3);
						System.out.println(alternate);
						myAlternate = true;
						getData();
					}
				}
			}

		});item7.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				myAlternate = false;
				getData();
			}
		});

		menu.add(item1);
		return menu;
	}



	public double convertString (String q) {
		String tempPrice1 = q.replace(",", "");
		double price = Double.parseDouble(tempPrice1);
		return price;
	}



	public void separator(String r) {
		low = 0;
		high = 0;
		String split[] = r.split(" - ",2);
		for (int i = 0; i <2;i++) {
			if(i == 0) {
				low =(convertString(split[0]));
			}
			else {
				high = (convertString(split[1]));
			}
		}
	}

	public void getData() {
		try {
			for(stock a : myStocks) {
				

				if(a.url.equals("")) {
					a.url = response;
					Random rand = new Random();
					int redValue = rand.nextInt(255);
					int greenValue = rand.nextInt(255);
					int blueValue = rand.nextInt(255);
					a.Color= new Color(redValue, greenValue, blueValue);
				}

				double flux = 0;

				final Document document = Jsoup.connect(a.url).get();
				//System.out.println(document);
				a.stockName = document.select("div.instrumentHead > .relativeAttr.float_lang_base_1").text();
				a.currentPrice = convertString(document.select("div.instrument.overViewBox > #quotes_summary_current_data > .left > .inlineblock > .inlineblock.bold.top > #last_last").text());
				Elements row = document.select("div.clear.overviewDataTable.overviewDataTableWithTooltip");
				a.prevClose = convertString(row.select(".inlineblock.first:nth-of-type(1) > .bold.float_lang_base_2").text());
				a.open = convertString(row.select(".inlineblock.first:nth-of-type(4) > .bold.float_lang_base_2").text());
				separator(row.select(".inlineblock:nth-of-type(2) > .bold.float_lang_base_2").text());
				a.rangeTodayLow = low;
				a.rangeTodayHigh = high;
				separator(row.select(".inlineblock:nth-of-type(5) > .bold.float_lang_base_2").text());
				a.range52low = low;
				a.range52high = high;
				//Scans the Internet for the values and stores them into the stock objects

				flux = a.currentPrice - a.prevClose;
				if(flux < 0) {
					a.negative = true;
					flux = flux * -1;
				}
				DecimalFormat f = new DecimalFormat("##.00");
				String tempFlux = f.format(flux);
				a.fluctuation = Double.parseDouble(tempFlux);
				double averageToday = (a.rangeTodayHigh + a.rangeTodayLow)/2;
				double average52 = (a.range52high + a.range52low)/2;
				//Calculates the fluctuations for both the day and the 52 week summary and averages them

				if (myAlternate == false) {
					if (invest != 1) {
						String tempPot = f.format(((invest/averageToday) * average52)- invest);
						a.potential = Double.parseDouble(tempPot);
					}
					
					else {
						a.potential = 1;
					}
				}
				if (myAlternate == true) {
					if (invest != 1) {
						String tempPot = f.format(((invest/averageToday) * alternate)- invest);
						a.potential = Double.parseDouble(tempPot);
					}
					else {
						a.potential = 1;
					}
				}
				//shows the potential return going off of market averages if you were to invest a certain amount of money

				//				System.out.println(a.stockName);
				//				System.out.println(a.currentPrice);
				//				System.out.println(a.prevClose);
				//				System.out.println(a.open);
				//				System.out.println(a.potential);
				myInfo.currentMaster.add(a.currentPrice);
				myInfo.closeMaster.add(a.prevClose);
				myInfo.openMaster.add(a.open);
				myInfo.potMaster.add(a.potential);
				myInfo.fluxMaster.add(a.fluctuation);

				//				if(a.negative == true) {
				//					System.out.println(a.fluctuation * -1);
				//				}
				//				else {
				//					System.out.println(a.fluctuation);
				//				}
				System.out.println("done");
				a.addToList();
			}
			myInfo.populateRel();

			for( stock b : myStocks) {
				b.currentMax = myInfo.currentMax;
				b.closeMax = myInfo.closeMax;
				b.openMax = myInfo.openMax;
				if(invest != 1) {
					b.potMax = myInfo.potMax;
				}
				if (invest == 1){
					b.potMax = 1;
				}
				b.fluxMax = myInfo.fluxMax;
				b.getRels();
				b.ready = true;
			}
			vis.start = true;
			repaint();
		}

		catch(Exception ex) {
			ex.printStackTrace();
		}

	}



	public static void main(String[] args){
		new myMain();

	}

}
