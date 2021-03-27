package starStock;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;






public class stockVis extends JPanel implements MouseListener, MouseMotionListener {

	int w,h, wHalf, hHalf; 
	private Color transparentCyan;
	public boolean start = false;
	int counter = 5;
	List<HyrumPolyline> lines;
	private double mousex1;
	private double mousey1;
	private double mousex2;
	private double mousey2;



	public stockVis() {
		super();
		addMouseMotionListener(this);
		addMouseListener(this);
		transparentCyan = new Color(0,255,255,50);
		lines = new ArrayList<HyrumPolyline>();

	}

	public void drawLines(Graphics2D g, double[] theStock, boolean neg,Color c, String name, Double current, Double Open, Double Close, Double Flux, Double Potential ){
		double chunks = 5;
		double degrees = 0;
		double x_p[] = new double[5]; 
		double y_p[] = new double[5];
		double centerx = wHalf*2;
		double centery = hHalf*2;
		double radiusX = wHalf;
		double radiusY = hHalf;
		double myVal = 0;
		double myValx = 0;
		double myValy = 0;
		HyrumPolyline line = new HyrumPolyline();


		g.setColor(c);
		g.fillRect(30, counter-13, 30, 30);

		g.setColor(Color.white);
		Font font = new Font("SansSerif", Font.BOLD, 15);
		g.setFont(font);
		g.drawString(name, 80, counter);
		if(neg == false){
		g.drawString("Stock Info- Current Price: $" + current + ", Opening Price: $" + Open + ", Previous Close: $" + Close + ", Market Fluctuation: $ +" + Flux + ", Potential Return: $" + Potential , 80, 15+counter);}
		if( neg == true) {
			g.drawString("Stock Info- Current Price: $" + current + ", Opening Price: $" + Open + ", Previous Close: $" + Close + ", Market Fluctuation: $ -" + Flux + ", Potential Return: $" + Potential , 80, 15+counter);}
    
		counter+= 40;


		for (int i = 0; i < (chunks+1); i++)
		{
			lines.clear();
			g.setColor(c);


			if( i < 5) {
				degrees = i * (360 / chunks); 
				double radian = (degrees * (Math.PI / 180));  
				x_p[i] = radiusX * Math.cos(radian);         
				y_p[i] = radiusY * Math.sin(radian);


				myVal = theStock[i];
				myValx = myVal * x_p[i];
				myValy = myVal * y_p[i];

				g.setColor(Color.WHITE);

				g.drawLine((int)centerx,(int)centery, (int) (centerx  + x_p[i]), (int) (centery+ y_p[i]));


				g.setColor(c);

				if ( i == 0) {
					g.setColor(c);
					g.fillOval( (int) Math.round(centerx + myValx -5), (int) Math.round(centery+ myValy-5), 10, 10);
					line.addPoint(centerx + myValx, centery+ myValy);
					g.setColor(Color.white);
					g.drawString(" Current Stock Price", (int) (centerx  + x_p[i] + 10), (int) (centery+ y_p[i] + 5));


				}
				if ( i == 1) {
					g.fillOval( (int) Math.round(centerx + myValx -5), (int) Math.round(centery+ myValy-5), 10, 10);
					line.addPoint(centerx + myValx, centery+ myValy);
					g.setColor(Color.white);
					g.drawString(" Stock's Opening Price", (int) (centerx  + x_p[i] + 10), (int) (centery+ y_p[i] + 15));


				}
				if ( i == 2) {
					g.fillOval( (int) Math.round(centerx + myValx -5), (int) Math.round(centery+ myValy-5), 10, 10);
					line.addPoint(centerx + myValx, centery+ myValy);
					g.setColor(Color.white);
					g.drawString("Stock's Closing Price", (int) (centerx  + x_p[i] -170), (int) (centery+ y_p[i] + 5));

				}
				if ( i == 3) {
					if( neg == false) {
						g.fillOval( (int) Math.round(centerx + myValx -5), (int) Math.round(centery+ myValy-5), 10, 10);
						line.addPoint(centerx + myValx, centery+ myValy);
						g.setColor(Color.white);
						g.drawString("Market Change", (int) (centerx  + x_p[i] -140), (int) (centery+ y_p[i] + 5));

					}
					if( neg == true) {
						g.fillRect( (int) Math.round(centerx + myValx -5), (int) Math.round(centery+ myValy-5), 10, 10);
						line.addPoint(centerx + myValx, centery+ myValy);

					}
				}
				if ( i == 4) {
					g.fillOval( (int) Math.round(centerx + myValx -5), (int) Math.round(centery+ myValy-5), 10, 10);
					line.addPoint(centerx + myValx, centery+ myValy);
					g.setColor(Color.white);
					g.drawString(" Stock's Potential Return", (int) (centerx  + x_p[i] + 10), (int) (centery+ y_p[i] -5 ));

				}


			}
			if (i == 5) {
				degrees = 0 * (360 / chunks); 
				double radian2 = (degrees * (Math.PI / 180)); 
				x_p[0] = radiusX * Math.cos(radian2);         
				y_p[0] = radiusY * Math.sin(radian2);
				myVal = theStock[0];
				double endPointX = myVal * x_p[0];
				double endPointY = myVal * y_p[0];
				g.fillOval( (int) Math.round(centerx + endPointX -5), (int) Math.round(centery+ endPointY-5), 10, 10);
				line.addPoint(centerx + endPointX, centery+ endPointY);
			}
			lines.add(line);

		}

		drawMe(g,c);
	}




	@Override
	public void paintComponent(Graphics g1) {
		Graphics2D g = (Graphics2D)g1;
		w = getWidth();
		h = getHeight();
		int d = w/2;
		int f = h/2;
		wHalf = (w-d)/2;
		hHalf = (h-f)/2;

		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, w, h);
		g.setColor(Color.CYAN);
		g.drawOval(wHalf, hHalf , d, f);
		g.setColor(transparentCyan);
		g.fillOval(wHalf, hHalf , d, f);
		if(start == true) {
			counter = 50;
			for(stock s : myMain.myStocks) {

				drawLines(g,s.stockInfo,s.negative,s.Color, s.stockName,s.currentPrice,s.open,s.prevClose,s.fluctuation,s.potential);

			}
		}


	}

	public void drawMe( Graphics2D g, Color lineColor) {

		for(HyrumPolyline p : lines) {
			p.draw(g, lineColor);


		}
	}



	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {


	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
