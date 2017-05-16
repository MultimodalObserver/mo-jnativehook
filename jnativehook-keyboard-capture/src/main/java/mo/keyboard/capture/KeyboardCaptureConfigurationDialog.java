package mo.keyboard.capture;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import mo.core.ui.GridBConstraints;
import mo.core.ui.Utils;
import mo.organization.ProjectOrganization;

public class KeyboardCaptureConfigurationDialog 
        extends JDialog implements DocumentListener {

    JLabel errorLabel;
    JTextField nameField;
    JButton accept;

    ProjectOrganization org;

    boolean accepted = false;

    public KeyboardCaptureConfigurationDialog() {
        super(null, "Keyboard Capture Configuration", Dialog.ModalityType.APPLICATION_MODAL);
    }

    public KeyboardCaptureConfigurationDialog(ProjectOrganization organization) {
        super(null, "Keyboard Capture Configuration", Dialog.ModalityType.APPLICATION_MODAL);
        org = organization;
    }

    public boolean showDialog() {

        setLayout(new GridBagLayout());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                accepted = false;
                super.windowClosing(e);
            }
        });

        setLayout(new GridBagLayout());
        GridBConstraints gbc = new GridBConstraints();

        JLabel label = new JLabel("Configuration name: ");
        nameField = new JTextField();
        nameField.getDocument().addDocumentListener(this);

        gbc.gx(0).gy(0).f(GridBConstraints.HORIZONTAL)
                .a(GridBConstraints.FIRST_LINE_START)
                .i(new Insets(5, 5, 5, 5));

        add(label, gbc);
        //add(path, gbc.gx(1));
        add(nameField, gbc.gx(2).wx(1));

        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.red);
        add(errorLabel, gbc.gx(0).gy(2).gw(3).a(GridBConstraints.LAST_LINE_START).wy(1));

        accept = new JButton("Accept");
        accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accepted = true;
                setVisible(false);
                dispose();
            }
        });

        gbc.gx(0).gy(3).a(GridBConstraints.LAST_LINE_END).gw(3).wy(1).f(GridBConstraints.NONE);
        add(accept, gbc);

        setMinimumSize(new Dimension(400, 150));
        setPreferredSize(new Dimension(400, 300));
        pack();
        Utils.centerOnScreen(this);
        updateState();
        setVisible(true);

        return accepted;
    }

    public static void main(String[] args) {
        ProjectOrganization o = new ProjectOrganization("C:\\Users\\Celso\\Desktop\\example");
        KeyboardCaptureConfigurationDialog c = new KeyboardCaptureConfigurationDialog(o);
        
        boolean i = c.showDialog();
        System.exit(0);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        updateState();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        updateState();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        updateState();
    }

    private void updateState() {
        if (nameField.getText().isEmpty()) {
            errorLabel.setText("A name for this configuration must be specified");
            accept.setEnabled(false);
        } else {
            errorLabel.setText("");
            accept.setEnabled(true);
        }
    }

    public String getConfigurationName() {
        return nameField.getText();
    }
}

