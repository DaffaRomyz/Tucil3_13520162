# Tucil3_13520162
Tugas Kecil 3 IF2211 Strategi Algoritma Semester II 2021/2022 Penyelesaian Persoalan 15-Puzzle dengan Algoritma Branch and Bound

## Deskripsi
Program akan menyelesaikan persoalan 15-Puzzle dengan menggunakan Algoritma Branch and Bound.
Nilai bound tiap simpul adalah penjumlahan cost yang diperlukan untuk sampai suatu simpul x dari akar, dengan taksiran cost simpul x untuk sampai ke goal.
Taksiran cost yang digunakan adalah jumlah ubin tidak kosong yang tidak berada pada tempat sesuai susunan akhir (goal state).
Posisi awal 15-puzzle dibangkitkan secara acak oleh program dan/atau dimasukkan dari file teks.

## Requirement
* java

## Langkah Compile
Pada directory src jalankan `javac Main.java Matriks.java -d ../bin`
 
## Cara Menggunakan Program
1. Pada directory src jalankan `java Main`
2. Pilih posisi awal puzzle secara acak atau dari file teks
3. Jika memilih file teks masukan nama file yang ada dalam directory test
4. Program akan menampilkan puzzle awal
5. Program akan menampilkan fungsi kurang() untuk setiap ubin
6. Program akan menampulkan nilai sigma(i=1,16) kurang(i) + X
7. Program akan menentukan apakah puzzle dapat diselesaikan
8. Jika dapat diselesaikan program akan menampilkan urutan matriks dari posisi awal ke posisi akhir
9. Program akan menampilkan waktu eksekusi
10. Program akan menampilkan jumlah simpul yang dibangkitkan

## Author
Name	: Daffa Romyz Aufa  
NIM	: 13520162  
E-mail	: 13520162@std.stei.itb.ac.id
	  daffaromyzaufa@gmail.com
