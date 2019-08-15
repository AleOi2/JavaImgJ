import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.filter.*;

public class segundo_plugin implements PlugInFilter {
	ImagePlus imp;

	public int setup(String arg, ImagePlus imp) {
		this.imp = imp;
		return DOES_ALL;
	}

	public void run(ImageProcessor ip) {
		// criando uma nova imagem p/ resultados ou manipulacao ...
		FloatProcessor ipf2=(FloatProcessor)ip.convertToFloat();
		ImagePlus impf2=new ImagePlus("exemplo float", ipf2);
		int w= ipf2.getWidth();
		int h= ipf2.getHeight();
		IJ.log ("Imagem float com " + h + " linhas" + " e " + w +" colunas");
		// loop para obter media e dp
		float v, soma=0, soma2=0;
		for (int y=0; y< h; y++)
		for (int x=0; x<w; x++)
		{v=ipf2.getf(x,y); soma = soma + v; soma2 = soma2 + v*v; }
		int N=w*h; // numero de pixels 
		double media=soma/N; 
		double dp=soma2/(N-1) - media*media*N/(N-1);
		dp=Math.sqrt(dp);
		// mostrando valores ...
		IJ.log ( " media= " + media + " e dp=" + dp);
		impf2.show();
	}
}

