import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

class Matriks {
    private int m[][]; // status puzzle
    private String prev; // aksi sebelumnya
    private int c; // cost
    private Matriks parent; // simpul parent

    // contructor untuk puzzle acak
    public Matriks() {
        this.m = new int[4][4];
        this.prev = "none";
        this.parent = null;
        ArrayList<Integer> a = new ArrayList<Integer>();
        for (int i = 0; i <16 ; i++) {
            a.add(i+1);
        } 
        int b = new Random().nextInt(16);
        int k = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.m[i][j] = a.get(b);
                a.remove(b);
                k++;
                if (b > 1) {
                b = new Random().nextInt(16 - k);
                } else {
                    b = 0;
                }
            }
        }
        this.c = this.g() + this.f();
    }

    // constructor untuk puzzle dari file teks
    public Matriks(File file) {
        try {
            this.m = new int[4][4];
            this.prev = "none";
            this.parent = null;
            Scanner scan = new Scanner(file);
            int i = 0;
            int j = 0;
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] numbers = line.split(" ");
                for (String number : numbers) {
                    if (j > 3) {
                        j = 0;
                        i++;
                    }
                    this.m[i][j] = Integer.parseInt(number);
                    j++;
                }
            }
            scan.close();
            this.c = this.g() + this.f();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        }
    }

    // copy constructor
    public Matriks(Matriks M) {
        this.m = new int[4][4];
        this.prev = "none";
        this.parent = M.parent;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.m[i][j] = M.m[i][j];
            }
        }
        this.c = this.g() + this.f();;
    }

    // membangkitkan simpul baru dengan aksi atas, kanan, bawah, kiri
    public Matriks Move(String dir) {
        Matriks Mat = new Matriks(this);
        Mat.parent = this;
        int[] point = Mat.findBlank();
        if (dir.equals("up") && point[0] != 0 && Mat.prev != "up") {
            Mat.m[point[0]][point[1]] = Mat.m[point[0] - 1][point[1]];
            Mat.m[point[0] - 1][point[1]] = 16;
            Mat.prev = "up";
            Mat.c = Mat.g() + Mat.f();
            return Mat;
        }
        else if (dir.equals("right") && point[1] != 3 && Mat.prev != "right") {
            Mat.m[point[0]][point[1]] = Mat.m[point[0]][point[1] + 1];
            Mat.m[point[0]][point[1] + 1] = 16;
            Mat.prev = "right";
            Mat.c = Mat.g() + Mat.f();
            return Mat;
        }
        else if (dir.equals("down") && point[0]!= 3 && Mat.prev != "down") {
            Mat.m[point[0]][point[1]] = Mat.m[point[0] + 1][point[1]];
            Mat.m[point[0] + 1][point[1]] = 16;
            Mat.prev = "down";
            Mat.c = Mat.g() + Mat.f();
            return Mat;
        }
        else if (dir.equals("left") && point[1] != 0 && Mat.prev != "left") {
            Mat.m[point[0]][point[1]] = Mat.m[point[0]][point[1] - 1];
            Mat.m[point[0]][point[1] - 1] = 16;
            Mat.prev = "left";
            Mat.c = Mat.g() + Mat.f();
            return Mat;
        } else {
            return null;
        }
    }

    // mencari posisi ubin kosong
    public int[] findBlank() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (this.m[i][j] == 16) {
                    int point[] = new int[2];
                    point[0] = i;
                    point[1] = j; 
                    return point;
                }
            }
        }
        return null;
    }

    // menampilkan matriks puzzle
    public void printMatriks() {
        for (int i = 0; i < 4; i++) {
            System.out.printf("%n-----------------%n");
            System.out.printf("|");
            for (int j = 0; j < 4; j++) {
                if (this.m[i][j] == 16) {
                    System.out.printf("   ");
                }
                else if (this.m[i][j] < 10) {
                    System.out.printf("%d  ", this.m[i][j]);
                } else {
                    System.out.printf("%d ", this.m[i][j]);
                }
                System.out.printf("|");

            }
            
        }
        System.out.printf("%n-----------------%n");
        
    }

    // menentukan apakah puzzle dapt diselesaikan dengan fungsi sigma(i=1,16) kurang(i) + X
    // fungsi kurang() untuk ubin kosong juga ikut dihitung
    public Boolean reachableGoal() {
        int kurang = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                kurang += this.kurang(i, j);
            }
        }
        int[] point = this.findBlank();
        if ((point[0] + point[1]) % 2 == 1) {
            kurang++;
        }
        System.out.printf("Total Fungsi Kurang adalah %d\n", kurang);
        return kurang % 2 == 0;
    }

    // fungsi kurang()
    public int kurang(int x, int y) {
        int kurang = 0;
        for (int i = y; i < 4; i++) {
            if (this.m[x][y] > this.m[x][i] && this.m[x][i] != 16) {
                kurang++;
            }
        }
        for (int k = x+1; k < 4; k++) {
            for (int l = 0; l < 4; l++) {
                if (this.m[x][y] > this.m[k][l] && this.m[k][l] != 16) {
                    kurang++;
                }
            }
        }
        if (this.m[x][y] != 16) {
            System.out.printf("Kurang(%d) = %d\n", this.m[x][y], kurang);
        } else {
            System.out.printf("Kurang() ubin kosong = %d\n", kurang);
        }
        return kurang;
    }
    
    // jumlah ubin tidak kosong yang tidak terdapat pada susunan akhir
    public int g() {
        int g = 0;
        int e = 1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (this.m[i][j] != e && this.m[i][j] != 16) {
                    g++;
                }
                e++;
            }
        }
        return g;
    }

    // jarak simpul ini ke simpul awal
    public int f() {
        int f = 0;
        Matriks N = this.getParent();
        while (!Objects.isNull(N)) {
            f++;
            N = N.getParent();
        }
        return f;
    }

    // menentukan apakah puzzle telah mencapai status akhir
    public Boolean goalState() {
        int e = 1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (this.m[i][j] != e) {
                    return false;
                }
                e++;
            }
        }
        return true;
    }

    // getter simpul parent
    public Matriks getParent() {
        return this.parent;
    }

    // getter cost
    public int getC() {
        return this.c;
    }
}