public class Outer {
    private int mem = 10;

    class Inner {
        private int imem = new Outer().mem; // ACCESS1
    }

    public static void main(String[] args) {
        new Outer().new Inner(); // ACCESS2
    }
}