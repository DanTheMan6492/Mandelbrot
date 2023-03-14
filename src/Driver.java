import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Driver extends JPanel implements ActionListener {
	/* Attributes a.k.a. Instance Variables */
	int w = 800, h = 800;
	static final int MAXITERS = 200;
	Timer t;
	
	//{pos, r, g, b}
	static int[][] CPfG = {
		{ 0, 0, 7, 100},
		{ 16, 32, 107, 203},
		{ 42, 237, 255, 255},
		{ 64, 255, 170, 0},
		{ 85, 0, 2, 0}
	};
	
	static int[][] MBpallet = {
	        { 66,  30,  15},
	        { 25,   7,  26},    
	        {  9,   1,  47},    
	        {  4,   4,  73},    
	        {  0,   7, 100},    
	        { 12,  44, 138},    
	        { 24,  82, 177},    
	        { 57, 125, 209},    
	        {134, 181, 229},    
	        {211, 236, 248},    
	        {241, 233, 191},    
	        {248, 201,  95},
	        {255, 170,   0},
	        {204, 128,   0},
	        {153,  87,   0},
	        {106,  52,   3}
	    };
	
	
	
	double hue = 0;
	public void paint(Graphics g) {
		super.paintComponent(g);
		
		basedMandelBrot(g);
		
		
	}
	
	public void basedMandelBrot(Graphics g) {
		int[] origin = {w/2, h/2};
		for(int x = -w/2; x < w/2; x++) {
			for(int y = -w/2; y < w/2; y++) {
				int t = checkValidMB(0, 0, 4.0*x/w, 4.0*y/w, 0);
				if(t == 0) g.setColor(Color.black);
				else {
					int[] rgb = pickcolorMB(t);
					g.setColor(new Color(rgb[0], rgb[1], rgb[2]));
				}
				g.fillRect((int)(x+w/2), (int)(y+h/2), 1, 1);
			}
		}
	}
	
	public int[] pickcolorMB(int iter) {
		int[] rgb = new int[3];
		double pos = 100 * iter / 256.0;
		int i = 0;
		while(CPfG[i][0] <= pos && i != 4) {i++;}
		if(i != 4) {
			double p = (CPfG[i][0]-pos)/(CPfG[i][0] - CPfG[i-1][0]);
			rgb[0] = (int) (CPfG[i-1][1] * (1-p) + CPfG[i][1]*p);
			rgb[1] = (int) (CPfG[i-1][2] * (1-p) + CPfG[i][3]*p);
			rgb[2] = (int) (CPfG[i-1][2] * (1-p) + CPfG[i][3]*p);
			return rgb;
		}
		double p = (1-pos)/(1 - CPfG[4][0]);
		rgb[0] = (int) (CPfG[i-1][1] * (1-p) + CPfG[i][1]*p);
		rgb[1] = (int) (CPfG[i-1][2] * (1-p) + CPfG[i][3]*p);
		rgb[2] = (int) (CPfG[i-1][2] * (1-p) + CPfG[i][3]*p);
		return rgb;
	}
	
	public static int checkValidMB(double a, double b, double c1, double c2, int iter) {
		if(iter == MAXITERS) return 0;
		if(Math.sqrt(a*a + b*b) > 2) return MAXITERS-iter;
		double square = a*a - b*b;
		double coeff = 2 * a * b;
		return checkValidMB(square+c1, coeff+c2, c1, c2, iter+1);
	}
	
	
	
	public void update() {
	}// end of update method - put code above for any updates on variable
	// ==================code above ===========================
	@Override
	public void actionPerformed(ActionEvent arg0) {
		update();
		repaint();
	}
	public static void main(String[] arg) {
		Driver d = new Driver();
	}
	
	public Driver() {
		JFrame f = new JFrame();
		f.setTitle("Pong");
		f.setSize(w, h);
		f.setBackground(Color.BLACK);
		f.setResizable(false);
		f.add(this);
		t = new Timer(7, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
}

