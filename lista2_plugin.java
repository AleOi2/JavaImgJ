import ij.*;
import ij.process.*;
import ij.gui.*;
import ij.io.Opener;

import java.awt.*;
import ij.plugin.filter.*;
import Bib.Biblioteca;

public class lista2_plugin implements PlugInFilter {
	ImagePlus imp;

	public int setup(String arg, ImagePlus imp) {
		this.imp = imp;
		return DOES_ALL;
	}

	public void run(ImageProcessor ip) {
		FloatProcessor ipf = (FloatProcessor)ip.convertToFloat();
		int w = ipf.getWidth();
		int h = ipf.getHeight();		
		double Sigma_E, Emax, NRMSE, SSIM, CoefCor;
		double[] desvmed2 = new double[4];
		double[] desvmed1 = new double[4];
		
		desvmed1 = Biblioteca.DesviMed(ipf);
		// Primeiro jeito
		// Abertura do Segundo arquivo
		Opener im2=new Opener();
		IJ.showMessage("Escolha a 2a imagem ");
		ImagePlus imp_float2=im2.openImage(""); // open file dialog
		ImageProcessor ip3=imp_float2.getProcessor(); // criando o objeto
		FloatProcessor ip_float2=(FloatProcessor)ip3.convertToFloat(); //float

		
		// Cáculos
		Emax = Biblioteca.EMax(ipf, ip_float2);
		IJ.log("O valor de EMax Novo é de " + Emax);
		NRMSE = Biblioteca.NRMSE(ipf, ip_float2);
		IJ.log("O valor de NRMSE é de " + NRMSE);
		SSIM = Biblioteca.SSIM(ipf, ip_float2);
		IJ.log("O valor de SSIM é de " + SSIM);
		desvmed2 = Biblioteca.DesviMed(ip_float2);
		IJ.log("O valor de SigmaS2 é de " + desvmed2[0]);
		IJ.log("O valor de SigmaS1 é de " + desvmed1[0]);
		IJ.log("O valor de SigmaS1 ao 2 é de " + desvmed1[0]*desvmed1[0]);
		Sigma_E = Biblioteca.Db2SigmaError(desvmed2[0], 6);
		IJ.log("O valor de SigmaS2+SigmaE ao 2 é de " + (desvmed2[0]*desvmed2[0] + 133.56*133.56));
		IJ.log("O valor de Mi1 é de " + desvmed1[1]);
		IJ.log("O valor de Mi2 é de " + desvmed2[1]);
		CoefCor = Biblioteca.CoefCorr(ipf, ip_float2);
		IJ.log("O valor de CoefCorr é de " + CoefCor);
		IJ.log("O valor de SigmaE é de " + Sigma_E);
		IJ.log("O valor de Minimo é de " + desvmed2[2]);
		IJ.log("O valor de Maximo é de " + desvmed2[3]);

		/*// Media e desvio padrao	
		double[] desvmed = new double[4];
		desvmed = Biblioteca.DesviMed(ipf);
		Sigma_E = Biblioteca.Db2SigmaError(desvmed[0], 3);

		// Colocar ruido com Sigma = 133,557
		IJ.log("O valor de SigmaE é de " + Sigma_E);
		IJ.log("O valor de SigmaS é de " + desvmed[0]);
		IJ.log("O valor de Media é de " + desvmed[1]);		
		// Segundo jeito
		ImagePlus imp2 = IJ.openImage("ct.dcm");
		ImageProcessor ip2 = imp2.getProcessor();
		FloatProcessor ipf2 = (FloatProcessor)ip2.convertToFloat();
		//imp2.show();						
		Emax = Biblioteca.EMax(ipf, ipf2);
		IJ.log("O valor de EMax é de " + Emax);
		NRMSE = Biblioteca.NRMSE(ipf, ipf2);
		IJ.log("O valor de NRMSE é de " + NRMSE);
		SSIM = Biblioteca.SSIM(ipf, ipf2);
		IJ.log("O valor de SSIM é de " + SSIM);
		desvmed2 = Biblioteca.DesviMed(ipf2);
		IJ.log("O valor de SigmaS é de " + desvmed2[0]);
		CoefCor = Biblioteca.CoefCorr(ipf, ipf2);
		IJ.log("O valor de CoefCorr é de " + CoefCor);*/
	}

}
