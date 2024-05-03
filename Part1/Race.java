

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;




import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Race extends JFrame {
    private JPanel panelTrack;
    private JLabel[] labelsHorse;
    private JLabel labelWinner;
    private List<Horse> equines;
    private JButton buttonStart;
    private int distanceTrack; // Determined by the window width
    private int countEquines = 2; // Default number of equines

    public Race() {
        setTitle("Equine Dash");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
    
        panelTrack = new JPanel(new GridLayout(0, 1));
        panelTrack.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(panelTrack, BorderLayout.CENTER);
    
        setupRace();
    
        initializeEquines();
    
        labelWinner = new JLabel("", SwingConstants.CENTER);
        add(labelWinner, BorderLayout.NORTH);
    
        buttonStart = new JButton("Start the Dash");
        buttonStart.addActionListener(e -> executeRace());
        add(buttonStart, BorderLayout.SOUTH);
    
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void modifyWindowSize(int width) {
        panelTrack.setPreferredSize(new Dimension(width, 600));
        panelTrack.setLayout(new GridLayout(countEquines, 1));
        panelTrack.revalidate();
        panelTrack.repaint();
        pack();
    }
    
    private void setupRace() {
        JTextField fieldCountEquines = new JTextField("" + countEquines);
        JTextField fieldDistanceTrack = new JTextField("900");
    
        JPanel setupPanel = new JPanel(new GridLayout(0, 1));
        setupPanel.add(new JLabel("Number of Equines:"));
        setupPanel.add(fieldCountEquines);
        setupPanel.add(new JLabel("Track Distance (in pixels):"));
        setupPanel.add(fieldDistanceTrack);
    
        int choice = JOptionPane.showConfirmDialog(null, setupPanel, "Race Setup",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    
        if (choice == JOptionPane.OK_OPTION) {
            countEquines = Integer.parseInt(fieldCountEquines.getText());
            distanceTrack = Integer.parseInt(fieldDistanceTrack.getText());
            modifyWindowSize(distanceTrack);
        }
    }
    
    private void initializeEquines() {
        equines = new ArrayList<>();
        labelsHorse = new JLabel[countEquines];
        for (int i = 0; i < labelsHorse.length; i++) {
            equines.add(new Horse("Equine " + (i + 1)));
            labelsHorse[i] = new JLabel();
            labelsHorse[i].setHorizontalAlignment(SwingConstants.CENTER);
            panelTrack.add(labelsHorse[i]);
        }
    }

    private void executeRace() {
        buttonStart.setEnabled(false);
        labelWinner.setText("");

        Thread raceExecution = new Thread(() -> {
            boolean finished = false;
            while (!finished) {
                equines.forEach(Horse::advance);
                refreshTrack();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (Horse equine : equines) {
                    if (equine.getPosition() >= distanceTrack) {
                        labelWinner.setText("Winner: " + equine.getName());
                        finished = true;
                        break;
                    }
                }
            }
            SwingUtilities.invokeLater(this::offerReplay);
        });
        raceExecution.start();
    }

    private void refreshTrack() {
        for (int i = 0; i < equines.size(); i++) {
            Horse equine = equines.get(i);
            int progress = (int) ((double) equine.getPosition() / distanceTrack * (panelTrack.getWidth() - 100));
            String trackMark = "-".repeat(Math.max(0, progress));
            labelsHorse[i].setText(trackMark + equine.getSymbol() + " | " + equine.getName());
        }
    }

    private void offerReplay() {
        int replay = JOptionPane.showConfirmDialog(null, "The dash is complete. Dash again?",
                                                      "Replay", JOptionPane.YES_NO_OPTION);
        if (replay == JOptionPane.YES_OPTION) {
            resetRace();
        } else {
            buttonStart.setEnabled(false);
        }
    }

    private void resetRace() {
        equines.forEach(Horse::resetPosition);
        labelWinner.setText("");
        buttonStart.setEnabled(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Race::new);
    }
}
