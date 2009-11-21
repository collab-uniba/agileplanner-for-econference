/***************************************************************************************************
 *Author: Robert Morgan
 *Date: Aug 2006
 *Project: MasePlanner
 *Description: 	MasePlanner is a distributed planning tool for agile teams. 
 *Class Description: Helper class to locate and place the direct edit cell. 
 *								
 *Modified After: This class is modified after a similar class in the "Logic Example Project" provided by the eclipse GEF project.  
 ***************************************************************************************************/
package cards.celleditorlocator;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Text;

import cards.CardConstants;
import cards.editpart.IterationCardEditPart;
import cards.figure.IterationCardFigure;



public class IterationCardDescriptionLabelCellEditorLocation implements CellEditorLocator {

    private IterationCardFigure iterationFigure;
    private IterationCardEditPart editpart;

    public IterationCardDescriptionLabelCellEditorLocation(IterationCardFigure figure,IterationCardEditPart editpart) {
        setIterationCardFigure(figure);
        
        this.editpart = editpart;
    }

    public void relocate(CellEditor celleditor) {
    	
    	ScalableFreeformRootEditPart root = (ScalableFreeformRootEditPart) editpart.getRoot();
		double ratio = root.getZoomManager().getZoom();
		
        Text text = (Text) celleditor.getControl();
        
        Rectangle rect = iterationFigure.getClientArea();
        iterationFigure.translateToAbsolute(rect);
        org.eclipse.swt.graphics.Rectangle trim = text.computeTrim(0, 0, 0, 0);
        rect.translate(trim.x, trim.y);
        rect.width = rect.width
                + trim.width
                - ((System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) ? CardConstants.MacIterationFigureDescriptionPoint
                        : CardConstants.WindowsIterationFigureDescriptionPoint) - 5;
        rect.height = 13 + trim.height;
        text.setBounds(rect.x
                + ((System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) ? CardConstants.MacIterationFigureDescriptionPoint
                        : (int)(CardConstants.WindowsIterationFigureDescriptionPoint*ratio)), (int)((rect.y + 20)*ratio), (int)(rect.width*ratio), (int)(rect.height*ratio));
    }

    protected IterationCardFigure getFigure() {
        return iterationFigure;
    }

    protected void setIterationCardFigure(IterationCardFigure figure) {
        this.iterationFigure = figure;
    }
}