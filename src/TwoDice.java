public class TwoDice {
    private int firstRoll = 0;
    private int secondRoll = 0;

    public  TwoDice(){}

    public boolean isDouble () {
        if (firstRoll != secondRoll) {return false;}
        else {return true;}
    }

    public int dieSum() {
        int sum = firstRoll + secondRoll;
        return sum;
    }

    public void roll () {
        firstRoll = 1 + (int) (Math.random() * 6);
        secondRoll = 1 + (int) (Math.random() * 6);
    }
}