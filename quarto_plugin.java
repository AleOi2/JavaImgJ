import java.util.Random;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;

class quarto_plugin implements PlugInFilter{
	ImagePlus imp;

	
	public double[] DesvPad(double w, double h, double soma , double soma2) {
		double media, desvpad;
		double retorno[] = new double[2];
		media = soma/(w * h);
		desvpad = soma2/(w * h - 1);
		desvpad = desvpad - (w * h) * media * media/(w * h - 1);
		retorno[0] = media;
		retorno[1] = desvpad;
		return retorno;
	}
	
	@Override
	public void run(ImageProcessor img) {
		// TODO Auto-generated method stub
		FloatProcessor ipf2 = (FloatProcessor)img.convertToFloat();
		
		Random r = new Random();
		double g, h, w; 
		double sumg, sumr, sumf;
		double sum2g, sum2r, sum2f;

		double varg[] = new double[2];
		double varf[] = new double[2];
		double varr[] = new double[2];
		
		sumg = 0; sumr = 0; sumf = 0; 
		sum2g = 0; sum2r = 0; sum2f = 0; 

		h = img.getHeight();
		w = img.getWidth();
		
		for(int index = 1; index <= w; index++) {
			for(int index2 = 1; index2 <= h; index2++) {
				g = ipf2.getf(index2, index) + Math.sqrt(10) * r.nextGaussian();
				sumg = sumg + g;
				sum2g = sum2g + g * g;
				
				sumf = sumf + ipf2.getf(index2, index);
				sum2f = sum2f + ipf2.getf(index2, index) * ipf2.getf(index2, index);
				
				sumr = sumr + Math.sqrt(10) * r.nextGaussian();				
				sum2r = sum2r + Math.sqrt(10) * r.nextGaussian() * r.nextGaussian();
				
			}
		}
		varg = DesvPad(w, h, sumg, sum2g);
		varr = DesvPad(w, h, sumr, sum2r);
		varf = DesvPad(w, h, sumf, sumf);

		IJ.log("A média g vale:  " + varg[0]);
		IJ.log("O desvio g vale:  " + Math.sqrt(varg[1]));
		IJ.log("A média r vale:  " + varr[0]);
		IJ.log("O desvio r vale:  " + Math.sqrt(varr[1]));
		IJ.log("A média f vale:  " + varf[0]);
		IJ.log("O desvio f vale:  " + Math.sqrt(varf[1]));
		
	}

	@Override
	public int setup(String arg0, ImagePlus imp) {		
		// TODO Auto-generated method stub
		this.imp = imp;
		return DOES_ALL;
	}
	
	
}
