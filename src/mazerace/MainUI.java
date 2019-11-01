package mazerace;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import my_tools.MyColors;
import my_tools.MyFrame;
import opponents.AI_RandomPath;
import opponents.AI_RightHandMethod;
import opponents.AI_TremauxMethod;
import opponents.Opponent;

public class MainUI
{
    private static final String MOVE_UP = "UP", MOVE_LEFT = "LEFT", MOVE_DOWN = "DOWN", MOVE_RIGHT = "RIGHT";

    private final MyFrame mainFrame;
    private JPanel inputPanel, backgroundPanel, opponentPanel, opponentSpeedPanel;
    private Maze mazePanel;
    private JButton startButton, resetButton, deleteHighscoresButton;
    private MyActionListener myActionListener;
    private JSpinner widthSpinner, heightSpinner;
    private MyChangeListener myChangeListener;
    private JLabel widthLabel, heightLabel, highscoreLabel, opponentSpeedLabel, playerStepLabel, opponentStepLabel;
    private JCheckBox helptrackCheckBox;
    private JRadioButton opponentNoneRadioButton, opponentRandomPathRadioButton, opponentRightHandMethodRadioButton, opponentTremauxMethodRadioButton;
    private ButtonGroup opponentButtonGroup;
    private JSlider opponentSpeedSlider;
    private JScrollPane highscoreScrollPane;
    private JTable highscoreTable;
    private Highscore highscore;
    private Timer opponentTimer;

    public MainUI()
    {
        mainFrame = new MyFrame("MazeRace");
        inputPanel = new JPanel();
        backgroundPanel = new JPanel();
        opponentPanel = new JPanel();
        opponentSpeedPanel = new JPanel();
        startButton = new JButton("Start");
        resetButton = new JButton("Reset");
        deleteHighscoresButton = new JButton("<html><center>Highscores<br>löschen</center></html>");
        myChangeListener = new MyChangeListener();
        myActionListener = new MyActionListener();
        widthSpinner = new JSpinner();
        heightSpinner = new JSpinner();
        widthLabel = new JLabel("Breite");
        heightLabel = new JLabel("Höhe");
        helptrackCheckBox = new JCheckBox("Hilfsspur");
        opponentNoneRadioButton = new JRadioButton("<html>Kein<br>Gegner</html>");
        opponentRandomPathRadioButton = new JRadioButton("<html>Zufällige<br>Wegwahl</html>");
        opponentRightHandMethodRadioButton = new JRadioButton("<html>Rechte-<br>Hand-<br>Methode</html>");
        opponentTremauxMethodRadioButton = new JRadioButton("<html>Trémaux-<br>Methode</html>");
        opponentButtonGroup = new ButtonGroup();
        opponentSpeedSlider = new JSlider();
        opponentSpeedLabel = new JLabel("Mittel");
        playerStepLabel = new JLabel();
        opponentStepLabel = new JLabel();
        highscoreScrollPane = new JScrollPane();
        highscoreTable = new JTable();
        highscoreLabel = new JLabel();

        highscore = Highscore.getInstance();
        File f = new File((Highscore.FILENAME));
        if (f.exists())
        {
            highscore = highscore.load();
        }

        opponentTimer = new Timer(0, myActionListener);
    }

    void init()
    {
        mainFrame.setLayout(new BorderLayout());
        inputPanel.setLayout(null);
        opponentPanel.setLayout(new BoxLayout(opponentPanel, BoxLayout.Y_AXIS));
        opponentSpeedPanel.setLayout(new BoxLayout(opponentSpeedPanel, BoxLayout.Y_AXIS));

        mainFrame.add(inputPanel, BorderLayout.WEST);
        mainFrame.add(backgroundPanel, BorderLayout.CENTER);

        widthLabel.setBounds(12, 10, 100, 20);
        widthSpinner.setBounds(12, 30, 100, 20);
        heightLabel.setBounds(12, 60, 100, 20);
        heightSpinner.setBounds(12, 80, 100, 20);
        startButton.setBounds(12, 130, 100, 40);
        resetButton.setBounds(12, 130, 100, 40);
        helptrackCheckBox.setBounds(10, 230, 100, 20);
        opponentPanel.setBounds(12, 320, 100, 200);
        opponentSpeedPanel.setBounds(12, 520, 100, 80);
        playerStepLabel.setBounds(12, 630, 100, 20);
        opponentStepLabel.setBounds(12, 650, 100, 20);
        highscoreLabel.setBounds(12, 690, 100, 20);
        highscoreScrollPane.setBounds(12, 710, 100, 183);
        deleteHighscoresButton.setBounds(12, 900, 100, 40);

        opponentPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "CPU-Gegner", TitledBorder.CENTER, TitledBorder.DEFAULT_JUSTIFICATION));
        opponentSpeedPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "CPU-Tempo", TitledBorder.CENTER, TitledBorder.DEFAULT_JUSTIFICATION));

        opponentSpeedLabel.setHorizontalAlignment(SwingConstants.CENTER);

        helptrackCheckBox.setOpaque(false);
        opponentPanel.setOpaque(false);
        opponentSpeedPanel.setOpaque(false);
        opponentNoneRadioButton.setOpaque(false);
        opponentRandomPathRadioButton.setOpaque(false);
        opponentRightHandMethodRadioButton.setOpaque(false);
        opponentTremauxMethodRadioButton.setOpaque(false);
        opponentSpeedSlider.setOpaque(false);

        inputPanel.setPreferredSize(new Dimension(123, 0));

        inputPanel.setBackground(MyColors.mixColors(MyColors.GREEN, MyColors.BLUE));
        backgroundPanel.setBackground(MyColors.mixColors(MyColors.GREEN, MyColors.BLUE));

        Font font = playerStepLabel.getFont();
        font = font.deriveFont(font.getSize2D() / 1.15f);
        playerStepLabel.setFont(font);
        opponentStepLabel.setFont(font);

        resetButton.setVisible(false);
        playerStepLabel.setVisible(false);
        opponentStepLabel.setVisible(false);

        widthSpinner.setModel(new SpinnerNumberModel(10, 10, 90, 10));
        heightSpinner.setModel(new SpinnerNumberModel(10, 10, 90, 10));

        opponentSpeedSlider.setModel(new DefaultBoundedRangeModel(400, 0, 0, 600));
        opponentSpeedSlider.setMinorTickSpacing(200);
        opponentSpeedSlider.setPaintTicks(true);
        opponentSpeedSlider.setSnapToTicks(true);
        opponentSpeedSlider.setPaintTrack(false);
        opponentSpeedSlider.setInverted(true);

        highscoreScrollPane.setViewportView(highscoreTable);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        highscoreTable.setDefaultRenderer(String.class, centerRenderer);

        opponentPanel.add(opponentNoneRadioButton);
        opponentPanel.add(opponentRandomPathRadioButton);
        opponentPanel.add(opponentRightHandMethodRadioButton);
        opponentPanel.add(opponentTremauxMethodRadioButton);

        opponentSpeedPanel.add(opponentSpeedSlider);
        opponentSpeedPanel.add(opponentSpeedLabel);

        opponentButtonGroup.add(opponentNoneRadioButton);
        opponentButtonGroup.add(opponentRandomPathRadioButton);
        opponentButtonGroup.add(opponentRightHandMethodRadioButton);
        opponentButtonGroup.add(opponentTremauxMethodRadioButton);

        opponentNoneRadioButton.setSelected(true);

        inputPanel.add(widthLabel);
        inputPanel.add(widthSpinner);
        inputPanel.add(heightLabel);
        inputPanel.add(heightSpinner);
        inputPanel.add(startButton);
        inputPanel.add(resetButton);
        inputPanel.add(helptrackCheckBox);
        inputPanel.add(opponentPanel);
        inputPanel.add(opponentSpeedPanel);
        inputPanel.add(playerStepLabel);
        inputPanel.add(opponentStepLabel);
        inputPanel.add(highscoreLabel);
        inputPanel.add(highscoreScrollPane);
        inputPanel.add(deleteHighscoresButton);
        
        widthSpinner.addChangeListener(myChangeListener);
        heightSpinner.addChangeListener(myChangeListener);
        opponentSpeedSlider.addChangeListener(myChangeListener);
        startButton.addActionListener(myActionListener);
        resetButton.addActionListener(myActionListener);
        deleteHighscoresButton.addActionListener(myActionListener);

        for (Component c : inputPanel.getComponents())
        {
            c.setFocusable(false);
        }
        highscoreTable.setFocusable(false);
        highscoreTable.setRowSelectionAllowed(false);

        showHighscore();

        mainFrame.show(1100, 1000);
    }

    private void swapInput()
    {
        widthLabel.setEnabled(!widthLabel.isEnabled());
        heightLabel.setEnabled(!heightLabel.isEnabled());
        widthSpinner.setEnabled(!widthSpinner.isEnabled());
        heightSpinner.setEnabled(!heightSpinner.isEnabled());
        opponentPanel.setEnabled(!opponentPanel.isEnabled());
        opponentNoneRadioButton.setEnabled(!opponentNoneRadioButton.isEnabled());
        opponentRandomPathRadioButton.setEnabled(!opponentRandomPathRadioButton.isEnabled());
        opponentRightHandMethodRadioButton.setEnabled(!opponentRightHandMethodRadioButton.isEnabled());
        opponentTremauxMethodRadioButton.setEnabled(!opponentTremauxMethodRadioButton.isEnabled());
        opponentSpeedPanel.setEnabled(!opponentSpeedPanel.isEnabled());
        opponentSpeedSlider.setEnabled(!opponentSpeedSlider.isEnabled());
        opponentSpeedLabel.setEnabled(!opponentSpeedLabel.isEnabled());
        playerStepLabel.setVisible(!playerStepLabel.isVisible());
        opponentStepLabel.setVisible(!opponentStepLabel.isVisible());
        startButton.setVisible(!startButton.isVisible());
        resetButton.setVisible(!resetButton.isVisible());
    }

    private void showHighscore()
    {
        String type = String.format("%sx%s", widthSpinner.getValue(), heightSpinner.getValue());
        highscoreLabel.setText(String.format("Highscore %s", type));
        highscoreTable.setModel(new HighscoreTableModel(highscore.get(type)));
    }

    private void playerTurn(int directionX, int directionY)
    {
        int helptrack = helptrackCheckBox.isSelected() ? Player.HELPTRACK : Player.NO_HELPTRACK;
        Cell enteredCell = mazePanel.getPlayer().step(helptrack, directionX, directionY);
        playerStepLabel.setText(String.format("Züge Spieler: %,d", mazePanel.getPlayer().getStepCount()));

        if (enteredCell == mazePanel.getGoalCell())
        {
            opponentTimer.stop();
            mazePanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).clear();
            JOptionPane.showMessageDialog(mainFrame, String.format("%s Labyrinth in %,d Zügen gelöst.", mazePanel.toString(), mazePanel.getPlayer().getStepCount()), "Gewonnen", JOptionPane.INFORMATION_MESSAGE);
            swapInput();
            
            if (highscore.isHighscore(mazePanel.toString(), mazePanel.getPlayer().getStepCount()))
            {
                String name = JOptionPane.showInputDialog(mainFrame, "Highscore-Liste erreicht! Bitte Name eingeben (max 3 Zeichen)", "", JOptionPane.QUESTION_MESSAGE);
                if (name == null)
                {
                    return;
                }
                while (name.length() > 3)
                {
                    name = JOptionPane.showInputDialog(mainFrame, "Bitte Name eingeben (max 3 Zeichen)", "", JOptionPane.QUESTION_MESSAGE);
                    if (name == null)
                    {
                        return;
                    }
                }
                name = name.toUpperCase();
                highscore.add(mazePanel.toString(), mazePanel.getPlayer().getStepCount(), name);
                highscore.save();
                highscoreTable.setModel(new HighscoreTableModel(highscore.get(mazePanel.toString())));
            }
        }
    }

    private void opponentTurn()
    {
        Cell enteredCell = mazePanel.getOpponent().step();
        opponentStepLabel.setText(String.format("Züge CPU: %,d", mazePanel.getOpponent().getStepCount()));

        if (enteredCell == mazePanel.getGoalCell())
        {
            opponentTimer.stop();
            mazePanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).clear();

            if (highscore.isHighscore(mazePanel.toString(), mazePanel.getOpponent().getStepCount()))
            {
                highscore.add(mazePanel.toString(), mazePanel.getOpponent().getStepCount(), mazePanel.getOpponent().getOpponentName());
                highscore.save();
                highscoreTable.setModel(new HighscoreTableModel(highscore.get(mazePanel.toString())));
            }

            if (opponentSpeedSlider.getValue() != 0)
            {
                JOptionPane.showMessageDialog(mainFrame, String.format("Der CPU-Gegner war zuerst im Ziel und hat gewonnen. Benötigte Züge: %,d", mazePanel.getOpponent().getStepCount()), "Verloren", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(mainFrame, String.format("Benötigte Züge: %,d", mazePanel.getOpponent().getStepCount()), "Simulation beendet", JOptionPane.INFORMATION_MESSAGE);
            }
            swapInput();
        }
    }

    private class MyActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent ae)
        {
            if (ae.getSource() instanceof JButton)
            {
                JButton sender = (JButton) ae.getSource();
                if (sender == startButton)
                {
                    playerStepLabel.setText("Züge Spieler: 0");
                    opponentStepLabel.setText("Züge CPU: 0");

                    swapInput();

                    mazePanel = new Maze((int) widthSpinner.getValue(), (int) heightSpinner.getValue());

                    mazePanel.generate();

                    mazePanel.setOpponent(getSelectedOpponent());

                    mazePanel.setLayout(null);
                    mazePanel.setBackground(MyColors.mixColors(MyColors.GREEN, MyColors.BLUE));
                    mainFrame.add(mazePanel, BorderLayout.CENTER);

                    if (opponentSpeedSlider.getValue() != 0 || opponentNoneRadioButton.isSelected())
                    {
                        mazePanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), MOVE_UP);
                        mazePanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), MOVE_UP);
                        mazePanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), MOVE_LEFT);
                        mazePanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), MOVE_LEFT);
                        mazePanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), MOVE_DOWN);
                        mazePanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), MOVE_DOWN);
                        mazePanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), MOVE_RIGHT);
                        mazePanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), MOVE_RIGHT);

                        mazePanel.getActionMap().put(MOVE_UP, new MoveAction(0, -1));
                        mazePanel.getActionMap().put(MOVE_LEFT, new MoveAction(-1, 0));
                        mazePanel.getActionMap().put(MOVE_DOWN, new MoveAction(0, 1));
                        mazePanel.getActionMap().put(MOVE_RIGHT, new MoveAction(1, 0));
                    }

                    int maxW = mainFrame.getWidth() - inputPanel.getWidth();
                    int maxH = mainFrame.getHeight() - 20;
                    Cell.setSideLength(maxH);
                    while (Cell.getSideLength() * mazePanel.getMazeWidth() > maxW || Cell.getSideLength() * mazePanel.getMazeHeight() > maxH)
                    {
                        Cell.setSideLength(Cell.getSideLength() - 1);
                    }

                    mazePanel.draw(Cell.getSideLength());
                    mazePanel.requestFocus();

                    if (mazePanel.getOpponent() != null)
                    {
                        opponentTimer = new Timer(opponentSpeedSlider.getValue() + 20, myActionListener);
                        opponentTimer.start();
                    }
                }
                else if (sender == resetButton)
                {
                    swapInput();
                    opponentTimer.stop();
                    mainFrame.remove(mazePanel);
                    mainFrame.repaint();
                    mazePanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).clear();
                }
                else if (sender == deleteHighscoresButton)
                {
                    int result = JOptionPane.showConfirmDialog(mainFrame, "Highscore-Listen aller Labyrinthe löschen?", "", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION)
                    {
                        highscore.delete();
                        showHighscore();
                    }
                }
            }
            else if (ae.getSource() instanceof Timer)
            {
                Timer sender = (Timer) ae.getSource();
                if (sender == opponentTimer)
                {
                    opponentTurn();
                }
            }
        }

        private Opponent getSelectedOpponent()
        {
            int showPath = opponentSpeedSlider.getValue() == 0 ? Player.SIMULATION : Player.NO_HELPTRACK;
            if (opponentNoneRadioButton.isSelected())
            {
                return null;
            }
            else if (opponentRandomPathRadioButton.isSelected())
            {
                return new AI_RandomPath(mazePanel, showPath);
            }
            else if (opponentRightHandMethodRadioButton.isSelected())
            {
                return new AI_RightHandMethod(mazePanel, showPath);
            }
            else if (opponentTremauxMethodRadioButton.isSelected())
            {
                return new AI_TremauxMethod(mazePanel, showPath);
            }
            return null;
        }
    }

    private class MoveAction extends AbstractAction
    {
        int directionX, directionY;

        MoveAction(int directionX, int directionY)
        {
            this.directionX = directionX;
            this.directionY = directionY;
        }

        @Override
        public void actionPerformed(ActionEvent ae)
        {
            playerTurn(directionX, directionY);
        }
    }

    private class MyChangeListener implements ChangeListener
    {
        @Override
        public void stateChanged(ChangeEvent ce)
        {
            if (ce.getSource() instanceof JSpinner)
            {
                JSpinner sender = (JSpinner) ce.getSource();
                if (sender == widthSpinner || sender == heightSpinner)
                {
                    showHighscore();
                }
            }
            else if (ce.getSource() instanceof JSlider)
            {
                JSlider sender = (JSlider) ce.getSource();
                if (sender == opponentSpeedSlider)
                {

                    String speed = "";
                    int speedValue = sender.getValue();
                    if (speedValue <= 100)
                    {
                        speed = "SIMULATION";
                    }
                    else if (speedValue <= 300)
                    {
                        speed = "Schnell";
                    }
                    else if (speedValue <= 500)
                    {
                        speed = "Mittel";
                    }
                    else if (speedValue <= 700)
                    {
                        speed = "Langsam";
                    }
//                    else if (speedValue > 700 && speedValue <= 900)
//                    {
//                        speed = "Langsam";
//                    }
//                    else if (speedValue > 900)
//                    {
//                        speed = "Sehr langsam";
//                    }
                    opponentSpeedLabel.setText(speed);
                }
            }
        }
    }
}
