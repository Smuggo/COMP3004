package view.internals.game;

import javax.swing.DefaultListSelectionModel;
import javax.swing.ListSelectionModel;

// Users can only select one row from a JTable
// This is used for buying and selling in magic realm
// Implemented by Zachary
// Source: http://stackoverflow.com/questions/18309113/jtable-how-to-force-user-to-select-exactly-one-row

public class ForcedListSelectionModel extends DefaultListSelectionModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1376286997484054917L;

	public ForcedListSelectionModel () {
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    @Override
    public void clearSelection() {
    }

    @Override
    public void removeSelectionInterval(int index0, int index1) {
    }

}