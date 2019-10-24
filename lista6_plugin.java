import ij.process.*;
import ij.IJ;
import ij.ImagePlus;
import ij.gui.*;
import ij.io.Opener;
import java.awt.*;
import ij.plugin.filter.*;
import Bib.Biblioteca;
import Bib.RecTangle;
 

public class lista6_plugin implements PlugInFilter {
	ImagePlus imp;

	public int setup(String arg, ImagePlus imp) {
		this.imp = imp;
		return DOES_ALL;
	}
	
	public void run(ImageProcessor ip) {
		// Imagem Real
		// Leitor de Imagem
		ImagePlus imp2 = IJ.openImage("ct.dcm");
		ImageProcessor ip2 = imp2.getProcessor();
		FloatProcessor ipf2 = (FloatProcessor)ip2.convertToFloat();
		// DesvioPadrao da imagem original
		double desvmed2[] = new double[4];
		desvmed2 = Biblioteca.DesviMed(ipf2);
		IJ.log("Desvio padrao da imagem "  + desvmed2[0]);
		
		// Imagem com Ruido
		FloatProcessor ipf = (FloatProcessor)ip.convertToFloat();
		int w = ipf.getWidth();int h = ipf.getHeight();	
		double sigmax = 1.5; double sigmay = 1.5;

		// Calculos Imagem sem e com ruido 
		double Emax = Biblioteca.EMax(ipf2, ipf);
		IJ.log("O valor de EMax I1I0 é de " + Emax);
		double NRMSE = Biblioteca.NRMSE(ipf2, ipf);
		IJ.log("O valor de NRMSE I1I0 é de " + NRMSE);
		double SSIM = Biblioteca.SSIM(ipf2, ipf);
		IJ.log("O valor de SSIM I1I0 é de " + SSIM);

		/////////////////////////////////////////////////////////////////////////
		// Imagem Filtrada (sigma = 1.5)
		FloatProcessor ipf3 = Biblioteca.GeraFiltroGauss(ipf, sigmax, sigmay);				 
		ImagePlus imp3 = new ImagePlus("Imagem Filtrada (sigma = 1.5)", ipf3);
		imp3.show();
		// Calculos Imagem sem e com ruido 1.5
		double Emax2 = Biblioteca.EMax(ipf2, ipf3);
		IJ.log("O valor de EMax I1.5I0 é de " + Emax2);
		double NRMSE2 = Biblioteca.NRMSE(ipf2, ipf3);
		IJ.log("O valor de NRMSE I1.5I0 é de " + NRMSE2);
		double SSIM2 = Biblioteca.SSIM(ipf2, ipf3);
		IJ.log("O valor de SSIM I1.5I0 é de " + SSIM2);		

		/////////////////////////////////////////////////////////////////////////
		// Imagem Filtrada (sigma = 4.5)
		sigmax = 4.5;
		FloatProcessor ipf4 = Biblioteca.GeraFiltroGauss(ipf, sigmax);				 
		ImagePlus imp4 = new ImagePlus("Imagem Filtrada (sigma = 4.5)", ipf4);
		imp4.show();
		// Calculos Imagem sem e com ruido 1.5
		double Emax3 = Biblioteca.EMax(ipf2, ipf4);
		IJ.log("O valor de EMax I4.5I0 é de " + Emax3);
		double NRMSE3 = Biblioteca.NRMSE(ipf2, ipf4);
		IJ.log("O valor de NRMSE I4.5I0 é de " + NRMSE3);
		double SSIM3 = Biblioteca.SSIM(ipf2, ipf4);
		IJ.log("O valor de SSIM I4.5I0 é de " + SSIM3);		

		/////////////////////////////////////////////////////////////////////////
		// Filtro de Wien
		// Selecionando regiao homogenea
		RecTangle Slice = new RecTangle(18,32,80,30,ipf);
		double desvmed5[] = new double[4];
		desvmed5 = Biblioteca.DesviMed(Slice.ip);
		IJ.log("Desvio padrao da regiao homogenea: " + desvmed5[0]);
		ImagePlus imp5 = new ImagePlus("Slice", Slice.ip); 
		imp5.show();
		
		FloatProcessor ipf6 = new FloatProcessor(w,h);
		try {
			ipf6 = Biblioteca.Filtro_Wier(ipf, desvmed5[0], 3);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("Dimensao deve ser impar");
		}
		ImagePlus imp6 = new ImagePlus("Filtro Wien", ipf6); 
		imp6.show();
		
		// Cáculos
		double Emax4 = Biblioteca.EMax(ipf2, ipf6);
		IJ.log("O valor de EMax WienI0 é de " + Emax4);
		double NRMSE4 = Biblioteca.NRMSE(ipf2, ipf6);
		IJ.log("O valor de NRMSE WienI0 é de " + NRMSE4);
		double SSIM4 = Biblioteca.SSIM(ipf2, ipf6);
		IJ.log("O valor de SSIM WienI0 é de " + SSIM4);
		
		/////////////////////////////////////////////////////////////////////////
		// Filtro de Media
		FloatProcessor ipf7 = new FloatProcessor(w, h); 
		FloatProcessor tmp = new FloatProcessor(w, h); 
		double[][] pesos_med = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};
		try {
			// Convolucao
			tmp = Biblioteca.Convolucao(ipf, pesos_med);
			// Dividir todo mundo pelo tamanho de pesos_med
			ipf7 = Biblioteca.Divide(tmp, pesos_med[0].length * pesos_med.length);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("Divisao deve ser impar");
		}
		ImagePlus imp7 = new ImagePlus("Filtro Media", ipf7);
		imp7.show();
		
		// Cáculos
		double Emax5 = Biblioteca.EMax(ipf2, ipf7);
		IJ.log("O valor de EMax MediaI0 é de " + Emax5);
		double NRMSE5 = Biblioteca.NRMSE(ipf2, ipf7);
		IJ.log("O valor de NRMSE MediaI0 é de " + NRMSE5);
		double SSIM5 = Biblioteca.SSIM(ipf2, ipf7);
		IJ.log("O valor de SSIM MediaI0 é de " + SSIM5);
			
	
	}

}
