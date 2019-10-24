import ij.process.*;
import ij.IJ;
import ij.ImagePlus;
import ij.gui.*;
import ij.io.Opener;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import ij.plugin.filter.*;
import Bib.Biblioteca;
import Bib.RecTangle;
 

public class lista7_plugin implements PlugInFilter {
	ImagePlus imp;

	public int setup(String arg, ImagePlus imp) {
		this.imp = imp;
		return DOES_ALL;
	}
	
	public void run(ImageProcessor ip) {
		/////////////////////////////////////////////////////////////////////////
		// Filtro de WienPieckle
		// Selecionando regiao homogenea

		// Imagem sem ruído
		FloatProcessor ipf = (FloatProcessor)ip.convertToFloat();
   
		// Imagem com ruído
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		System.out.println("Current relative path is: " + s);

		ImagePlus imp1 = IJ.openImage(s + "/t1comMin25_cv3.tiff");
		ImageProcessor ip1 = imp1.getProcessor();
		FloatProcessor ipf1 = (FloatProcessor)ip1.convertToFloat();
		RecTangle Slice = new RecTangle(18,32,80,30,ipf1);
		double desvmed[] = new double[4];
		desvmed = Biblioteca.DesviMed(Slice.ip);
		IJ.log("Desvio padrao da regiao homogenea: " + desvmed[0]);
		ImagePlus imp2 = new ImagePlus("Slice", Slice.ip); 
		imp2.show();
		int w = ipf.getWidth();int h = ipf.getHeight();			
		
		FloatProcessor ipf3 = new FloatProcessor(w,h);
		try {
			ipf3 = Biblioteca.Filtro_WierPieckle(ipf1, Slice.ip, 3);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("Dimensao deve ser impar");
		}
		ImagePlus imp3 = new ImagePlus("Filtro Wien Pickle", ipf3); 
		imp3.show();
		
		// Cáculos
		double Emax = Biblioteca.EMax(ipf3, ipf);
		IJ.log("O valor de EMax WienPiI0 é de " + Emax);
		double NRMSE = Biblioteca.NRMSE(ipf3, ipf);
		IJ.log("O valor de NRMSE WienPiI0 é de " + NRMSE);
		double SSIM = Biblioteca.SSIM(ipf3, ipf);
		IJ.log("O valor de SSIM WienPiI0 é de " + SSIM);

		// Cáculos sem filtro
		double Emax2 = Biblioteca.EMax(ipf1, ipf);
		IJ.log("O valor de EMax I1I0 é de " + Emax2);
		double NRMSE2 = Biblioteca.NRMSE(ipf1, ipf);
		IJ.log("O valor de NRMSE I1I0 é de " + NRMSE2);
		double SSIM2 = Biblioteca.SSIM(ipf1, ipf);
		IJ.log("O valor de SSIM I1I0 é de " + SSIM2);

		// Imagem Ruidosa
		imp1.show();

		/////////////////////////////////////////////////////////////////////////
		// Filtro de Wien
		// Selecionando regiao homogenea
		RecTangle Slice2 = new RecTangle(18,32,80,30,ipf);
		double desvmed4[] = new double[4];
		desvmed4 = Biblioteca.DesviMed(Slice2.ip);
		
		FloatProcessor ipf4 = new FloatProcessor(w,h);
		try {
			ipf4 = Biblioteca.Filtro_Wier(ipf1, desvmed4[0], 3);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("Dimensao deve ser impar");
		}
		ImagePlus imp4 = new ImagePlus("Filtro Wien", ipf4); 
		imp4.show();
		
		// Cáculos
		double Emax4 = Biblioteca.EMax(ipf, ipf4);
		IJ.log("O valor de EMax WienI0 é de " + Emax4);
		double NRMSE4 = Biblioteca.NRMSE(ipf, ipf4);
		IJ.log("O valor de NRMSE WienI0 é de " + NRMSE4);
		double SSIM4 = Biblioteca.SSIM(ipf, ipf4);
		IJ.log("O valor de SSIM WienI0 é de " + SSIM4);
		
		// Filtro de Media
		FloatProcessor ipf5 = new FloatProcessor(w, h); 
		FloatProcessor tmp = new FloatProcessor(w, h); 
		double[][] pesos_med = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};
		try {
			// Convolucao
			tmp = Biblioteca.Convolucao(ipf1, pesos_med);
			// Dividir todo mundo pelo tamanho de pesos_med
			ipf5 = Biblioteca.Divide(tmp, pesos_med[0].length * pesos_med.length);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("Divisao deve ser impar");
		}
		ImagePlus imp5 = new ImagePlus("Filtro Media", ipf5);
		imp5.show();
		
		// Cáculos
		double Emax5 = Biblioteca.EMax(ipf, ipf5);
		IJ.log("O valor de EMax MediaI0 é de " + Emax5);
		double NRMSE5 = Biblioteca.NRMSE(ipf, ipf5);
		IJ.log("O valor de NRMSE MediaI0 é de " + NRMSE5);
		double SSIM5 = Biblioteca.SSIM(ipf, ipf5);
		IJ.log("O valor de SSIM MediaI0 é de " + SSIM5);
	
		/////////////////////////////////////////////////////////////////////////
		// Imagem Filtrada (sigma = 1.5)
		double sigmax = 1.5; double sigmay = 1.5;
		FloatProcessor ipf6 = Biblioteca.GeraFiltroGauss(ipf1, sigmax, sigmay);				 
		ImagePlus imp6 = new ImagePlus("Imagem Filtrada (sigma = 1.5)", ipf6);
		imp6.show();
		// Calculos Imagem sem e com ruido 1.5
		double Emax6 = Biblioteca.EMax(ipf, ipf6);
		IJ.log("O valor de EMax I1.5I0 é de " + Emax6);
		double NRMSE6 = Biblioteca.NRMSE(ipf, ipf6);
		IJ.log("O valor de NRMSE I1.5I0 é de " + NRMSE6);
		double SSIM6 = Biblioteca.SSIM(ipf, ipf6);
		IJ.log("O valor de SSIM I1.5I0 é de " + SSIM6);		

		/////////////////////////////////////////////////////////////////////////
		// Imagem Filtrada (sigma = 4.5)
		double sigmax7 = 4.5; double sigmay7 = 4.5;
		FloatProcessor ipf7 = Biblioteca.GeraFiltroGauss(ipf1, sigmax7, sigmay7);				 
		ImagePlus imp7 = new ImagePlus("Imagem Filtrada (sigma = 4.5)", ipf7);
		imp7.show();
		// Calculos Imagem sem e com ruido 4.5
		double Emax7 = Biblioteca.EMax(ipf, ipf7);
		IJ.log("O valor de EMax I4.5I0 é de " + Emax7);
		double NRMSE7 = Biblioteca.NRMSE(ipf, ipf7);
		IJ.log("O valor de NRMSE I4.5I0 é de " + NRMSE7);
		double SSIM7 = Biblioteca.SSIM(ipf, ipf7);
		IJ.log("O valor de SSIM I4.5I0 é de " + SSIM7);		
	}

}
