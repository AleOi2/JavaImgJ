import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ColorProcessor;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;

// usar long ou float quando for trabalhar com acumulador
// usar float 
public class lista1_plugin implements PlugInFilter {

	@Override
	public void run(ImageProcessor ip) {
		FloatProcessor ipf2 = (FloatProcessor)ip.convertToFloat();
		ImagePlus impf2 = new ImagePlus("exemplo float", ipf2);
		
		int w = ipf2.getWidth();
		int h = ipf2.getHeight();
		
		float soma, media, soma2, desvpad;
		int x = 0,y = 0;
		float minimo, maximo;
		minimo = 999999;maximo = 0;
		soma = 0;soma2 = 0;media = 0; desvpad = 0;
		IJ.log("Valor do IP " + ipf2.getf(x, y));	
		
		for(y = 0; y < h; y++) {
			for (x = 0; x < w; x++) {
				soma = soma + ipf2.getf(x, y);
				soma2 = soma2 + ipf2.getf(x, y) * ipf2.getf(x, y);
				if (ipf2.getf(x, y) < minimo) {
					minimo = ipf2.getf(x, y);
				}
				if (ipf2.getf(x, y) > maximo) {
					maximo = ipf2.getf(x, y);
				}		
			}
	}
	// Valor da media		
	media = soma/(w * h);
	desvpad = soma2/(w * h - 1);
	desvpad = desvpad - (w * h) * media * media/(w * h - 1);
	IJ.log("A media vale " + media);	
	IJ.log("O desvio vale:  " + Math.sqrt(desvpad));
	IJ.log("O valor mínimo é " + minimo);	
	IJ.log("O valor máximo é:  " + maximo);
	impf2.show();

}

	@Override
	public int setup(String arg0, ImagePlus ip) {
		// TODO Auto-generated method stub
		return DOES_ALL;
	}



}