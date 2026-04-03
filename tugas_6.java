import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;

// CLASS NODE AVL
class Node {
    int id;
    String nama;
    int height;
    Node left, right;

    Node(int id, String nama) {
        this.id = id;
        this.nama = nama;
        this.height = 1;
        left = right = null;
    }
}

// CLASS AVL TREE
class AVL {
    Node root;

    // HEIGHT
    int height(Node n) {
        return (n == null) ? 0 : n.height;
    }

    // BALANCE FACTOR
    // -1, 0, atau 1 maka seimbang
    int getBalance(Node n) {
        return (n == null) ? 0 : height(n.left) - height(n.right);
    }

    // RIGHT ROTATE
    Node rightRotate(Node y) {
        Node x = y.left; // ambil anak kiri dan jadikan node
        Node T2 = x.right; // simpan subtree kanan dari x

        x.right = y; // jadikan y sebagai anak kanan
        y.left = T2; 

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // LEFT ROTATE             // contoh
    Node leftRotate(Node x) { // x = 10
        Node y = x.right;  // y = 20
        Node T2 = y.left; // T2 = null

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    // INSERT AVL
    Node insert(Node node, int id, String nama) {
        if (node == null)
            return new Node(id, nama);

        if (id < node.id)
            node.left = insert(node.left, id, nama);
        else if (id > node.id)
            node.right = insert(node.right, id, nama);
        else
            return node;

        // update height node
        node.height = 1 + Math.max(height(node.left), height(node.right));

        //hitung balance faktor
        int balance = getBalance(node);

        // Left berat
        if (balance > 1 && id < node.left.id)
            return rightRotate(node);

        // kanan berat
        if (balance < -1 && id > node.right.id)
            return leftRotate(node);

        // kombinasi kedua
        if (balance > 1 && id > node.left.id) {
            node.left = leftRotate(node.left); // rotasi kiri dulu baru kanan
            return rightRotate(node);
        }

        // RL
        if (balance < -1 && id < node.right.id) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // MIN VALUE
    Node minValue(Node node) {
        Node current = node;
        while (current.left != null)
            current = current.left;
        return current;
    }

    // DELETE AVL
    Node delete(Node root, int id) {
        if (root == null)
            return root;

        if (id < root.id)
            root.left = delete(root.left, id);
        else if (id > root.id)
            root.right = delete(root.right, id);
        else {
            if ((root.left == null) || (root.right == null)) {
                Node temp = (root.left != null) ? root.left : root.right;

                if (temp == null) {
                    root = null;
                } else {
                    root = temp;
                }
            } else {
                Node temp = minValue(root.right);

                root.id = temp.id;
                root.nama = temp.nama;

                root.right = delete(root.right, temp.id);
            }
        }

        if (root == null)
            return root;

        root.height = 1 + Math.max(height(root.left), height(root.right));

        int balance = getBalance(root);

        // LL
        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);

        // LR
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // RR
        if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);

        // RL
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    // SEARCH
    Node search(Node root, int id) {
        if (root == null || root.id == id)
            return root;

        if (id < root.id)
            return search(root.left, id);

        return search(root.right, id);
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

            br.readLine(); // skip header

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
            System.out.println("Import CSV berhasil!");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

// MAIN CLASS
public class tugas_6 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        AVL tree = new AVL();
        int pilihan;

        do {
            System.out.println("\n=== MENU AVL TREE ===");
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

                    if (hasil != null)
                        System.out.println("Ditemukan: " + hasil.nama);
                    else
                        System.out.println("Tidak ditemukan");
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