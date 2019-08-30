import ij.*;
import ij.process.*;
import ij.gui.*;
import ij.io.Opener;

import java.awt.*;
import ij.plugin.filter.*;
import Bib.Biblioteca;

public class lista3_plugin implements PlugInFilter {
	ImagePlus imp;

	public int setup(String arg, ImagePlus imp) {
		this.imp = imp;
		return DOES_ALL;
	}

	public void run(ImageProcessor ip) {
		FloatProcessor ipf = (FloatProcessor)ip.convertToFloat();
		int w = ipf.getWidth();
		int h = ipf.getHeight();		
		FloatProcessor Grad = new FloatProcessor(w, h); 
		FloatProcessor Lapl = new FloatProcessor(w, h); 
		
		Grad = Biblioteca.Gradiente(ipf);
		ImagePlus grad = new ImagePlus("Gradiente", Grad); 
		grad.show();
		
		/*double[][] pesos_grad = {{-1, 0, 1}};
		// Cáculos
		try {
			Grad = Biblioteca.Convolucao(ipf, pesos_grad);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("Divisao deve ser impar");
		}*/
		double[][] pesos_lap = {{0, 1, 0}, {1, -4, 1}, {0, 1, 0}};
		try {
			Lapl = Biblioteca.Convolucao(ipf, pesos_lap);
			ImagePlus imp2 = new ImagePlus("Laplaciano", Lapl);
			imp2.show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("Divisao deve ser impar");
		}
		 
	}

}
