package Bib;

import ij.IJ;
import ij.ImagePlus;
import ij.process.FloatProcessor;
import java.util.Random;

public class Biblioteca {
	// Utilizados para essa lista
	public static double EMax(FloatProcessor ip, FloatProcessor ip2) {
		int w = ip.getWidth();
		int h = ip.getHeight();
		double maximo = Double.MIN_VALUE;
		
		for(int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				if(Math.abs(ip.getf(x, y) - ip2.getf(x, y)) > maximo) {
					maximo = Math.abs(ip.getf(x, y) - ip2.getf(x, y));
				}				
			}
		}		
		return maximo;
	}
	/////////////////////////////////////////////////////////////////////////
	public static double NRMSE(FloatProcessor ip, FloatProcessor ip2) {
		double nrmse = 0;
		int w = ip.getWidth();
		int h = ip.getHeight();
		double soma_dif2, soma2;
		soma2 = 0; soma_dif2 = 0; 
		
		for(int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				soma2 = soma2 + ip.getf(x, y) * ip.getf(x, y);
				soma_dif2 = soma_dif2 + (ip.getf(x, y) - ip2.getf(x, y)) * 
						(ip.getf(x, y) - ip2.getf(x, y));
				
			}
		}		
		nrmse = Math.sqrt(soma_dif2/soma2);
		return nrmse;
	}
	/////////////////////////////////////////////////////////////////////////
	// Calcula a covariância
	public static double Cov(FloatProcessor ip, FloatProcessor ip2) {
		int w = ip.getWidth();
		int h = ip.getHeight();
		double desvmed1[] = new double[2];
		double desvmed2[] = new double[2];
		desvmed1 = DesviMed(ip);
		desvmed2 = DesviMed(ip2);
		double cov, soma;
		soma = 0; 
		
		for(int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				soma = soma + (ip.getf(x, y) - desvmed1[1]) * (ip2.getf(x, y) - desvmed2[1]);				
			}
		}
		cov = soma/ (h * w);
		return cov;
	}
	// Calcula Q	
	public static double SSIM(FloatProcessor ip, FloatProcessor ip2) {
		double ssim, cov;
		double[] desvmedip = new double[2];
		double[] desvmedip2 = new double[2];
		
		desvmedip = DesviMed(ip);// Retorna a média, desvio, minimo e maximo
		desvmedip2 = DesviMed(ip2);// Retorna a média, desvio, minimo e maximo
		cov = Cov(ip, ip2);// Retorna a covariância
		
		ssim = cov/(desvmedip2[0] * desvmedip[0]) * 
				2 * desvmedip2[1] * desvmedip[1]/(desvmedip2[1] * desvmedip2[1] + desvmedip[1] * desvmedip[1]) *
				2 * desvmedip2[0] * desvmedip[0]/(desvmedip2[0] * desvmedip2[0] + desvmedip[0] * desvmedip[0]);		
		return ssim;
		
	}
	/////////////////////////////////////////////////////////////////////////
	
	// Calcula Q - Método 2	
	// ip2 é sem ruído
	public static double SSIM2(FloatProcessor ip, FloatProcessor ip2) {
		double ssim, cov;
		double[] desvmedip = new double[2];
		double[] desvmedip2 = new double[2];
		
		desvmedip = DesviMed(ip);// Retorna a média, desvio, minimo e maximo
		desvmedip2 = DesviMed(ip2);// Retorna a média, desvio, minimo e maximo
		cov = Cov(ip, ip2);// Retorna a covariância
		
		double c = (desvmedip[4] - desvmedip[3])/10000 * 
						(desvmedip[4] - desvmedip[3])/10000; 
		ssim = (2 * cov + c)/(desvmedip2[0] * desvmedip2[0] +  desvmedip[0] * desvmedip[0] + c) * 
		(2 * desvmedip2[1] * desvmedip[1] + c)/(desvmedip2[1] * desvmedip2[1] + desvmedip[1] * desvmedip[1] + c); 
		return ssim;
		
	}
	/////////////////////////////////////////////////////////////////////////

	
	// Dado um FloatProcessor, calcula a média, desvio-padrão, minimo e maximo	
	public static double[] DesviMed(FloatProcessor ip) {
		int w = ip.getWidth();
		int h = ip.getHeight();
		double desvpad, soma, media, soma2;
		double minimo, maximo;
		double desvmed[] = new double[4];
		minimo = Double.MAX_VALUE; maximo = Double.MIN_VALUE;		
		soma = 0;soma2 = 0;
		for(int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				soma = soma + ip.getf(x, y);
				soma2 = soma2 + ip.getf(x, y) * ip.getf(x, y);
				if(ip.getf(x, y) < minimo) {
					minimo = ip.getf(x, y);
				}else if(ip.getf(x, y) > maximo) {
					maximo = ip.getf(x, y);
				}
			}
		}		
		media = soma/(w * h);
		desvpad = soma2/(w * h - 1);
		desvpad = Math.sqrt(desvpad - (w * h) * media * media/(w * h - 1));
		desvmed[0] = desvpad; desvmed[1] = media;
		desvmed[2] = minimo; desvmed[3] = maximo;
		System.out.println(desvmed[0]);
		return desvmed;
	}
	

	/////////////////////////////////////////////////////////////////////////
	// Retorna o desvio padrao do erro dado dB
	public static double Db2SigmaError(double DesvPad, double Db) {
		return DesvPad/Math.pow(10, Db/20);
	}	

	/////////////////////////////////////////////////////////////////////////
	// Calcula o coeficiente de correlação
	
	public static double CoefCorr(FloatProcessor ip, FloatProcessor ip2) {
		double Covar;
		double[] desvmed = new double[4];
		double[] desvmed2 = new double[4];
		Covar = Cov(ip, ip2);// Devolve a covariância entre elementos
		desvmed = DesviMed(ip); // Devolve o desvio-padrão, a média, o minimo e o maximo 
		desvmed2 = DesviMed(ip2); // Devolve o desvio-padrão, a média, o minimo e o maximo
		return Covar/(desvmed[0] * desvmed2[0]);
	}
	/////////////////////////////////////////////////////////////////////////
    // Método para usar convolução
	// Matriz nxn
	public static void Prod_Conv1(FloatProcessor Conv, double s, int x, int y, 
	      int a, int b, double[][] pesos, FloatProcessor ip1){
		
		for(int x1 = -a;x1 <= a;x1++) {
			for(int y1 = -b;y1 <= b;y1++) {
				s = s + pesos[y1 + b][x1 + a] * ip1.getf(x - x1, y - y1);
			}
		}				
		Conv.setf(x, y, (float)s);		
	}
	// Matriz 1xn, 1x1, nx1
	public static void Prod_Conv2(FloatProcessor Conv, double s, int x, int y, 
		    int a, int b, double[][] pesos, FloatProcessor ip1){			
			// pesos é tipo coluna
			if (a == 0 && b != 0) {
				int x1 = 0;
				for(int y1 = -b;y1 <= b;y1++) {					
					s = s + pesos[y1 + b][x1] * ip1.getf(x, y - y1);
				}				
				Conv.setf(x, y, (float)s);		
			}
			// pesos é tipo linha
			else if(b == 0 && a != 0) {
				int y1 = 0;
				for(int x1 = -a;x1 <= a;x1++) {
					s = s + pesos[y1][x1 + a] * ip1.getf(x - x1, y)/100;
				}				
				Conv.setf(x, y, (float)s);		
				
			}			
			// pesos é um único elemento
			else if(b == 0 && a != 0) {
				int x1 = 0;int y1 = 0;
				s = s + pesos[x1][y1] * ip1.getf(x, y);
				Conv.setf(x, y, (float)s);						
			}			

	}
	
	public static FloatProcessor Convolucao(FloatProcessor ip1, double[][] pesos) throws Exception{
		int H = ip1.getHeight();int W = ip1.getWidth();
		int a = (pesos[0].length - 1)/2;
		int b = (pesos.length - 1)/2;
		double s;
		System.out.println(a);
		if ((pesos.length % 2 == 0)||(pesos[0].length % 2 == 0)) {
			throw new Exception();
		}	
		System.out.println(a%2);
		FloatProcessor Conv = new FloatProcessor(W, H);
		
		for (int y = b; y < H - b; y++) {
			for (int x = a; x < W - a ; x++) {
				s = 0;
				if (a == 0 && b != 0) {
					// pesos é tipo coluna
					Prod_Conv2(Conv, s, x, y, a, b, pesos, ip1);
				}else if (b == 0 && a != 0) {
					// pesos é tipo linha
					Prod_Conv2(Conv, s, x, y, a, b, pesos, ip1);
				}else if (a == 0 && b == 0) {
					// pesos só tem um elemento
					Prod_Conv2(Conv, s, x, y, a, b, pesos, ip1);
				}else if (a != 0 && b != 0) {
					Prod_Conv1(Conv, s, x, y, a, b, pesos, ip1);
				}
			}
		}
		ImagePlus imp2 = new ImagePlus("Float", Conv);
		//imp2.show();
		
		return Conv;
	}


	/////////////////////////////////////////////////////////////////////////
    // Método para usar correlação - Igual Convolucao mudando
	// o sinal de ip1.getf no Prod_Corr
	public static void Prod_Corr1(FloatProcessor Conv, double s, int x, int y, 
		      int a, int b, double[][] pesos, FloatProcessor ip1){
			
			for(int x1 = -a;x1 <= a;x1++) {
				for(int y1 = -b;y1 <= b;y1++) {
					s = s + pesos[y1 + b][x1 + a] * ip1.getf(x + x1, y + y1);
				}
			}				
			Conv.setf(x, y, (float)s);		
	}
		
	public static void Prod_Corr2(FloatProcessor Conv, double s, int x, int y, 
		    int a, int b, double[][] pesos, FloatProcessor ip1){			
			// pesos é tipo coluna
			if (a == 0 && b != 0) {
				int x1 = 0;
				for(int y1 = -b;y1 <= b;y1++) {					
					s = s + pesos[y1 + b][x1] * ip1.getf(x, y + y1);
				}				
				Conv.setf(x, y, (float)s);		
			}
			// pesos é tipo linha
			else if(b == 0 && a != 0) {
				int y1 = 0;
				for(int x1 = -a;x1 <= a;x1++) {
					s = s + pesos[y1][x1 + a] * ip1.getf(x + x1, y);
				}				
				Conv.setf(x, y, (float)s);		
				
			}			
			// pesos é um único elemento
			else if(b == 0 && a != 0) {
				int x1 = 0;
				int y1 = 0;
				s = s + pesos[x1][y1] * ip1.getf(x, y);
				Conv.setf(x, y, (float)s);						
			}			

	}
	
	public static FloatProcessor Correlacao(FloatProcessor ip1, 
			double[][] pesos) throws Exception{
		int H = ip1.getHeight();int W = ip1.getWidth();
		int a = (pesos[0].length - 1)/2;
		int b = (pesos.length - 1)/2;
		double s;
		System.out.println(a);
		if ((pesos.length % 2 == 0)||(pesos[0].length % 2 == 0)) {
			throw new Exception();
		}	
		System.out.println(a%2);
		FloatProcessor Conv = new FloatProcessor(W, H);
		ImagePlus imp2 = new ImagePlus("Float", Conv);
		
		for (int y = b; y < H - b; y++) {
			for (int x = a; x < W - a ; x++) {
				s = 0;
				if (a == 0 && b != 0) {
					// pesos é tipo coluna
					Prod_Corr2(Conv, s, x, y, a, b, pesos, ip1);
				}else if (b == 0 && a != 0) {
					// pesos é tipo linha
					Prod_Corr2(Conv, s, x, y, a, b, pesos, ip1);
				}else if (a == 0 && b == 0) {
					// pesos só tem um elemento
					Prod_Corr2(Conv, s, x, y, a, b, pesos, ip1);
				}else if (a != 0 && b != 0) {
					Prod_Corr1(Conv, s, x, y, a, b, pesos, ip1);
				}
			}
		}
		imp2.show();
		
		return Conv;
	}

	/////////////////////////////////////////////////////////////////////////
    // Método para calcular gradiente
	public static FloatProcessor Gradiente(FloatProcessor ip1) {
		double[][] pesos_gradx = {{1, 0, -1}, {1, 0, -1}, {1, 0, -1}};
		double[][] pesos_grady = {{1, 1, 1}, {0, 0, 0}, {-1, -1, -1}};
		int w = ip1.getWidth();
		int h = ip1.getHeight();		
		FloatProcessor GradX = new FloatProcessor(w, h); 
		FloatProcessor GradY = new FloatProcessor(w, h); 
		FloatProcessor Grad = new FloatProcessor(w, h); 
		
		// Gradiente do vetor x
		try {
			GradX = Convolucao(ip1, pesos_gradx);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Gradiente do vetor y
		try {
			GradY = Convolucao(ip1, pesos_grady);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		try {
			Grad = ModuloImg(GradX, GradY);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Grad;
	}

	/////////////////////////////////////////////////////////////////////////
    // Método para calcular gradiente
	public static FloatProcessor ModuloImg(FloatProcessor ...ip) throws Exception{
		// Verificando  se as dimensoes sao iguais
		int Qtde = ip.length;
		int altura, largura;altura = 0; largura = 0;
		ImagePlus teste1 = new ImagePlus("Float", ip[0]); 
		ImagePlus teste2 = new ImagePlus("Float", ip[1]);
		
		//teste1.show();
		//teste2.show();
		switch(Qtde) {
		// Se entrada igual a 0, deve-se enviar um erro
		case 0: 
			throw new Exception();
		// Se um ou mais elementos, necessário verificar se todos tem mesmo tamanho
		default:				
			boolean primeiro; primeiro = true;				
			for (FloatProcessor i:ip) {
				// Buscando primeiro elemento
				if (primeiro) {
					altura = i.getHeight();
					largura = i.getWidth();
					primeiro = false;
				}
				// Verificando se todos tem mesmo tamanho
				if (altura != i.getHeight() || largura != i.getWidth()) {
					throw new Exception();
				}
			}
			break;
		}
		// Calculando valor do modulo
		FloatProcessor Mod = new FloatProcessor(largura, altura);
		double s;
		for(int x = 0; x < largura; x++) {
			for(int y = 0;y < altura; y++) {
				s = 0;
				for(FloatProcessor i : ip) {
					s = s + i.getf(x, y) * i.getf(x, y);
				}
				s = Math.sqrt(s);
				Mod.setf(x, y, (float)s);												
			}
		}
		return Mod;
	}	

}
	

