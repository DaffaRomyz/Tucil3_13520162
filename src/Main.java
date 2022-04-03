public class Main {
    public static void main(String[] args) {
        Matriks m1 = new Matriks("1.txt");
        m1.printMatriks();
        if (m1.reachableGoal()) {
            System.out.println("OK");
        } else {
            System.out.println("NOK");
        }
        System.out.println("END");
    }
}
