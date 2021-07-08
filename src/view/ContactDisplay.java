package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class ContactDisplay extends JFrame {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTable table;
    private ContactForm formPane;

    public ContactDisplay(Object[][] contacts) {
        // Create and set up the window.
        super("Contact Editor");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        formPane = new ContactForm();

        JPanel tablePane = new JPanel(new GridLayout(1, 0));
        DefaultTableModel model = new DefaultTableModel(contacts,
                new Object[] { "First Name", "Last Name", "Phone Number", "Email Address" }) {

            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                // all cells false
                return false;
            }
        };
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(800, 500));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add the scroll pane to this panel.
        tablePane.add(new JScrollPane(table));

        JPanel formWrapper = new JPanel(new FlowLayout());
        formWrapper.add(formPane);
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formWrapper, tablePane);

        split.setOpaque(true); // content panes must be opaque
        this.setContentPane(split);

        // Display the window.
        this.pack();
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public ContactForm getFormPane() {
        return formPane;
    }

    public void setFormPane(ContactForm formPane) {
        this.formPane = formPane;
    }

}
