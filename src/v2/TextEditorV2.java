package v2;

import shared.Command;
import java.util.LinkedList;
import java.util.Stack;

import java.util.Queue;

public class TextEditorV2 {
    private static final int MAX_CLIPBOARD_SIZE = 5;

    private LinkedList<String> lines;
    private Stack<Command> undoStack;
    private Stack<Command> redoStack;
    private Queue<String> clipboardQueue;

    public TextEditorV2() {
        lines = new LinkedList<>();
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        clipboardQueue = new LinkedList<>();
    }

    // append
    public void append(String text) {
        if (text == null || text.trim().isEmpty()) {
            System.out.println("Error: Teks tidak boleh kosong.");
            return;
        }

        lines.add(text);
        int addedLineNumber = lines.size() - 1;
        Command cmd = new Command("append", text, addedLineNumber);
        undoStack.push(cmd);
        redoStack.clear();

        System.out.println("Berhasil menambahkan baris di posisi " + lines.size() + ".");
    }

    // show
    public void show() {
        if (lines.isEmpty()) {
            System.out.println("Dokumen Kosong.");
            return;
        }

        System.out.println("=== Isi Dokumen ===");
        for (int i = 0; i < lines.size(); i++) {
            System.out.println((i + 1) + ": " + lines.get(i));
        }
        System.out.println("Total: " + lines.size() + " baris.");
    }

    // insert
    public void insert(int lineNumber, String text) {
        if (text == null || text.trim().isEmpty()) {
            System.out.println("Error: Teks tidak boleh kosong.");
            return;
        }
        if (lineNumber < 1 || lineNumber > lines.size() + 1) {
            System.out.println("Error: Nomor baris tidak valid. (1-" + (lines.size() + 1) + ")");
            return;
        }
        int index = lineNumber - 1;
        lines.add(index, text);

        Command cmd = new Command("insert", text, index);
        undoStack.push(cmd);
        redoStack.clear();

        System.out.println("Berhasil menyisipkan teks pada baris " + lineNumber + ".");
    }

    // delete
    public void delete(int lineNumber) {
        if (lineNumber < 1 || lineNumber > lines.size()) {
            System.out.println("Error: Nomor baris tidak valid. (1-" + lines.size() + ")");
            return;
        }

        int index = lineNumber - 1;
        String deletedLine = lines.get(index);
        lines.remove(index);

        Command cmd = new Command("delete", deletedLine, index);
        undoStack.push(cmd);
        redoStack.clear();

        System.out.println("Berhasil menghapus baris " + lineNumber + ": \"" + deletedLine + "\"");
    }

    // undo
    public void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("Tidak ada yang bisa di-undo.");
            return;
        }

        Command cmd = undoStack.pop();
        String action = cmd.getAction();
        String data = cmd.getData();
        int index = cmd.getLineNumber();

        switch (action) {
            case "append":
                if (index >= 0 && index < lines.size()) {
                    lines.remove(index);
                }
                System.out.println("Undo berhasil: Membatalkan penambahan baris " + (index + 1));
                break;
            case "insert":
                if (index >= 0 && index < lines.size()) {
                    lines.remove(index);
                }
                System.out.println("Undo berhasil: Membatalkan penyisipkan baris " + (index + 1));
                break;
            case "paste":
                if (index >= 0 && index < lines.size()) {
                    lines.remove(index);
                }
                System.out.println("Undo berhasil: Membatalkan penempelan dari Clipboard" + (index + 1));
                break;
            case "delete":
                if (index >= 0 && index <= lines.size()) {
                    lines.add(index, data);
                }
                System.out.println("Undo berhasil: Mengembalikan baris yang dihapus pada baris " + (index + 1));
                break;
            // case "edit":
            // if (index >= 0 && index < lines.size()) {
            // lines.set(index, data);
            // }
            // break;
            case "clear":
                if (data != null && !data.isEmpty()) {
                    String[] restoredLines = data.split("\n");
                    for (String line : restoredLines) {
                        lines.add(line);
                    }
                }
                System.out.println("Undo berhasil: Mengembalikan semua baris yang dihapus");
                break;
            default:
                System.out.println("Error: Aksi tidak dikenal " + action);
                return;
        }

        redoStack.push(cmd);
    }

    // redo
    public void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("Tidak ada yang bisa di-redo.");
            return;
        }

        Command cmd = redoStack.pop();
        String action = cmd.getAction();
        String data = cmd.getData();
        int index = cmd.getLineNumber();

        switch (action) {
            case "append":
            case "insert":
            case "paste":
                if (index >= 0 && index <= lines.size()) {
                    lines.add(index, data);
                }
                System.out.println("Redo berhasil: " + action + " pada baris " + (index + 1));
                break;
            case "delete":
                if (index >= 0 && index < lines.size()) {
                    lines.remove(index);
                }
                System.out.println("Redo berhasil: Menghapus kembali baris " + (index + 1));
                break;
            // case "edit":
            // if (index >= 0 && index < lines.size()) {
            // lines.set(index, data);
            // }
            // break;
            case "clear":
                lines.clear();
                System.out.println("Redo berhasil: Menghapus semua baris kembali");
                break;
            default:
                System.out.println("Error: Aksi tidak dikenal dalam redo: " + action);
                return;
        }
        undoStack.push(cmd);
    }

    // clear
    public void clear() {
        if (lines.isEmpty()) {
            System.out.println("Dokumen sudah kosong.");
            return;
        }

        String allLines = String.join("\n", lines);
        int totalLines = lines.size();
        lines.clear();

        Command cmd = new Command("clear", allLines, 0);
        undoStack.push(cmd);
        redoStack.clear();

        System.out.println("Berhasil menghapus " + totalLines + " baris. Dokumen sekarang kosong.");
    }

    // copy
    public void copy(int lineNumber) {
        if (lineNumber < 1 || lineNumber > lines.size()) {
            System.out.println("Error: Nomor baris tidak valid. (1-" + lines.size() + ")");
            return;
        }

        int index = lineNumber - 1;
        String text = lines.get(index);

        clipboardQueue.offer(text);

        while (clipboardQueue.size() > MAX_CLIPBOARD_SIZE) {
            clipboardQueue.poll();
        }

        System.out.println("Berhasil menyalin baris " + lineNumber + ": \"" + text + "\"");
        System.out.println("Clipboard: " + clipboardQueue.size() + "/" + MAX_CLIPBOARD_SIZE + " item");
    }

    // paste
    public void paste(int lineNumber) {
        if (clipboardQueue.isEmpty()) {
            System.out.println("Error: Clipboard kosong. Salin baris terlebih dahulu.");
            return;
        }

        String text = clipboardQueue.poll();

        if (lineNumber < 1 || lineNumber > lines.size() + 1) {
            System.out.println("Error: Nomor baris tidak valid. (1-" + (lines.size() + 1) + ")");
            clipboardQueue.offer(text);
            return;
        }

        int index = lineNumber - 1;
        lines.add(index, text);

        Command cmd = new Command("paste", text, index);
        undoStack.push(cmd);
        redoStack.clear();

        System.out.println("Berhasil menempel baris di posisi " + lineNumber + ": \"" + text + "\"");
        System.out.println("Clipboard tersisa: " + clipboardQueue.size() + " item");
    }

    // showClipboard
    public void showClipboard() {
        if (clipboardQueue.isEmpty()) {
            System.out.println("Clipboard kosong.");
            return;
        }

        System.out.println("\n=== Riwayat Clipboard (Terlama -> Terbaru) ===");
        int num = 1;
        for (String item : clipboardQueue) {
            System.out.println(num + ": " + item);
            num++;
        }
        System.out.println("Total: " + clipboardQueue.size() + "/" + MAX_CLIPBOARD_SIZE + " item.");
    }

}