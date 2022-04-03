import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

class Matriks {
    private int m[][];

    public Matriks() {
        this.m = new int[4][4];
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
    }

    public Matriks(String filename) {
        try {
            this.m = new int[4][4];
            String F = ("..\\test\\" + filename);
            File file = new File(F);
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
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        }
    }

    public Matriks(Matriks M) {
        this.m = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.m[i][j] = M.m[i][j];
            }
        }
    }

    public Matriks Move(String dir) {
        Matriks Mat = new Matriks(this);
        int[] point = Mat.findBlank();
        if (dir.equals("up") && point[0] != 0) {
            Mat.m[point[0]][point[1]] = Mat.m[point[0] - 1][point[1]];
            Mat.m[point[0] - 1][point[1]] = 16;
            return Mat;
        }
        else if (dir.equals("right") && point[1] != 3) {
            Mat.m[point[0]][point[1]] = Mat.m[point[0]][point[1] + 1];
            Mat.m[point[0]][point[1] + 1] = 16;
            return Mat;
        }
        else if (dir.equals("down") && point[0]!= 3) {
            Mat.m[point[0]][point[1]] = Mat.m[point[0] + 1][point[1]];
            Mat.m[point[0] + 1][point[1]] = 16;
            return Mat;
        }
        else if (dir.equals("left") && point[1] != 0) {
            Mat.m[point[0]][point[1]] = Mat.m[point[0]][point[1] - 1];
            Mat.m[point[0]][point[1] - 1] = 16;
            return Mat;
        } else {
            return Mat;
        }
    }

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

    public Boolean reachableGoal() {
        int kurang = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                kurang += this.kurang(i, j);
            }
        }
        int[] point = this.findBlank();
        if (point[0] + point[1] % 2 == 1) {
            kurang++;
        }
        System.out.printf("%d%n", kurang);
        return kurang % 2 == 0;
    }

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
        return kurang;
    }
    
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
}