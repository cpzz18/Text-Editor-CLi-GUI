# Proyek Editor Teks - Implementasi Struktur Data
**Mata Kuliah:** Algoritma dan Pemrograman  
**Topik:** Stack, LinkedList, dan Queue  

---

## 📋 Deskripsi Proyek

Proyek ini mengimplementasikan 3 versi aplikasi berbeda yang mendemonstrasikan penggunaan struktur data **Stack**, **LinkedList**, dan **Queue**:

1. **Versi 1 (V1):** Kalkulator CLI - Stack Only
2. **Versi 2 (V2):** Editor Teks CLI - Stack + LinkedList + Queue
3. **Versi 3 (V3):** Editor Teks GUI - Stack + LinkedList + Queue

---

## 🗂️ Struktur Project
```
text-editor-project/
├── src/
│   ├── shared/
│   │   └── Command.java           # Class untuk menyimpan command history
│   ├── v1/
│   │   └── Calculator.java        # Kalkulator CLI (Stack)
│   ├── v2/
│   │   ├── TextEditorV2.java      # Core logic editor teks
│   │   ├── MainV2.java            # Interface CLI
│   │   └── TestEditorV2.java      # File testing
│   └── v3/
│       └── TextEditorGUI.java     # Editor teks dengan GUI Swing
├── bin/                            # Compiled classes
└── README.md
```

---

## 📚 Struktur Data yang Digunakan

### 1. **Stack** (LIFO - Last In First Out)
**Digunakan untuk:** Undo/Redo functionality di semua versi
- **V1:** Menyimpan history operasi matematika
- **V2 & V3:** Menyimpan snapshot dokumen untuk undo/redo

**Operasi:**
- `push()` - Tambah ke stack
- `pop()` - Ambil & hapus dari stack
- `isEmpty()` - Cek apakah kosong

### 2. **LinkedList** (Doubly Linked List)
**Digunakan untuk:** Penyimpanan dokumen per baris (V2 & V3)

**Keuntungan:**
- Efisien untuk insert/delete di tengah dokumen
- Dynamic size
- Tidak perlu shift elemen seperti ArrayList

**Operasi:**
- `add()` - Tambah baris
- `remove()` - Hapus baris
- `get()` - Akses baris tertentu

### 3. **Queue** (FIFO - First In First Out)
**Digunakan untuk:** Clipboard history (V2 & V3)

**Implementasi:** 
- Maksimal 5 item
- Item terlama otomatis terhapus saat penuh
- Paste mengambil item paling lama (FIFO)

**Operasi:**
- `offer()` - Tambah ke queue
- `poll()` - Ambil & hapus dari depan
- `isEmpty()` - Cek apakah kosong

---

## 🚀 Cara Menjalankan

### Prerequisites
- Java JDK 11 atau lebih baru
- Terminal/Command Prompt

### Compile Semua Versi
```bash
# Compile Command class (shared)
javac -d bin src/shared/Command.java

# Compile Versi 1 (Calculator)
javac -cp bin -d bin src/v1/Calculator.java

# Compile Versi 2 (Text Editor CLI)
javac -cp bin -d bin src/v2/TextEditorV2.java src/v2/MainV2.java

# Compile Versi 3 (Text Editor GUI)
javac -cp bin -d bin src/v3/TextEditorGUI.java
```

**Atau compile sekaligus:**
```bash
javac -d bin src/shared/*.java src/v1/*.java src/v2/*.java src/v3/*.java
```

---

## 💻 Versi 1: Kalkulator (Stack Only)

### Fitur:
- ✅ Operasi matematika: tambah, kurang, kali, bagi
- ✅ **Undo/Redo** menggunakan Stack
- ✅ History operasi
- ✅ Clear untuk reset
- ✅ Error handling (division by zero)

### Cara Menjalankan:
```bash
java -cp bin v1.Calculator
```

### Perintah yang Tersedia:
```
add <angka>     - Tambah
sub <angka>     - Kurang
mul <angka>     - Kali
div <angka>     - Bagi
undo            - Batalkan operasi terakhir (Stack)
redo            - Ulangi operasi yang dibatalkan (Stack)
result          - Tampilkan hasil sekarang
history         - Tampilkan riwayat operasi
clear           - Reset ke 0
help            - Tampilkan menu
exit            - Keluar
```

### Contoh Penggunaan:
```
> add 10
Operasi berhasil: 0.0 + 10.0 = 10.0

> mul 5
Operasi berhasil: 10.0 × 5.0 = 50.0

> undo
Undo berhasil. Hasil sekarang: 10.0

> redo
Redo berhasil. Hasil sekarang: 50.0
```

### Demonstrasi Stack:
- **Push:** Setiap operasi di-push ke `undoStack`
- **Pop:** Undo melakukan pop dari `undoStack`
- **Clear:** `redoStack` di-clear setiap ada operasi baru

---

## 📝 Versi 2: Editor Teks CLI (Full Features)

### Fitur:
- ✅ **LinkedList** untuk penyimpanan dokumen per baris
- ✅ **Stack** untuk undo/redo
- ✅ **Queue** untuk clipboard history (max 5, FIFO)
- ✅ Insert, append, delete baris
- ✅ Copy/paste dengan clipboard history
- ✅ Clear dengan undo support

### Cara Menjalankan:
```bash
java -cp bin v2.MainV2
```

### Perintah yang Tersedia:
```
append <teks>           - Tambah baris di akhir (LinkedList)
insert <baris> <teks>   - Sisipkan di nomor baris (LinkedList)
delete <baris>          - Hapus baris (LinkedList)
show                    - Tampilkan semua baris
undo                    - Batalkan operasi terakhir (Stack)
redo                    - Ulangi operasi yang dibatalkan (Stack)
clear                   - Hapus semua baris
copy <baris>            - Salin baris ke clipboard (Queue)
paste <baris>           - Tempel dari clipboard FIFO (Queue)
clipboard               - Lihat riwayat clipboard
help                    - Tampilkan menu
exit                    - Keluar
```

### Contoh Penggunaan:
```
> append Hello World
Berhasil menambahkan baris di akhir.

> append Line 2
Berhasil menambahkan baris di akhir.

> insert 1 First Line
Berhasil menyisipkan baris di posisi 1.

> show
=== Isi Dokumen ===
1: First Line
2: Hello World
3: Line 2
Total: 3 baris

> copy 1
Berhasil menyalin baris 1: "First Line"
Clipboard: 1/5 item

> paste 4
Berhasil menempel baris di posisi 4: "First Line"
Clipboard tersisa: 0 item

> undo
Undo berhasil: Membatalkan penempelan dari Clipboard
```

### Demonstrasi Struktur Data:
- **LinkedList:** `lines.add(index, text)` untuk insert di posisi tertentu
- **Stack:** `undoStack.push(command)` untuk setiap operasi
- **Queue:** `clipboardQueue.offer(text)` untuk copy, `poll()` untuk paste (FIFO)

---

## 🖥️ Versi 3: Editor Teks GUI (Swing)

### Fitur:
- ✅ **Interface grafis** menggunakan Java Swing
- ✅ **LinkedList** untuk penyimpanan dokumen
- ✅ **Stack** untuk undo/redo via button
- ✅ **Queue** untuk clipboard history (FIFO)
- ✅ TextArea untuk edit langsung
- ✅ Toolbar dengan button intuitif
- ✅ Status bar menampilkan info real-time

### Cara Menjalankan:
```bash
java -cp bin v3.TextEditorGUI
```

### Komponen GUI:

**Toolbar:**
- 🔄 **Undo** - Batalkan perubahan (Stack pop)
- 🔄 **Redo** - Ulangi perubahan (Stack pop dari redoStack)
- 📋 **Copy** - Salin teks terpilih ke clipboard (Queue offer)
- 📄 **Paste** - Tempel dari clipboard FIFO (Queue poll)
- 🗑️ **Clear** - Hapus semua dengan konfirmasi
- 📑 **Clipboard** - Lihat isi clipboard queue

**Status Bar:**
- Jumlah baris
- Jumlah karakter
- Clipboard: X/5 item

### Cara Menggunakan:
1. Ketik teks di area editor
2. **Select text** → Click **Copy** → Text masuk Queue
3. Copy beberapa text berbeda (max 5)
4. Click **Paste** → Text terlama (FIFO) akan di-paste
5. Click **Undo** → Paste dibatalkan
6. Click **Clear** → Semua text terhapus
7. Click **Undo** → Text kembali (dari Stack)

### Demonstrasi Struktur Data:
- **LinkedList:** Sinkronisasi TextArea ↔ LinkedList per baris
- **Stack:** Snapshot dokumen di-push sebelum setiap perubahan
- **Queue:** Clipboard FIFO - paste ambil item paling lama

---

## 🧪 Testing

### Test Versi 2 (Comprehensive):
```bash
java -cp bin v2.TestEditorV2
```

Output akan menampilkan testing otomatis untuk semua fitur.

---

## 🎯 Highlights

### Penggunaan Stack:
- **V1:** History operasi matematika
- **V2 & V3:** Snapshot dokumen untuk undo/redo
- **Konsep:** LIFO - operasi terakhir dibatalkan duluan

### Penggunaan LinkedList:
- **V2 & V3:** Penyimpanan dokumen per baris
- **Keuntungan:** Insert/delete di tengah efisien O(1) jika sudah ada node
- **vs ArrayList:** Tidak perlu shift elemen

### Penggunaan Queue:
- **V2 & V3:** Clipboard history
- **Implementasi:** Max 5 item, FIFO
- **Konsep:** Item paling lama di-paste duluan
- **Auto-remove:** Item ke-6 otomatis buang item paling lama

---

## 📖 Penjelasan Teknis

### Undo/Redo Implementation:
```java
// UNDO
1. Pop command dari undoStack
2. Push current state ke redoStack
3. Restore state dari command
```
```java
// REDO
1. Pop command dari redoStack
2. Push current state ke undoStack
3. Restore state dari command
```

### Clipboard Queue Implementation:
```java
// COPY
1. Offer text ke clipboardQueue
2. While size > MAX: poll oldest

// PASTE
1. Poll dari clipboardQueue (FIFO - oldest first)
2. Insert di posisi cursor
3. Save snapshot ke undoStack
```

---

## 🐛 Known Limitations

1. **V3 Undo/Redo:** Bekerja via button click, bukan real-time keystroke
2. **Clipboard:** Max 5 items (by design)
3. **V2 & V3:** Undo menyimpan full snapshot (memory intensive untuk dokumen besar)

---

## 👨‍💻 Author

**Nama:** [Nama Anda]  
**NIM:** [NIM Anda]  
**Mata Kuliah:** Algoritma dan Pemrograman  
**Dosen:** [Nama Dosen]  

---

## 📅 Development Timeline

- **Hari 1:** Versi 2 (CLI Full) - 5 jam
- **Hari 1:** Versi 1 (Calculator) - 2 jam
- **Hari 1:** Versi 3 (GUI) - 3-4 jam
- **Total:** ~10 jam

---

## ✅ Kesimpulan

Proyek ini berhasil mendemonstrasikan:
- ✅ **Stack** untuk undo/redo mechanism
- ✅ **LinkedList** untuk dynamic document storage
- ✅ **Queue** untuk FIFO clipboard history
- ✅ Implementasi CLI dan GUI
- ✅ Error handling dan user-friendly interface
- ✅ Clean code structure dan reusable components

---

## 📞 Contact

Jika ada pertanyaan atau bug, silakan hubungi:
- Email: [email@example.com]
- GitHub: [username]

---

**Terima kasih!** 🎉