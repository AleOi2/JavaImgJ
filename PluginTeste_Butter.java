import ij.*;
import ij.process.*;
import ij.gui.*;
import ij.io.Opener;

import java.awt.*;
import ij.plugin.filter.*;
import Bib.Biblioteca;
import Bib.RecTangle;
 

public class PluginTeste_Butter implements PlugInFilter {
	ImagePlus imp;

	public int setup(String arg, ImagePlus imp) {
		this.imp = imp;
		return DOES_ALL;
	}
	
	public void run(ImageProcessor ip) {
		// Imagem com Ruido
		FloatProcessor ipf = (FloatProcessor)ip.convertToFloat();
		int w = ipf.getWidth();int h = ipf.getHeight();		
		int local = 2;double FreqCorte = 100; double n = 1;
	    FloatProcessor Filtro = Biblioteca.FiltroButter(ipf, FreqCorte, n, local);
		ImagePlus imp = new ImagePlus("Slice Roi", Filtro); 
		imp.show();
		
		/* Segundo jeito de leitor de imagem
		 * Rectangle roi_s = ip.getRoi();
		FloatProcessor ip3 = new FloatProcessor(roi_s.width, roi_s.height);
		for (int index = roi_s.x;index < roi_s.x + roi_s.width;index++) {
			for(int index2 = roi_s.y;index2 < roi_s.y + roi_s.height;index2++) {
				ip3.setf(index - roi_s.x, index2 - roi_s.y, 
						ip.getf(index, index2)) ;
			}
		}
		
		ImagePlus imp3 = new ImagePlus("Slice Roi", ip3); 
		imp3.show();*/
		 
	}

}
