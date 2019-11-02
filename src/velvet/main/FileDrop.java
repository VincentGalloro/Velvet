package velvet.main;

import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class FileDrop extends DropTarget {

    private final LinkedList<File> fileQueue;

    public FileDrop(){
        fileQueue = new LinkedList<>();
    }

    public synchronized void drop(DropTargetDropEvent evt) {
        try {
            evt.acceptDrop(DnDConstants.ACTION_COPY);
            List<File> droppedFiles = (List<File>)
                    evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
            fileQueue.addAll(droppedFiles);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public synchronized boolean hasFile(){ return !fileQueue.isEmpty(); }
    public synchronized File popFile(){ return fileQueue.pop(); }
}
