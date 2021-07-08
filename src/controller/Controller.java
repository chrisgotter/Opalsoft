package controller;


import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import access.ContactDataInterface;
import view.ContactDisplay;
import view.ContactForm;

public class Controller {
    private static ContactDataInterface data;
    public static void main(String... args) {
        data = new ContactDataInterface("contact.csv");
        javax.swing.SwingUtilities.invokeLater(()->createAndShowGUI());
    }
    /**
     * Create the GUI and show it. For thread safety, this method should be invoked
     * from the event-dispatching thread.
     */
    private static void createAndShowGUI() {
        Object[][] contacts = data.toArray();
        ContactDisplay frame = new ContactDisplay(contacts);
        Set<String> existingNames = new HashSet<>(contacts.length);
        for (Object[] contact : contacts) {
            existingNames.add(contact[1] + ", " + contact[0]);
        }
        setListeners(frame, existingNames);
        frame.setVisible(true);
    }


    private static void setListeners(ContactDisplay frame, Set<String> existingNames) {
        ContactForm formPane = frame.getFormPane(); 
        JTable table = frame.getTable();
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        ListSelectionModel selectionModel = table.getSelectionModel();
        table.getSelectionModel().addListSelectionListener((event) -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                formPane.setValues("", "", "", "");
                formPane.setNameEnabled(true);
                formPane.setRemoveEnabled(false);
            } else {
                String firstName = table.getValueAt(selectedRow, 0).toString();
                String lastName = table.getValueAt(selectedRow, 1).toString();
                String email = table.getValueAt(selectedRow, 2).toString();
                String phoneNumber = table.getValueAt(selectedRow, 3).toString();
                formPane.setValues(firstName, lastName, email, phoneNumber);
                formPane.setNameEnabled(false);
                formPane.setRemoveEnabled(true);
            }
        });
        formPane.setSaveButtonListener((e) -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {

                String fullName = formPane.getLastName() + ", " + formPane.getFirstName();
                if (existingNames.contains(fullName)) {
                    if (JOptionPane.showConfirmDialog(frame, "Would you like to edit this entry?", "Contact \"" + fullName + "\" has already been created.", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                        int rowCount = tableModel.getRowCount();
                        for (int i = 0; i < rowCount; i++) {
                            if (fullName.equals(tableModel.getValueAt(i, 1) + ", " + tableModel.getValueAt(i, 0))) {
                                String email = formPane.getEmail();
                                String phoneNumber = formPane.getPhoneNumber();
                                selectionModel.addSelectionInterval(i, i);
                                formPane.setValues(formPane.getFirstName(), formPane.getLastName(), email, phoneNumber);
                                break;
                            }
                        }
                    }
                } else {
                    tableModel.addRow(new Object[] { formPane.getFirstName(), formPane.getLastName(),
                            formPane.getEmail(), formPane.getPhoneNumber() });
                    selectionModel.addSelectionInterval(table.getRowCount() - 1, table.getRowCount() - 1);
                    formPane.setNameEnabled(false);
                    formPane.setRemoveEnabled(true);
                    existingNames.add(fullName);
                }

            } else {
                table.setValueAt(formPane.getFirstName(), selectedRow, 0);
                table.setValueAt(formPane.getLastName(), selectedRow, 1);
                table.setValueAt(formPane.getEmail(), selectedRow, 2);
                table.setValueAt(formPane.getPhoneNumber(), selectedRow, 3);
            }
        });
        formPane.setNewButtonListener((e) -> {
            formPane.setValues("", "", "", "");
            formPane.setNameEnabled(true);
            formPane.setRemoveEnabled(false);
            selectionModel.clearSelection();
        });
        formPane.setRemoveButtonListener((e) -> {
            formPane.setValues("", "", "", "");
            formPane.setNameEnabled(true);
            formPane.setRemoveEnabled(false);
            int selectedRow = table.getSelectedRow();
            tableModel.removeRow(selectedRow);
            selectionModel.clearSelection();
        });
        table.getRowCount();
        formPane.setPersistButtonListener((e) -> {
            data.persist(tableToArray(tableModel));
            JOptionPane.showMessageDialog(frame,"Your changes have been synced to contact.csv", "Contacts Persisted.", JOptionPane.INFORMATION_MESSAGE);
        });
    }
    private static Object[][] tableToArray(TableModel model) {
        int rowCount = model.getRowCount();
        int columnCount = model.getColumnCount();
        Object[][] result = new Object[rowCount][columnCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                result[i][j] = model.getValueAt(i, j);
            }
        }
        return result;
    }
}
