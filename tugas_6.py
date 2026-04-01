import csv

# CLASS NODE
class Node:
    def __init__(self, id, nama):
        self.id = id
        self.nama = nama
        self.left = None
        self.right = None


# CLASS BST
class BST:
    def __init__(self):
        self.root = None

    # INSERT
    def insert(self, root, id, nama):
        if root is None:
            return Node(id, nama)

        if id < root.id:
            root.left = self.insert(root.left, id, nama)
        elif id > root.id:
            root.right = self.insert(root.right, id, nama)

        return root

    # SEARCH
    def search(self, root, id):
        if root is None or root.id == id:
            return root

        if id < root.id:
            return self.search(root.left, id)

        return self.search(root.right, id)

    # DELETE
    def delete(self, root, id):
        if root is None:
            return root

        if id < root.id:
            root.left = self.delete(root.left, id)
        elif id > root.id:
            root.right = self.delete(root.right, id)
        else:
            # Tidak punya anak
            if root.left is None and root.right is None:
                return None

            # Satu anak
            if root.left is None:
                return root.right
            elif root.right is None:
                return root.left

            # Dua anak
            temp = self.min_value(root.right)
            root.id = temp.id
            root.nama = temp.nama
            root.right = self.delete(root.right, temp.id)

        return root

    def min_value(self, root):
        while root.left:
            root = root.left
        return root

    # TRAVERSAL
    def inorder(self, root):
        if root:
            self.inorder(root.left)
            print(f"ID: {root.id} - Nama: {root.nama}")
            self.inorder(root.right)

    def preorder(self, root):
        if root:
            print(f"ID: {root.id} - Nama: {root.nama}")
            self.preorder(root.left)
            self.preorder(root.right)

    def postorder(self, root):
        if root:
            self.postorder(root.left)
            self.postorder(root.right)
            print(f"ID: {root.id} - Nama: {root.nama}")

    # IMPORT CSV
    def import_csv(self, path):
        try:
            with open(path, newline='') as file:
                reader = csv.reader(file)

                next(reader)  # skip header

                for row in reader:
                    if len(row) >= 2:
                        try:
                            id = int(row[0])
                            nama = row[1]

                            self.root = self.insert(self.root, id, nama)

                        except ValueError:
                            print(f"Data salah: {row}")

            print("Import CSV berhasil!")

        except Exception as e:
            print("Error:", e)


# MAIN PROGRAM
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
        print("7. Import CSV")
        print("8. Keluar")

        pilihan = input("Pilih: ")

        if pilihan == "1":
            id = int(input("ID: "))
            nama = input("Nama: ")
            tree.root = tree.insert(tree.root, id, nama)

        elif pilihan == "2":
            cari = int(input("Cari ID: "))
            hasil = tree.search(tree.root, cari)

            if hasil:
                print(f"Ditemukan: {hasil.nama}")
            else:
                print("Tidak ditemukan")

        elif pilihan == "3":
            hapus = int(input("Hapus ID: "))
            tree.root = tree.delete(tree.root, hapus)
            print("Data dihapus")

        elif pilihan == "4":
            tree.inorder(tree.root)

        elif pilihan == "5":
            tree.preorder(tree.root)

        elif pilihan == "6":
            tree.postorder(tree.root)

        elif pilihan == "7":
            path = input("Path CSV: ")
            tree.import_csv(path)

        elif pilihan == "8":
            print("Program selesai")
            break

        else:
            print("Pilihan tidak valid!")


if __name__ == "__main__":
    main()