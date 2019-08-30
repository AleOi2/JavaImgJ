import ij.*;
import ij.process.*;
import ij.gui.*;
import ij.io.Opener;

import java.awt.*;
import ij.plugin.filter.*;
import Bib.Biblioteca;

public class Teste_Convolucao implements PlugInFilter {
	ImagePlus imp;

	public int setup(String arg, ImagePlus imp) {
		this.imp = imp;
		return DOES_ALL;
	}

	public void run(ImageProcessor ip) {
		FloatProcessor ipf = (FloatProcessor)ip.convertToFloat();
		int w = ipf.getWidth();
		int h = ipf.getHeight();		
		FloatProcessor Convol = new FloatProcessor(w, h); 
		
		double[][] pesos = {{-1, 0, 1}};
		// Cáculos
		try {
			Convol = Biblioteca.Convolucao(ipf, pesos);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("Divisao deve ser impar");
		}
	}

}
