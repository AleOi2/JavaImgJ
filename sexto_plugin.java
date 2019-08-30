import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.filter.*;

public class sexto_plugin implements PlugInFilter {
	ImagePlus imp;

	public int setup(String arg, ImagePlus imp) {
		this.imp = imp;
		return DOES_ALL;
	}
	public void run(ImageProcessor ip) {
		// 1) converte imagem p/ float. P/ ficar independente do tipo
		FloatProcessor ip_float=(FloatProcessor)ip.convertToFloat();
		ImagePlus imp_float=new ImagePlus("Float", ip_float); // opcional
		IJ.showMessage("Estou na classe run");
		// 2) obtendo as dimensoes da imagem
		int w= ip_float.getWidth();
		int h=ip_float.getHeight();
		IJ.log("Imagem com nlinhas= " + h + " e ncolunas= "+ w);
		// 3) le um numero do usuario
		int N=3; // deve ser impar
		int nc=N/2;
		// criando os pesos para as medias 
		double[][] pesos={{0, 1, 0}, {1, -4, 1}, {0, 1, 0}}; //pesos[linha][coluna]
		// 4) criando nova imagem de mesmo tamanho p/ nao interferir no original 
		FloatProcessor ip_float2=new FloatProcessor (w,h);
		ImagePlus imp_float2=new ImagePlus("Resultado", ip_float2);
		// realizando a convolucao com o kernel da media
		for(int y=nc; y< h-nc; y++) // nao considera bordas
			for (int x=nc; x<w-nc; x++) // nao considera bordas
				{   double media=0;
					for(int r=-nc; r<=nc; r++) {
						for(int c=-nc; c<=nc; c++) 
							media=media + pesos[r+nc][c+nc]*ip_float.getf(x-c,y-r); 
						}
					ip_float2.setf(x,y,(float)media); 
				}
				// 5) mostrando resultados em nova janela e pode salvar usando o imageJ
				imp_float2.show(); }
}

