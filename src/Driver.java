import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Driver extends JPanel implements MouseListener, ActionListener {
	
	static final int WIDTH = 600, HEIGHT = 600;
	static final int MAXITERS = 60;
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
	
	//{x, y, width, height} (The x and y are of the bottom left corner
	double[] BB = {-2.2, -1.5, 3, 3};
	
	double ppf = 0.95;
	
	public boolean pSelected = false;
	public double[] pZoom = new double[2];
	
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		
		basedMandelBrot(g);
		if(pSelected) update();
		
	}
	
	public void update() {
		BB[0] = (BB[0]-pZoom[0])*ppf+pZoom[0];
		BB[1] = (BB[1]-pZoom[1])*ppf+pZoom[1];
		BB[2] = BB[2]*ppf;
		BB[3] = BB[3]*ppf;
	}
	
	public void basedMandelBrot(Graphics g) {
		
		for(int x = 0; x < WIDTH; x++) {
			for(int y = 0; y < HEIGHT; y++) {
				double xc = (BB[2] * x / WIDTH) + BB[0];
				double yc = (BB[3] * y / WIDTH) + BB[1];
				int t = checkValidMB(0, 0, xc, yc, 0);
				if(t == 0) g.setColor(Color.black);
				else {
					int[] rgb = pickcolorMB(t);
					g.setColor(new Color(rgb[0], rgb[1], rgb[2]));
				}
				g.fillRect(x, y, 1, 1);
			}
		}
	}
	
	public int[] pickcolorMB(int iter) {
        int i = (MAXITERS-iter) % 16;
        if(i < 16) return MBpallet[i];
        return new int[] {0, 0, 0};
    }
	
	public static int checkValidMB(double a, double b, double c1, double c2, int iter) {
		if(iter == MAXITERS) return 0;
		if(Math.sqrt(a*a + b*b) > 2) return MAXITERS-iter;
		double square = a*a - b*b;
		double coeff = 2 * a * b;
		return checkValidMB(square+c1, coeff+c2, c1, c2, iter+1);
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		pSelected = true;
		pZoom[0] = (BB[2] * (e.getX()- 7) / WIDTH) + BB[0];
		pZoom[1] = (BB[3] * (e.getY()-31) / WIDTH) + BB[1];
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] arg) {
		new Driver();
	}
	
	public Driver() {
		JFrame f = new JFrame();
		f.setTitle("MandelBrot");
		f.setSize(WIDTH, HEIGHT);
		f.setBackground(Color.BLACK);
		f.setResizable(false);
		f.add(this);
		f.addMouseListener(this);
		t = new Timer(7, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
}

