import ij.*;
import ij.process.*;
import ij.gui.*;
import ij.io.Opener;

import java.awt.*;
import ij.plugin.filter.*;
import Bib.Biblioteca;
import Bib.RecTangle;
 

public class lista4_plugin implements PlugInFilter {
	ImagePlus imp;

	public int setup(String arg, ImagePlus imp) {
		this.imp = imp;
		return DOES_ALL;
	}
	
	// 6dB = 94,5
	// 18,32,72,50
	// 72 - 18 = 54
	// 50 - 32 = 18
	
	public void run(ImageProcessor ip) {
		// Imagem com Ruido
		FloatProcessor ipf = (FloatProcessor)ip.convertToFloat();
		int w = ipf.getWidth();int h = ipf.getHeight();		
		
		// Imagem Real
		// Leitor de Imagem
		ImagePlus imp2 = IJ.openImage("ct.dcm");
		ImageProcessor ip2 = imp2.getProcessor();
		FloatProcessor ipf2 = (FloatProcessor)ip2.convertToFloat();
		// Determinando quanto de desvio colocar para 6dB
		double desvmed2[] = new double[4];
		desvmed2 = Biblioteca.DesviMed(ipf2);
		IJ.log("Desvio padrao da imagem "  + desvmed2[0]);
		double DesvE = Biblioteca.Db2SigmaError(desvmed2[0], 6);
		IJ.log("Desvio padrao do erro para " + 6 + " dB " + DesvE);
		//imp2.show();						
		// Cáculos
		double Emax = Biblioteca.EMax(ipf2, ipf);
		IJ.log("O valor de EMax I1I0 é de " + Emax);
		double NRMSE = Biblioteca.NRMSE(ipf2, ipf);
		IJ.log("O valor de NRMSE I1I0 é de " + NRMSE);
		double SSIM = Biblioteca.SSIM(ipf2, ipf);
		IJ.log("O valor de SSIM I1I0 é de " + SSIM);
		
		// Selecionando regiao homogenea
		RecTangle Slice = new RecTangle(18,32,80,30,ipf);
		double desvmed[] = new double[4];
		desvmed = Biblioteca.DesviMed(Slice.ip);
		IJ.log("Desvio padrao da regiao homogenea: " + desvmed[0]);
		ImagePlus imp = new ImagePlus("Slice", Slice.ip); 
		imp.show();
		
		FloatProcessor ipf3 = new FloatProcessor(w,h);
		try {
			ipf3 = Biblioteca.Filtro_Wier(ipf, desvmed[0], 3);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("Dimensao deve ser impar");
		}
		ImagePlus imp3 = new ImagePlus("Filtrado", ipf3); 
		imp3.show();
		
		// Cáculos
		Emax = Biblioteca.EMax(ipf3, ipf2);
		IJ.log("O valor de EMax NovoI0 é de " + Emax);
		NRMSE = Biblioteca.NRMSE(ipf3, ipf2);
		IJ.log("O valor de NRMSE NovoI0 é de " + NRMSE);
		SSIM = Biblioteca.SSIM(ipf3, ipf2);
		IJ.log("O valor de SSIM NovoI0 é de " + SSIM);
		
		
		
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
