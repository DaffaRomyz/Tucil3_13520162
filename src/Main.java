import java.util.ArrayList;
import java.util.Objects;
import java.util.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        // memilih puzzle awal
        System.out.println("Pilih Masukan :");
        System.out.println("[1] Acak");
        System.out.println("[2] File");
        Scanner scaninput = new Scanner(System.in);
        System.out.print(">>> ");
        int in = scaninput.nextInt();
        Matriks R;

        // inisiasi simpul awal R
        Boolean input = false;
        do {
            if (in == 1) {
                System.out.println("Pilih Acak");
                R = new Matriks();
                input = true;
            } 
            else if (in == 2) {
                System.out.println("Pilih File");
                System.out.println("Masukan Nama File:");
                System.out.print(">>> ");
                String filename = scaninput.next();
                String F = ("..\\test\\" + filename);
                File file = new File(F);
                while (!file.exists()) {
                    System.out.println("File tidak ada. Ulangi !");
                    System.out.println("Masukan Nama File:");
                    System.out.print(">>> ");
                    filename = scaninput.next();
                    F = ("..\\test\\" + filename);
                    file = new File(F);
                }
                R = new Matriks(file); 
                input = true;
            }
            else {
                R = new Matriks();
                System.out.println("Masukan Salah, Ulangi !");
                System.out.println("Pilih Masukan :");
                System.out.println("[1] Acak");
                System.out.println("[2] File");
                System.out.print(">>> ");
                in = scaninput.nextInt();
                
            }
        } while (!input);

        scaninput.close();

        // memulai waktu
        long start = System.currentTimeMillis();
        R.printMatriks();

        // menentukan apakah puzzle dapat diselesaikan
        if (!R.reachableGoal()) {
            System.out.println("Persoalan Tidak Dapat Diselesaikan");
            System.exit(0);
        }

        // menentukan apalah puzzle awal sudah mencapai status akhir
        if (R.goalState()) {
            System.out.println("Solusi Ditemukan");
            System.out.println("Urutan Matriks :");
            R.printMatriks();
        }

        // list untuk simpul hidup
        ArrayList<Matriks> Hidup = new ArrayList<Matriks>();

        // list untuk simpul solusi
        ArrayList<Matriks> Solusi = new ArrayList<Matriks>();

        // memasukan puzzle awal ke list simpul hidup
        Hidup.add(R);

        Boolean found = false;

        // inisiasi jumlah d=simpul yang telah dibangkitkan
        int jumlah = 1;

        // membangkitkan simpul-simpul valid dari simpul R
        while (!found) {
            // mengeluarkan simpul R dari list simpul hidup
            Hidup.remove(R);

            // membangkitkan simpul untuk aksi atas
            Matriks MU = R.Move("up");
            if (!Objects.isNull(MU)) {
                jumlah++;
                Hidup.add(MU);
            }

            // membangkitkan simpul untuk aksi kanan
            Matriks MR = R.Move("right");
            if (!Objects.isNull(MR)) {
                jumlah++;
                Hidup.add(MR);
            }

            // membangkitkan simpul untuk aksi bawah
            Matriks MD = R.Move("down");
            if (!Objects.isNull(MD)) {
                jumlah++;
                Hidup.add(MD);
            }

            // membangkitkan simpul untuk aksi kiri
            Matriks ML = R.Move("left");
            if (!Objects.isNull(ML)) {
                jumlah++;
                Hidup.add(ML);
            }

            // mencari simpul yang memiliki cost terkecil dan menjadikanya simpul R baru
            R = Hidup.get(0);
            for (int i = 1; i < Hidup.size(); i++) {
                if (Hidup.get(i).getC() < R.getC()) {
                    R = Hidup.get(i);
                }
            }

            // menentukan jika simpul R sudah mencapai status akhir
            if (R.goalState()) {
                found = true;
            }
        }

        // memasukan simpul awal sampai simpul akhir kedalam list simpul solusi
        Solusi.add(R);
        R = R.getParent();
        while (!Objects.isNull(R)) {
            Solusi.add(R);
            R = R.getParent();
        }

        // menampilkan urutan matriks dari posisi awal ke posisi akhir
        Collections.reverse(Solusi);
        System.out.println("Solusi Ditemukan");
        System.out.println("Urutan Matriks :");
        for (Matriks m : Solusi) {
            m.printMatriks();
        }

        // mengakhiri waktu
        long stop = System.currentTimeMillis();

        // menampilkan waktu eksekusi
        System.out.println("Execution Time : " + (stop - start) + " ms\n");

        // menampilkan jumlah simpul yang dibangkitkan
        System.out.printf("Jumlah Matriks yang Dibangkitkan : %d\n", jumlah);
    }
}
