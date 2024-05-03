


import java.util.Random;




class Horse {
    private String name;
    private int position;
    private double morale;
    private static final int MAX_STRIDE = 3;
    private static final Random rand = new Random();

    public Horse(String name) {
        this.name = name;
        this.position = 0;
        this.morale = 0.5;
    }

    public void advance() {
        int stride = rand.nextInt(MAX_STRIDE) + 1;
        position += stride;
        updateMorale();
    }

    private void updateMorale() {
        morale += (rand.nextDouble() * 0.2) - 0.1;
        morale = Math.max(0, Math.min(morale, 1));
    }

    public int getPosition() {
        return position;
    }

    public double getMorale() {
        return morale;
    }

    public String getName() {
        return name;
    }

    public void resetPosition() {
        position = 0;
    }

    public String getSymbol() {
        return "\uD83D\uDC0E";
    }
}