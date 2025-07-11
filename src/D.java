public class D {
    private int val;
    public D(int val) {
        this.val= val;
    }
}
class E extends D{
    public E(int val) {
        super(val);
    }


    public static void main(String[] args){
        D[] t1 = new E[4];
        t1[0] = new D(1);
        t1[1] = new E(3);

    }





}