import java.util.Scanner;

// Class Node untuk menyimpan data
class Node {
    int id;
    String nama;
    Node left, right;

    // Constructor
    Node(int id, String nama) {
        this.id = id;
        this.nama = nama;
        left = right = null;
    }
}


// Class BST berisi semua operasi
class BST {
    Node root;

    // TAMBAH DATA 
    Node insert(Node root, int id, String nama) {
        // Jika kosong, buat node baru
        if (root == null) {
            return new Node(id, nama);
        }

        // Rekursi ke kiri jika id lebih kecil
        if (id < root.id) {
            root.left = insert(root.left, id, nama);
        }
        // Rekursi ke kanan jika id lebih besar
        else if (id > root.id) {
            root.right = insert(root.right, id, nama);
        }

        return root;
    }

    // CARI DATA 
    Node search(Node root, int id) {
        // Jika kosong atau ketemu
        if (root == null || root.id == id) {
            return root;
        }

        // Cari ke kiri
        if (id < root.id) {
            return search(root.left, id);
        }

        // Cari ke kanan
        return search(root.right, id);
    }

    // HAPUS DATA 
    Node delete(Node root, int id) {

        if (root == null) return root;

        // Cari node yang akan dihapus
        if (id < root.id) {
            root.left = delete(root.left, id);

        } else if (id > root.id) {
            root.right = delete(root.right, id);

        } else {
            // Node ditemukan

            // Kasus 1: tidak punya anak
            if (root.left == null && root.right == null) {
                return null;
            }

            // Kasus 2: satu anak
            else if (root.left == null) {
                return root.right;
                
            } else if (root.right == null) {
                return root.left;
            }

            // Kasus 3: dua anak
            // Cari pengganti (nilai terkecil di kanan)
            Node temp = minValue(root.right);

            // Ganti nilai
            root.id = temp.id;
            root.nama = temp.nama;

            // Hapus node pengganti
            root.right = delete(root.right, temp.id);
        }

        return root;
    }

    // Cari node dengan nilai terkecil
    Node minValue(Node root) {
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    // TRAVERSAL 
    void inorder(Node root) {
        if (root != null) {
            inorder(root.left);
            System.out.println("ID: " + root.id + " - Nama: " + root.nama);
            inorder(root.right);
        }
    }

    void preorder(Node root) {
        if (root != null) {
            System.out.println("ID: " + root.id + " - Nama: " + root.nama);
            preorder(root.left);
            preorder(root.right);
        }
    }

    void postorder(Node root) {
        if (root != null) {
            postorder(root.left);
            postorder(root.right);
            System.out.println("ID: " + root.id + " - Nama: " + root.nama);
        }
    }
}


public class tugas_6 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        BST tree = new BST();
        int pilihan;

        do {
            System.out.println("\n=== MENU BST ===");
            System.out.println("1. Tambah Data");
            System.out.println("2. Cari Data");
            System.out.println("3. Hapus Data");
            System.out.println("4. Tampilkan Inorder");
            System.out.println("5. Tampilkan Preorder");
            System.out.println("6. Tampilkan Postorder");
            System.out.println("7. Keluar");
            System.out.print("Pilih: ");
            pilihan = input.nextInt();

            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan ID: ");
                    int id = input.nextInt();
                    input.nextLine(); // buang newline
                    System.out.print("Masukkan Nama: ");
                    String nama = input.nextLine();

                    tree.root = tree.insert(tree.root, id, nama);
                    System.out.println("Data berhasil ditambahkan!");
                    break;

                case 2:
                    System.out.print("Masukkan ID yang dicari: ");
                    int cari = input.nextInt();

                    Node hasil = tree.search(tree.root, cari);

                    if (hasil != null) {
                        System.out.println("Data ditemukan");
                        System.out.println("ID: " + hasil.id + " - Nama : " + hasil.nama);
                    } else {
                        System.out.println("Data tidak ditemukan.");
                    }
                    break;

                case 3:
                    System.out.print("Masukkan ID yang dihapus: ");
                    int hapus = input.nextInt();

                    tree.root = tree.delete(tree.root, hapus);
                    System.out.println("Data berhasil dihapus.");
                    break;

                case 4:
                    System.out.println("Inorder:");
                    tree.inorder(tree.root);
                    break;

                case 5:
                    System.out.println("Preorder:");
                    tree.preorder(tree.root);
                    break;

                case 6:
                    System.out.println("Postorder:");
                    tree.postorder(tree.root);
                    break;

                case 7:
                    System.out.println("Program selesai.");
                    break;

                default:
                    System.out.println("Pilihan tidak valid!");
            }

        } while (pilihan != 7);

        input.close();
    }
}