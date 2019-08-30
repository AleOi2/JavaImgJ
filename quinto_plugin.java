// Gera Relação Sinal Ruído

import java.util.Random;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;

class quinto_plugin implements PlugInFilter{

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
		int x1, x2, y1, y2;
		x1 = 62; x2 = 186;
		y1 = 14; y2 = 53;
		double Noisesum, Noisesum2 ;
		double[] NoiseDesvPad = new double[2];
		Noisesum = 0;Noisesum2 = 0;
		for(int Noiseindex = 1; Noiseindex <= x2 - x1; Noiseindex++) {
			for(int Noiseindex2 = 1; Noiseindex2 <= y2 - y1; Noiseindex2++) {
				Noisesum = Noisesum + ipf2.getf(Noiseindex2, Noiseindex);
				Noisesum2 = Noisesum2 + ipf2.getf(Noiseindex2, Noiseindex) * ipf2.getf(Noiseindex2, Noiseindex);								
			}
		}

		NoiseDesvPad = DesvPad(x2 - x1, y2 - y1, Noisesum, Noisesum2);

		double h,w;
		double sum, sum2;
		double[] DesviPad = new double[2];
		sum = 0;sum2 = 0;

		h = img.getHeight();
		w = img.getWidth();
		
		for(int index = 1; index <= w; index++) {
			for(int index2 = 1; index2 <= h; index2++) {
				sum = sum + ipf2.getf(index2, index);
				sum2 = sum2 + ipf2.getf(index2, index) * ipf2.getf(index2, index);
								
			}
		}
		DesviPad = DesvPad(x2 - x1, y2 - y1, Noisesum, Noisesum2);
		
	}

	@Override
	public int setup(String arg0, ImagePlus arg1) {
		// TODO Auto-generated method stub
		return DOES_ALL;
	}
	
	
	
}