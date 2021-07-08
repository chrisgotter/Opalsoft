package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ContactForm extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JFormattedTextField emailField;
    private JFormattedTextField phoneNumberField;
    private JButton newButton;
    private JButton removeButton;
    private JButton saveButton;
    private JButton persistButton;
    public ContactForm() {
        super(new BorderLayout());
        newButton = new JButton("New");
        removeButton = new JButton("Remove");
        removeButton.setEnabled(false);
        saveButton = new JButton("Save");
        persistButton = new JButton("Persist");
        //Create the labels.
        JLabel firstNameLabel = new JLabel("First Name");
        JLabel lastNameLabel = new JLabel("Last Name");
        JLabel emailLabel = new JLabel("Email Address");
        JLabel phoneLabel = new JLabel("Phone Number");
 
        //Create the text fields and set them up.
        firstNameField = new JTextField();
        firstNameField.setColumns(30);
 
        lastNameField = new JTextField();
        lastNameField.setColumns(30);
 
        emailField = new JFormattedTextField();
        emailField.setColumns(30);
 
        phoneNumberField = new JFormattedTextField();
        phoneNumberField.setColumns(30);
 
        //Tell accessibility tools about label/textfield pairs.
        firstNameLabel.setLabelFor(firstNameField);
        lastNameLabel.setLabelFor(lastNameField);
        emailLabel.setLabelFor(emailField);
        phoneLabel.setLabelFor(phoneNumberField);
 
        //Lay out the labels in a panel.
        JPanel labelPane = new JPanel(new GridLayout(0,1));
        labelPane.add(newButton);
        labelPane.add(firstNameLabel);
        labelPane.add(lastNameLabel);
        labelPane.add(emailLabel);
        labelPane.add(phoneLabel);
        labelPane.add(saveButton);
 
        //Layout the text fields in a panel.
        JPanel fieldPane = new JPanel(new GridLayout(0,1));
        fieldPane.add(removeButton);
        fieldPane.add(firstNameField);
        fieldPane.add(lastNameField);
        fieldPane.add(emailField);
        fieldPane.add(phoneNumberField);
        fieldPane.add(persistButton);
        //Put the panels in this panel, labels on left,
        //text fields on right.
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(labelPane, BorderLayout.CENTER);
        add(fieldPane, BorderLayout.LINE_END);
    }
    public void setNewButtonListener(ActionListener listener) {
        newButton.addActionListener(listener);
    }
    public void setSaveButtonListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }
    public void setRemoveButtonListener(ActionListener listener) {
        removeButton.addActionListener(listener);
    }
    public void setPersistButtonListener(ActionListener listener) {
        persistButton.addActionListener(listener);
    }
    public String getFirstName() {
        return firstNameField.getText();
    }
    public String getLastName() {
        return lastNameField.getText();
    }
    public String getEmail() {
        return emailField.getText();
    }
    public String getPhoneNumber() {
        return phoneNumberField.getText();
    }
    public void setValues(String firstName, String lastName, String email, String phoneNumber) {
        firstNameField.setText(firstName);
        lastNameField.setText(lastName);
        emailField.setText(email);
        phoneNumberField.setText(phoneNumber);
    }
    public void setNameEnabled(boolean enabled) {
        firstNameField.setEnabled(enabled);
        lastNameField.setEnabled(enabled);
    }
    public void setRemoveEnabled(boolean enabled) {
        removeButton.setEnabled(enabled);
    }
}
