package javafx.draganddrop;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.InvalidDnDOperationException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Application to be run by SwingNodeDragDropTest:
 * simple swing drag and drop inside a SwingNode.
 * @author andrey.rusakov
 */
public class SwingNodeDragDropApp extends Application {

    private static class TransferableDummy implements Transferable {

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{DataFlavor.stringFlavor};
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return true;
        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
            //For now return value doesn't matter so method returns just an empty string
            return "";
        }
    }

    public static Throwable dragDropException;
    public static String dropResult;

    private void createSwingContent(SwingNode swingNode) {
        SwingUtilities.invokeLater(() -> {
            JPanel root = new JPanel();
            root.setLayout(new GridLayout(1, 2, 16, 16));

            JPanel dragSource = new JPanel();
            dragSource.setBackground(Color.RED);
            dragSource.add(new JLabel("Drag me"));

            JPanel dropTarget = new JPanel();
            dropTarget.setBackground(Color.GREEN);
            dropTarget.add(new JLabel("Drop here"));

            root.add(dragSource);
            root.add(dropTarget);

            DragSource source = DragSource.getDefaultDragSource();
            source.createDefaultDragGestureRecognizer(dragSource, DnDConstants.ACTION_COPY_OR_MOVE,
                    (DragGestureEvent dge) -> {
                        System.out.println("Drag started: " + dge);
                        try {
                            dge.startDrag(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR),
                                    new TransferableDummy());
                        } catch (InvalidDnDOperationException ex) {
                            dragDropException = ex;
                        }
                    });
            DropTarget target = new DropTarget(dropTarget, new DropTargetAdapter() {

                @Override
                public void drop(DropTargetDropEvent dtde) {
                    try {
                        System.out.println("Drop: " + dtde.getTransferable().getTransferData(DataFlavor.stringFlavor));
                    } catch (UnsupportedFlavorException | IOException ex) {
                        Logger.getLogger(SwingNodeDragDropApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            swingNode.setContent(root);
        });
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        SwingNode swingNode = new SwingNode();
        BorderPane pane = new BorderPane();

        createSwingContent(swingNode);
        pane.setCenter(swingNode);

        stage.setTitle("SwingNode DnD test");
        stage.setScene(new Scene(pane, 300, 300));
        stage.onCloseRequestProperty().addListener((Observable observable) -> {
            System.exit(0);
        });
        stage.show();
    }
}
