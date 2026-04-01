import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;

// CLASS NODE
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

// CLASS BST
class BST {
    Node root;

    // INSERT
    Node insert(Node root, int id, String nama) {
        if (root == null) {
            return new Node(id, nama);
        }

        if (id < root.id) {
            root.left = insert(root.left, id, nama);
        } else if (id > root.id) {
            root.right = insert(root.right, id, nama);
        }

        return root;
    }

    // SEARCH
    Node search(Node root, int id) {
        if (root == null || root.id == id) {
            return root;
        }

        if (id < root.id) {
            return search(root.left, id);
        }

        return search(root.right, id);
    }

    // DELETE
    Node delete(Node root, int id) {
        if (root == null) return root;

        if (id < root.id) {
            root.left = delete(root.left, id);
        } else if (id > root.id) {
            root.right = delete(root.right, id);
        } else {

            // Tidak punya anak
            if (root.left == null && root.right == null) {
                return null;
            }

            // Satu anak
            else if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            // Dua anak
            Node temp = minValue(root.right);

            root.id = temp.id;
            root.nama = temp.nama;

            root.right = delete(root.right, temp.id);
        }

        return root;
    }

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

    // IMPORT CSV
    void importCSV(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;

            // skip header
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length >= 2) {
                    try {
                        int id = Integer.parseInt(data[0]);
                        String nama = data[1];

                        root = insert(root, id, nama);

                    } catch (NumberFormatException e) {
                        System.out.println("Data salah: " + line);
                    }
                }
            }

            br.close();
            System.out.println("✅ Import CSV berhasil!");

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}

// MAIN CLASS
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
            System.out.println("4. Inorder");
            System.out.println("5. Preorder");
            System.out.println("6. Postorder");
            System.out.println("7. Import CSV");
            System.out.println("8. Keluar");
            System.out.print("Pilih: ");
            pilihan = input.nextInt();

            switch (pilihan) {
                case 1:
                    System.out.print("ID: ");
                    int id = input.nextInt();
                    input.nextLine();

                    System.out.print("Nama: ");
                    String nama = input.nextLine();

                    tree.root = tree.insert(tree.root, id, nama);
                    break;

                case 2:
                    System.out.print("Cari ID: ");
                    int cari = input.nextInt();

                    Node hasil = tree.search(tree.root, cari);

                    if (hasil != null) {
                        System.out.println("Ditemukan: " + hasil.nama);
                    } else {
                        System.out.println("Tidak ditemukan");
                    }
                    break;

                case 3:
                    System.out.print("Hapus ID: ");
                    int hapus = input.nextInt();

                    tree.root = tree.delete(tree.root, hapus);
                    System.out.println("Data dihapus");
                    break;

                case 4:
                    tree.inorder(tree.root);
                    break;

                case 5:
                    tree.preorder(tree.root);
                    break;

                case 6:
                    tree.postorder(tree.root);
                    break;

                case 7:
                    input.nextLine();
                    System.out.print("Path CSV: ");
                    String path = input.nextLine();

                    tree.importCSV(path);
                    break;

                case 8:
                    System.out.println("Program selesai");
                    break;
            }

        } while (pilihan != 8);

        input.close();
    }
}