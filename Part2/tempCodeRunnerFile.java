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