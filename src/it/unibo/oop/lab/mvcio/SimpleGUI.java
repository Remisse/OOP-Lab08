package it.unibo.oop.lab.mvcio;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

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
     * 1) It has a main method that starts the graphical application
     * 
     * 2) In its constructor, sets up the whole view
     * 
     * 3) The graphical interface consists of a JTextArea with a button "Save" right
     * below (see "ex02.png" for the expected result). SUGGESTION: Use a JPanel with
     * BorderLayout
     * 
     * 4) By default, if the graphical interface is closed the program must exit
     * (call setDefaultCloseOperation)
     * 
     * 5) The program asks the controller to save the file if the button "Save" gets
     * pressed.
     * 
     * Use "ex02.png" (in the res directory) to verify the expected aspect.
     */

    public JFrame getFrame() {
        return frame;
    }

    public Controller getController() {
        return controller;
    }

    private void arrangeComponents() {
        final JPanel mainPanel = new JPanel(new BorderLayout());
        final JTextArea textArea = new JTextArea();
        final JButton bSave = new JButton("Save");
        mainPanel.add(textArea);
        mainPanel.add(bSave, BorderLayout.SOUTH);
        this.frame.setContentPane(mainPanel);

        bSave.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                try {
                    SimpleGUI.this.controller.writeOnCurrentFile(textArea.getText());
                } catch (IOException exception) {
                    JOptionPane.showMessageDialog(SimpleGUI.this.frame, exception, "Error", JDialog.EXIT_ON_CLOSE);
                    exception.printStackTrace();
                }
            }
        });
        /*
        bSave.addActionListener(e -> {
            try {
                this.controller.writeOnCurrentFile(textArea.getText());
            } catch (IOException exception) {
                JOptionPane.showMessageDialog(this.frame, exception, "Error", JDialog.EXIT_ON_CLOSE);
                exception.printStackTrace();
            }
        });
        */
    }

    /**
     * builds a new {@link SimpleGUI}.
     */
    public SimpleGUI() {
        /*
         * Creating a new Controller.
         */
        this.controller = new Controller();
        /*
         * Laying out the various GUI components as per instructions.
         */
        this.arrangeComponents();
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
         * Will exit without confirmation from the user.
         */
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /**
         * Displays the application window.
         */
        this.frame.setVisible(true);
    }

    public static void main(final String... args) {
        new SimpleGUI();
    }
}
