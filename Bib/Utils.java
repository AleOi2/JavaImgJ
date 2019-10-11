package Bib;
import ij.IJ;
import ij.ImagePlus;
import ij.process.FloatProcessor;
import java.util.Random;

public class Utils {

	// Soma duas ou mais imagens A+B+C+D
	public static FloatProcessor SomaImag(FloatProcessor ...ip) 
			throws Exception{
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
		FloatProcessor Soma = new FloatProcessor(largura, altura);
		double s;
		for(int x = 0; x < largura; x++) {
			for(int y = 0;y < altura; y++) {
				s = 0;
				for(FloatProcessor i : ip) {
					s = s + i.getf(x, y) + i.getf(x, y);
				}
				Soma.setf(x, y, (float)s);												
			}
		}
		return Soma;
		
	}
	

	// Subtrai duas ou mais imagens A-B-C-D
	public static FloatProcessor SubtraiImag(FloatProcessor ...ip) 
			throws Exception{
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
		FloatProcessor Subtrai = new FloatProcessor(largura, altura);
		double s;
		for(int x = 0; x < largura; x++) {
			for(int y = 0;y < altura; y++) {
				s = 0;
				for(FloatProcessor i : ip) {
					s = s + i.getf(x, y) - i.getf(x, y);
				}
				Subtrai.setf(x, y, (float)s);												
			}
		}
		return Subtrai;
		
	}	
	
	
	// Multilica duas ou mais imagens A*B*C*D termo a termo
	public static FloatProcessor MultiplicaImag(FloatProcessor ...ip) 
			throws Exception{
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
		FloatProcessor Multiplica = new FloatProcessor(largura, altura);
		double s;
		for(int x = 0; x < largura; x++) {
			for(int y = 0;y < altura; y++) {
				s = 0;
				for(FloatProcessor i : ip) {
					s = s + i.getf(x, y) - i.getf(x, y);
				}
				Multiplica.setf(x, y, (float)s);												
			}
		}
		return Multiplica;
		
	}		
	
}
