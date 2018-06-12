package com.vp.plugin.aws.shape;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;

import com.vp.plugin.aws.*;
import com.vp.plugin.aws.util.*;
import com.vp.plugin.diagram.*;

public abstract class SVGShapeController extends AbstractShapeController {
	
	private String _imageRelativePath;
	private SVGPainter _painter;
	
	public SVGShapeController(String aImageRelativePath) {
		_imageRelativePath = aImageRelativePath;
	}
	
	private boolean _inited = false;
	private void initPainter() {
		if (! _inited) {
			_inited = true;
			
			try {
				System.out.println("INIT PAINTER: " + _imageRelativePath);
				InputStream lIs = AWSPlugin.loadResourcesFile(_imageRelativePath);
				try {
					_painter = new SVGPainter(lIs);
				}
				finally {
					lIs.close();
				}
				
			} catch (Exception lE) {
				lE.printStackTrace();
			}
		}
	}
	
	@Override
	public void drawShape(Graphics2D aGraphics2D, Paint aLineColor, Paint aFillColor, Stroke aStroke, VPShapeInfo aShapeInfo) {
		initPainter();
		
		Rectangle2D lBounds = aShapeInfo.getBounds();
		if (_painter != null) {
			_painter.paint(aGraphics2D, lBounds);
		}
		else {
			aGraphics2D.drawLine(0, 0, (int) lBounds.getWidth(), (int) lBounds.getHeight());
			aGraphics2D.drawLine((int) lBounds.getWidth(), 0, 0, (int) lBounds.getHeight());
		}
	}

}
