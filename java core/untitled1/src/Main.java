public class Main {
    public static void main(String[] args) {


        System.out.println(getAgeDiff((byte) 10, (byte) 2));
        System.out.println(getAgeDiff((byte) 5, (byte) 8));
        System.out.println(getAgeDiff((byte) 30, (byte) 10));
    }

    public static byte getAgeDiff(byte age1, byte age2) {
        if (age1 > age2) {
            return (byte) (age1 - age2);
        } else {
            return (byte) (age2 - age1);
        }


    }
}
