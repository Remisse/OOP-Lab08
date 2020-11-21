package it.unibo.oop.lab.mvcio2;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import it.unibo.oop.lab.mvcio.Controller;
import it.unibo.oop.lab.mvcio.SimpleGUI;

/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUIWithFileChooser {

    /*
     * TODO: Starting from the application in mvcio:
     * 
     * 1) Add a JTextField and a button "Browse..." on the upper part of the
     * graphical interface.
     * Suggestion: use a second JPanel with a second BorderLayout, put the panel
     * in the North of the main panel, put the text field in the center of the
     * new panel and put the button in the line_end of the new panel.
     * 
     * 2) The JTextField should be non modifiable. And, should display the
     * current selected file.
     * 
     * 3) On press, the button should open a JFileChooser. The program should
     * use the method showSaveDialog() to display the file chooser, and if the
     * result is equal to JFileChooser.APPROVE_OPTION the program should set as
     * new file in the Controller the file chosen. If CANCEL_OPTION is returned,
     * then the program should do nothing. Otherwise, a message dialog should be
     * shown telling the user that an error has occurred (use
     * JOptionPane.showMessageDialog()).
     * 
     * 4) When in the controller a new File is set, also the graphical interface
     * must reflect such change. Suggestion: do not force the controller to
     * update the UI: in this example the UI knows when should be updated, so
     * try to keep things separated.
     */

    private final SimpleGUI gui;

    public SimpleGUIWithFileChooser() {
        this.gui = new SimpleGUI();
        final Container mainPanel = this.gui.getFrame().getContentPane();
        final Controller contr = this.gui.getController();
        final JPanel nPanel = new JPanel(new BorderLayout());
        final JTextField text1 = new JTextField();
        text1.setEditable(false);
        text1.setText(contr.getPathOfCurrentFile());
        nPanel.add(text1);
        final JButton bBrowse = new JButton("Browse...");
        nPanel.add(bBrowse, BorderLayout.LINE_END);
        mainPanel.add(nPanel, BorderLayout.NORTH);
        /*
         * Events
         */
        bBrowse.addActionListener(l -> {
            final JFileChooser chooser = new JFileChooser();
            final int result = chooser.showOpenDialog(this.gui.getFrame());
            if (result == JFileChooser.APPROVE_OPTION) {
                contr.setCurrentFile(chooser.getSelectedFile());
                text1.setText(chooser.getSelectedFile().getAbsolutePath());
            } else if (result != JFileChooser.CANCEL_OPTION) {
                JOptionPane.showMessageDialog(this.gui.getFrame(), "An error has occurred.");
            }
        });
    }

    public static void main(final String... args) {
        new SimpleGUIWithFileChooser();
    }
}
