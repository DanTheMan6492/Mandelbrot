
public class Gradient {
	public int[][] stops;
	public double[] lens;
	
	public Gradient(int[][] stops, double[] lens) {
		this.stops = stops;
		this.lens = lens;
	}
	
	/*
	 * Returns the RGB color value of the gradient at a specified point
	 */
	public int[] getColor(double pos) {
		double p = lens[0];
		int i = 0;
		while(pos < p) {
			i++;
			p+=lens[i];
		}
		
		
		
		return null;
	}
	
	public int[] hslToRgb(double h, double s, double l){
		double r, g, b;
		if(s == 0){
			r = g = b = l;
		}else{
			double q = l < 0.5 ? l * (1 + s) : l + s - l * s;
			double p = 2 * l - q;
			r = hue2rgb(p, q, h + 1.0/3);
			g = hue2rgb(p, q, h);
			b = hue2rgb(p, q, h - 1.0/3);
		}
		return new int[] {(int)(r * 255), (int)(g * 255), (int)(b * 255)};
	}
	
	double hue2rgb(double p, double q, double t){
		if(t < 0) t += 1;
		if(t > 1) t -= 1;
		if(t < 1.0/6) return p + (q - p) * 6 * t;
		if(t < 1.0/2) return q;
		if(t < 2.0/3) return p + (q - p) * (2.0/3 - t) * 6;
		return p;
	}
}
