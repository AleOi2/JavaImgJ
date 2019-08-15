import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;

// usar long ou float quando for trabalhar com acumulador
// usar float 
public class terceiro_plugin implements PlugInFilter {

	@Override
	public void run(ImageProcessor ip) {
		int w = ip.getWidth();
		int h = ip.getHeight();
		
		float soma, media, soma2, desvpad;
		int x = 0,y = 0;
		float minimo, maximo;
		minimo = 999999;maximo = 0;
		soma = 0;soma2 = 0;media = 0; desvpad = 0;
		IJ.log("Valor do IP " + ip.getf(x, y));	
		
		for(y = 0; y < h; y++) {
			for (x = 0; x < w; x++) {
				soma = soma + ip.getf(x, y);
				soma2 = soma2 + ip.getf(x, y) * ip.getf(x, y);
				if (ip.getf(x, y) < minimo) {
					minimo = ip.getf(x, y);
				}	
				if (ip.getf(x, y) > maximo) {
					maximo = ip.getf(x, y);
				}		
			}
		}
		media = soma/(x * y);
		desvpad = soma2/(x * y);
		desvpad = desvpad - (x * y) * media * media/(x * y - 1);
		IJ.log("A media vale " + media);	
		IJ.log("O desvio vale:  " + desvpad);
		IJ.log("O valor mínimo é " + minimo);	
		IJ.log("O valor máximo é:  " + maximo);
	
	}
	
	@Override
	public int setup(String arg0, ImagePlus ip) {
		// TODO Auto-generated method stub
		return DOES_ALL;
	}
	


}