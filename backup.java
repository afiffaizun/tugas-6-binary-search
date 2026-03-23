import java.util.Scanner;

// Class Node
class Node {
    int id;
    String nama;
    Node left, right;

    Node(int id, String nama) {
        this.id = id;
        this.nama = nama;
        left = right = null;
    }
}

// Class BST
class BST {
    Node root;

    // Tambah data
    Node insert(Node root, int id, String nama) {

        //Jika kosong buat Node baru
        if (root == null) return new Node(id, nama);

        // Rekursif ke kiri jika ID lebih kecil
        if (id < root.id)
            root.left = insert(root.left, id, nama);
        else if (id > root.id)
            root.right = insert(root.right, id, nama);
        else
            System.out.println("ID sudah ada!");

        return root;
    }

    // Cari data berdasarkan ID
    Node search(Node root, int id) {
        //kosong atau ketemu
        if (root == null || root.id == id)
            return root;

        //Cari kekiri
        if (id < root.id)
            return search(root.left, id);
        else
            //cari ke kekanan
            return search(root.right, id);
    }

    // Hapus data
    Node delete(Node root, int id) {
        if (root == null) return null;

        if (id < root.id) {
            root.left = delete(root.left, id);
        } else if (id > root.id) {
            root.right = delete(root.right, id);
        } else {
            // Node ditemukan

            // Tidak ada anak
            if (root.left == null && root.right == null)
                return null;

            // Satu anak
            else if (root.left == null)
                return root.right;

            else if (root.right == null)
                return root.left;

            // Dua anak
            // Cari Nilai Terkecil
            Node successor = findMin(root.right);
            root.id = successor.id;
            root.nama = successor.nama;

            // Hapus node pengganti
            root.right = delete(root.right, successor.id);
        }

        return root;
    }

    // Cari node dengan ID terkecil
    Node findMin(Node root) {
        while (root.left != null)
            root = root.left;
        return root;
    }

    // Inorder
    void inorder(Node root) {
        if (root != null) {
            inorder(root.left);
            System.out.println("ID: " + root.id + ", Nama: " + root.nama);
            inorder(root.right);
        }
    }

    // Preorder
    void preorder(Node root) {
        if (root != null) {
            System.out.println("ID: " + root.id + ", Nama: " + root.nama);
            preorder(root.left);
            preorder(root.right);
        }
    }

    // Postorder
    void postorder(Node root) {
        if (root != null) {
            postorder(root.left);
            postorder(root.right);
            System.out.println("ID: " + root.id + ", Nama: " + root.nama);
        }
    }
}

// Main
public class backup {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        BST tree = new BST();

        int pilihan;

        do {
            System.out.println("\n=== MENU BST (ID & Nama) ===");
            System.out.println("1. Tambah Data");
            System.out.println("2. Cari Data");
            System.out.println("3. Hapus Data");
            System.out.println("4. Traversal");
            System.out.println("5. Keluar");
            System.out.print("Pilih: ");
            pilihan = input.nextInt();
            input.nextLine(); // buang newline

            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan ID   : ");
                    int id = input.nextInt();
                    input.nextLine();

                    System.out.print("Masukkan Nama : ");
                    String nama = input.nextLine();

                    tree.root = tree.insert(tree.root, id, nama);
                    System.out.println("Data Berhasil Ditambahkan");
                    break;

                case 2:
                    System.out.print("Cari ID: ");
                    int cari = input.nextInt();

                    Node hasil = tree.search(tree.root, cari);
                    if (hasil != null)
                        System.out.println("Ditemukan → ID: " + hasil.id + ", Nama: " + hasil.nama);
                    else
                        System.out.println("Data tidak ditemukan.");
                    break;

                case 3:
                    System.out.print("Hapus ID: ");
                    int hapus = input.nextInt();
                    tree.root = tree.delete(tree.root, hapus);
                    System.out.println("Data dihapus (jika ada).");
                    break;

                case 4:
                    System.out.println("\nInorder:");
                    tree.inorder(tree.root);

                    System.out.println("\nPreorder:");
                    tree.preorder(tree.root);

                    System.out.println("\nPostorder:");
                    tree.postorder(tree.root);
                    break;

                case 5:
                    System.out.println("Keluar...");
                    break;

                default:
                    System.out.println("Pilihan tidak valid!");
            }

        } while (pilihan != 5);

        input.close();
    }
}