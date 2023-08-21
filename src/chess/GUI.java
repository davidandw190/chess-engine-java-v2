package chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI implements ActionListener {

    private JFrame frame;
    private JPanel panel;
    private JFrame promotionSelector;
    private JPanel promotionPanel;
    private JFrame gameOver;

    private ArrayList<JButton> board;
    private ArrayList<JButton> promotionList;

    private static final Color LEGAL_MOVE_COLOR = new Color(10, 66, 64);
    private static final Color TILE_COLOR = new Color(44, 165, 141);
    private static final Color HIGHLIGHT_COLOR = new Color(50, 75, 116);

    public static final int BOARD_WIDTH = 8;

    public GUI() {
        initializeFrames();
        frame.setVisible(true);
    }

    private void initializeFrames() {
        frame = new JFrame();
        frame.setSize(730, 730);
        frame.setTitle("ChessMate my ass");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
