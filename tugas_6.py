# Class Node
class Node:
    def __init__(self, id, nama):
        self.id = id
        self.nama = nama
        self.left = None
        self.right = None


# Class BST
class BST:
    def __init__(self):
        self.root = None

    # ================= TAMBAH DATA =================
    def insert(self, root, id, nama):
        # Jika kosong, buat node baru
        if root is None:
            return Node(id, nama)

        # Rekursi ke kiri
        if id < root.id:
            root.left = self.insert(root.left, id, nama)
        # Rekursi ke kanan
        elif id > root.id:
            root.right = self.insert(root.right, id, nama)

        return root

    # ================= CARI DATA =================
    def search(self, root, id):
        # Jika kosong atau ditemukan
        if root is None or root.id == id:
            return root

        if id < root.id:
            return self.search(root.left, id)

        return self.search(root.right, id)

    # ================= HAPUS DATA =================
    def delete(self, root, id):
        if root is None:
            return root

        # Cari node
        if id < root.id:
            root.left = self.delete(root.left, id)
        elif id > root.id:
            root.right = self.delete(root.right, id)
        else:
            # Node ditemukan

            # Kasus 1: tidak punya anak
            if root.left is None and root.right is None:
                return None

            # Kasus 2: satu anak
            elif root.left is None:
                return root.right
            elif root.right is None:
                return root.left

            # Kasus 3: dua anak
            temp = self.min_value(root.right)

            root.id = temp.id
            root.nama = temp.nama

            root.right = self.delete(root.right, temp.id)

        return root

    # Cari nilai terkecil
    def min_value(self, root):
        while root.left:
            root = root.left
        return root

    # ================= TRAVERSAL =================
    def inorder(self, root):
        if root:
            self.inorder(root.left)
            print("ID: ", root.id, "- Nama: ", root.nama)
            self.inorder(root.right)

    def preorder(self, root):
        if root:
            print("ID: ", root.id, "- Nama: ", root.nama)
            self.preorder(root.left)
            self.preorder(root.right)

    def postorder(self, root):
        if root:
            self.postorder(root.left)
            self.postorder(root.right)
            print("ID: ", root.id, "- Nama: ", root.nama)


def main():
    tree = BST()

    while True:
        print("\n=== MENU BST ===")
        print("1. Tambah Data")
        print("2. Cari Data")
        print("3. Hapus Data")
        print("4. Inorder")
        print("5. Preorder")
        print("6. Postorder")
        print("7. Keluar")

        pilihan = int(input("Pilih: "))

        if pilihan == 1:
            id = int(input("Masukkan ID: "))
            nama = input("Masukkan Nama: ")
            tree.root = tree.insert(tree.root, id, nama)
            print("Data berhasil ditambahkan!")

        elif pilihan == 2:
            id = int(input("Masukkan ID yang dicari: "))
            hasil = tree.search(tree.root, id)

            if hasil:
                print("Data ditemukan:", hasil.id, "-", hasil.nama)
            else:
                print("Data tidak ditemukan.")

        elif pilihan == 3:
            id = int(input("Masukkan ID yang dihapus: "))
            tree.root = tree.delete(tree.root, id)
            print("Data berhasil dihapus (jika ada).")

        elif pilihan == 4:
            print("Inorder:")
            tree.inorder(tree.root)

        elif pilihan == 5:
            print("Preorder:")
            tree.preorder(tree.root)

        elif pilihan == 6:
            print("Postorder:")
            tree.postorder(tree.root)

        elif pilihan == 7:
            print("Program selesai.")
            break

        else:
            print("Pilihan tidak valid!")


# Jalankan program
if __name__ == "__main__":
    main()