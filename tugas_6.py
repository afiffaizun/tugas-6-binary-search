import csv

# CLASS NODE
class Node:
    def __init__(self, id, nama):
        self.id = id
        self.nama = nama
        self.height = 1
        self.left = None
        self.right = None


# CLASS AVL TREE
class AVL:
    def __init__(self):
        self.root = None

    # HEIGHT
    def height(self, node):
        return 0 if not node else node.height

    # BALANCE FACTOR
    def get_balance(self, node):
        return 0 if not node else self.height(node.left) - self.height(node.right)

    # RIGHT ROTATE
    def right_rotate(self, y):
        x = y.left
        t2 = x.right

        x.right = y
        y.left = t2

        y.height = 1 + max(self.height(y.left), self.height(y.right))
        x.height = 1 + max(self.height(x.left), self.height(x.right))

        return x

    # LEFT ROTATE
    def left_rotate(self, x):
        y = x.right
        t2 = y.left

        y.left = x
        x.right = t2

        x.height = 1 + max(self.height(x.left), self.height(x.right))
        y.height = 1 + max(self.height(y.left), self.height(y.right))

        return y

    # INSERT
    def insert(self, node, id, nama):
        if not node:
            return Node(id, nama)

        if id < node.id:
            node.left = self.insert(node.left, id, nama)
        elif id > node.id:
            node.right = self.insert(node.right, id, nama)
        else:
            return node

        node.height = 1 + max(self.height(node.left), self.height(node.right))

        balance = self.get_balance(node)

        # LL
        if balance > 1 and id < node.left.id:
            return self.right_rotate(node)

        # RR
        if balance < -1 and id > node.right.id:
            return self.left_rotate(node)

        # LR
        if balance > 1 and id > node.left.id:
            node.left = self.left_rotate(node.left)
            return self.right_rotate(node)

        # RL
        if balance < -1 and id < node.right.id:
            node.right = self.right_rotate(node.right)
            return self.left_rotate(node)

        return node

    # MIN VALUE
    def min_value(self, node):
        current = node
        while current.left:
            current = current.left
        return current

    # DELETE
    def delete(self, node, id):
        if not node:
            return node

        if id < node.id:
            node.left = self.delete(node.left, id)
        elif id > node.id:
            node.right = self.delete(node.right, id)
        else:
            if not node.left or not node.right:
                node = node.left if node.left else node.right
            else:
                temp = self.min_value(node.right)
                node.id = temp.id
                node.nama = temp.nama
                node.right = self.delete(node.right, temp.id)

        if not node:
            return node

        node.height = 1 + max(self.height(node.left), self.height(node.right))

        balance = self.get_balance(node)

        # LL
        if balance > 1 and self.get_balance(node.left) >= 0:
            return self.right_rotate(node)

        # LR
        if balance > 1 and self.get_balance(node.left) < 0:
            node.left = self.left_rotate(node.left)
            return self.right_rotate(node)

        # RR
        if balance < -1 and self.get_balance(node.right) <= 0:
            return self.left_rotate(node)

        # RL
        if balance < -1 and self.get_balance(node.right) > 0:
            node.right = self.right_rotate(node.right)
            return self.left_rotate(node)

        return node

    # SEARCH
    def search(self, node, id):
        if not node or node.id == id:
            return node

        if id < node.id:
            return self.search(node.left, id)

        return self.search(node.right, id)

    # TRAVERSAL
    def inorder(self, node):
        if node:
            self.inorder(node.left)
            print(f"ID: {node.id} - Nama: {node.nama}")
            self.inorder(node.right)

    def preorder(self, node):
        if node:
            print(f"ID: {node.id} - Nama: {node.nama}")
            self.preorder(node.left)
            self.preorder(node.right)

    def postorder(self, node):
        if node:
            self.postorder(node.left)
            self.postorder(node.right)
            print(f"ID: {node.id} - Nama: {node.nama}")

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
                            print("Data salah:", row)

            print("Import CSV berhasil!")

        except Exception as e:
            print("Error:", e)


# MAIN PROGRAM
def main():
    tree = AVL()

    while True:
        print("\n=== MENU AVL TREE ===")
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
                print("Ditemukan:", hasil.nama)
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
            print("Pilihan tidak valid")


if __name__ == "__main__":
    main()