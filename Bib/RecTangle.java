package Bib;

import ij.ImagePlus;
import ij.process.FloatProcessor;
import Bib.Biblioteca;
;

public class RecTangle{
	
	private int left_top_edge_x,left_top_edge_y;
	private int width,height;
	public FloatProcessor ip;
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param ip
	 */
	public RecTangle(int x, int y, int width, int height, FloatProcessor ip){
		this.left_top_edge_x = x;
		this.left_top_edge_y = y;
		if (x + width < ip.getWidth()) {
			this.width = width;			
		}else {
			this.width = ip.getWidth() - x;
		}
		
		if (y + height < ip.getHeight()) {
			this.height = height;		
		}else {
			this.height = ip.getHeight() - y;
		}
		GeraRetangulo(ip);		
		
	}
	
	public void GeraRetangulo(FloatProcessor ip) {
		this.ip = new FloatProcessor(this.width, this.height);
		
		for (int index = this.left_top_edge_x;index < this.left_top_edge_x + this.width;index++) {
			for(int index2 = this.left_top_edge_y;index2 < this.left_top_edge_y + this.height;index2++) {
				this.ip.setf(index - this.left_top_edge_x, index2 - this.left_top_edge_y, 
						ip.getf(index, index2)) ;
			}
		}
				
	}
	

	// Getters and Setters
	public int getLeft_top_edge_x() {
		return this.left_top_edge_x;
	}
	public void setLeft_top_edge_x(int left_top_edge_x) {
		this.left_top_edge_x = left_top_edge_x;
	}
	public int getLeft_top_edge_y() {
		return this.left_top_edge_y;
	}
	public void setLeft_top_edge_y(int left_top_edge_y) {
		this.left_top_edge_y = left_top_edge_y;
	}		
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}

	
}
