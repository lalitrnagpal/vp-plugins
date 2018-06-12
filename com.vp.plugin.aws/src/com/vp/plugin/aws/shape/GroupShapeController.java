package com.vp.plugin.aws.shape;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;

import javax.imageio.*;

import com.vp.plugin.aws.*;
import com.vp.plugin.diagram.*;

public abstract class GroupShapeController extends AbstractShapeController implements VPShapeModelController {
	
	private final String _imageRelativePath;
	protected final Stroke _stroke;
	private Image _titleImage;
	
	public GroupShapeController(String aImageRelativePath, Stroke aStroke) {
		if (aImageRelativePath != null) {
			_imageRelativePath = "resources/"+File.separator+"borderTitle" + File.separator + aImageRelativePath;
		}
		else {
			_imageRelativePath = null;
		}
		_stroke = aStroke;
	}
	
	private boolean _inited = false;
	private void initTitleImage() {
		if (! _inited) {
			_inited = true;
			
			if (_imageRelativePath != null) {
				try {
					InputStream lIs = AWSPlugin.loadResourcesFile(_imageRelativePath);
					try {
						_titleImage = ImageIO.read(lIs);
					}
					finally {
						lIs.close();
					}
					
				} catch (Exception lE) {
					lE.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void initDefaultProperties(IDiagramElement aDiagramElement) {
		// TODO IDiagramElement.ILineModel should support setting Stroke
		
		((IShapeUIModel) aDiagramElement).getFillColor().setTransparency(100);
	}
	
	@Override
	public void drawShape(Graphics2D aGraphics2D, Paint aLineColor, Paint aFillColor, Stroke aStroke, VPShapeInfo aShapeInfo) {
		initTitleImage();
		
		Rectangle2D lBounds = aShapeInfo.getBounds();
		
		drawBorder(aGraphics2D, lBounds, aFillColor, new BorderLine(_stroke, aLineColor));
	}
	protected static class BorderLine {
		public final Stroke stroke;
		public final Paint lineColor;
		
		public BorderLine(Stroke aStroke, Paint aLineColor) {
			this.stroke = aStroke;
			this.lineColor = aLineColor;
		}
	}
	protected final void drawBorder(Graphics2D aGraphics2D, Rectangle2D aBounds, Paint aFillColor, BorderLine... aBorderLines) {
		
		int lTitleCenterY = 0;
		if (_titleImage != null) {
			lTitleCenterY = _titleImage.getHeight(null)/2;
		}
		
		RoundRectangle2D.Double lBorder = new RoundRectangle2D.Double(0, lTitleCenterY, aBounds.getWidth(), aBounds.getHeight()-lTitleCenterY, 20, 20);
		
		aGraphics2D.setPaint(aFillColor);
		aGraphics2D.fill(lBorder);
		
		if (aBorderLines != null) {
			for (BorderLine lBorderLine : aBorderLines) {
				if (lBorderLine.stroke != null) {
					aGraphics2D.setStroke(lBorderLine.stroke);
					aGraphics2D.setPaint(lBorderLine.lineColor);
					aGraphics2D.draw(lBorder);
				}
			}
		}
		
		if (_titleImage != null) {
			aGraphics2D.drawImage(_titleImage, 5, 0, null);
		}
		
	}

}
