package it.unibo.oop.lab.mvc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUI {

    private final JFrame frame = new JFrame();
    private final Controller controller;

    /*
     * Once the Controller is done, implement this class in such a way that:
     * 
     * 1) I has a main method that starts the graphical application
     * 
     * 2) In its constructor, sets up the whole view
     * 
     * 3) The graphical interface consists of a JTextField in the upper part of the frame, 
     * a JTextArea in the center and two buttons below it: "Print", and "Show history". 
     * SUGGESTION: Use a JPanel with BorderLayout
     * 
     * 4) By default, if the graphical interface is closed the program must exit
     * (call setDefaultCloseOperation)
     * 
     * 5) The behavior of the program is that, if "Print" is pressed, the
     * controller is asked to show the string contained in the text field on standard output. 
     * If "show history" is pressed instead, the GUI must show all the prints that
     * have been done to this moment in the text area.
     * 
     */

    private void arrangeComponents() {
        final JPanel mainPanel = new JPanel(new BorderLayout());
        this.frame.setContentPane(mainPanel);
        final JTextField field = new JTextField();
        mainPanel.add(field, BorderLayout.NORTH);
        final JTextArea area = new JTextArea();
        area.setEditable(false);
        mainPanel.add(area);

        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        final JButton bPrint = new JButton("Print");
        final JButton bHistory = new JButton("Show history");
        buttonPanel.add(bPrint);
        buttonPanel.add(bHistory);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        /*
         * Events
         */
        bPrint.addActionListener(l -> {
            if (!field.getText().isBlank()) {
                this.controller.setNextString(field.getText());
                this.controller.printNextString();
            } else {
                JOptionPane.showMessageDialog(this.frame, "No text to print!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        bHistory.addActionListener(l -> {
            if (!controller.getHistory().isEmpty()) {
                area.setText("");
                this.controller.getHistory().stream()
                                            .map(s -> s + "\n")
                                            .forEach(area::append);
            } else {
                JOptionPane.showMessageDialog(this.frame,
                                    "History is empty!",
                                    "No history",
                                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    /**
     * Builds a new {@link SimpleGUI}.
     * 
     * @param contr
     *              {@link Controller} for the GUI.
     */
    public SimpleGUI(final Controller contr) {

        /*
         * Make the frame half the resolution of the screen. This very method is
         * enough for a single screen setup. In case of multiple monitors, the
         * primary is selected.
         * 
         * In order to deal coherently with multimonitor setups, other
         * facilities exist (see the Java documentation about this issue). It is
         * MUCH better than manually specify the size of a window in pixel: it
         * takes into account the current resolution.
         */
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        this.frame.setSize(sw / 2, sh / 2);

        /*
         * Instead of appearing at (0,0), upper left corner of the screen, this
         * flag makes the OS window manager take care of the default positioning
         * on screen. Results may vary, but it is generally the best choice.
         */
        this.frame.setLocationByPlatform(true);

        /*
         * Assigning the controller passed on as argument.
         */
        this.controller = contr;

        /*
         * Lays out the various components as per instructions.
         */
        this.arrangeComponents();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
    }

    public static void main(final String... args) {
        new SimpleGUI(new ControllerImpl());
    }
}
