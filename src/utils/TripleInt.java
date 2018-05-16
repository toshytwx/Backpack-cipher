package utils;

public class TripleInt {
    private Integer d;
    private Integer x;
    private Integer y;

    public TripleInt(Integer first, Integer second, Integer third) {
        d = first;
        x = second;
        y = third;
    }

    public  TripleInt() {}

    public TripleInt getGCD(Integer a, Integer b) {
        TripleInt temphere = new TripleInt(a, 1, 0);
        TripleInt temphere2;

        if (b == 0) {
            return temphere;
        }

        temphere2 = getGCD(b, a % b);
        temphere = new TripleInt();

        temphere.d = temphere2.d;
        temphere.x = temphere2.y;
        temphere.y = temphere2.x - (a / b) * temphere2.y;

        return temphere;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getD() {
        return d;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }
}