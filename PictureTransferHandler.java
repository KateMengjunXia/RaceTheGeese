
/*
 * PictureTransferHandler.java is used by the 1.4
 * DragPictureDemo.java example.
 */

import java.io.*;
import java.awt.*;
import java.awt.datatransfer.*;
import javax.swing.*;
import java.util.List;

class PictureTransferHandler extends TransferHandler {
    DTPicture sourcePic;
    boolean shouldRemove;

    /* 
     * Can we import one of the flavors provided? 
     */
    public boolean canImport(JComponent c, DataFlavor[] flavors) {
        for (int i = 0; i < flavors.length; i++) {
            if (flavors[i].equals(DataFlavor.imageFlavor) || 
            		flavors[i].equals(DataFlavor.javaFileListFlavor)) {
                return true;
            }
        }
        return false;
    }

    /* 
     * Import the data from the transferable to the component.
     */
    public boolean importData(JComponent c, Transferable t) {
        Image image;
        if (canImport(c, t.getTransferDataFlavors())) {
            DTPicture pic = (DTPicture)c;
            //Don't drop on myself.
            if (sourcePic == pic) {
                shouldRemove = false;
                return true;
            }
            
            try {
            	if (t.isDataFlavorSupported(DataFlavor.imageFlavor)) {
            		image = (Image)t.getTransferData(DataFlavor.imageFlavor);
            	} else if (t.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                	List<File> files = (List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);
                    File f = files.get(0);
                	ImageIcon iIcon = new ImageIcon(f.getAbsolutePath());
                    image = iIcon.getImage();
            	} else {
            		image = null;	// assure compiler everything was initialized
            		assert false;
            	}
                //Set the component to the new picture.
                pic.setImage(image);
                return true;
            } catch (UnsupportedFlavorException ufe) {
                System.out.println("importData: unsupported data flavor");
            } catch (IOException ioe) {
                System.out.println("importData: I/O exception");
            }
        }
        return false;
    }

    /* 
     * What kinds of drag actions can we support?
     */
    public int getSourceActions(JComponent c) {
        return COPY_OR_MOVE;
    }

    /*
     * Create a transferable to drag somewhere else.
     */
    protected Transferable createTransferable(JComponent c) {
        sourcePic = (DTPicture)c;
        shouldRemove = true;
        return new PictureTransferable(sourcePic);
    }

    /*
     * Finish the export.
     */
    protected void exportDone(JComponent c, Transferable data, int action) {
        if (shouldRemove && (action == MOVE)) {
            sourcePic.setImage(null);
        }
        sourcePic = null;
    }
}
