package v2;

import java.util.Scanner;

public class MainV2 {
    private static TextEditorV2 editor;
    private static Scanner scanner;

    public static void main(String[] args) {
        editor = new TextEditorV2();
        scanner = new Scanner(System.in);

        printWelcome();
        printHelp();

        while (true) {
            System.out.print("\n>");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            String[] parts = input.split(" ", 2);
            String command = parts[0].toLowerCase();

            switch (command) {
                case "append":
                    handleAppend(parts);
                    break;

                case "insert":
                    handleInsert(parts);
                    break;

                case "delete":
                    handleDelete(parts);
                    break;

                case "show":
                    editor.show();
                    break;

                case "redo":
                    editor.redo();
                    break;

                case "clear":
                    editor.clear();
                    break;

                case "copy":
                    handleCopy(parts);
                    break;

                case "paste":
                    handlePaste(parts);
                    break;

                case "clipboard":
                    editor.showClipboard();
                    break;

                case "help":
                    printHelp();
                    break;

                case "exit":
                    System.out.println("\nTerimakasi telah menggunakan Text Editor!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Perintah tidak dikenal: '" + command + "'. Ketik 'help' untuk daftar perintah.");
            }
        }
    }

    static void handleAppend(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Penggunaan: append <teks>");
            return;
        }
        editor.append(parts[1]);
    }

    static void handleInsert(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Penggunaan: insert <nomor_baris> <teks>");
            return;
        }

        String[] args = parts[1].split(" ", 2);
        if (args.length < 2) {
            System.out.println("Penggunaan: insert <nomor_baris> <teks>");
            return;
        }

        try {
            int lineNumber = Integer.parseInt(args[0]);
            String text = args[1];
            editor.insert(lineNumber, text);
        } catch (NumberFormatException e) {
            System.out.println("Error: Nomor baris harus berupa angka.");
        }
    }

    static void handleDelete(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Penggunaan: delete <nomor_baris>");
            return;
        }

        try {
            int lineNumber = Integer.parseInt(parts[1]);
            editor.delete(lineNumber);
        } catch (NumberFormatException e) {
            System.out.println("Error: Nomor baris harus berupa angka.");
        }
    }

    static void handleCopy(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Penggunaan: copy <nomor_baris>");
            return;
        }

        try {
            int lineNumber = Integer.parseInt(parts[1]);
            editor.copy(lineNumber);
        } catch (NumberFormatException e) {
            System.out.println("Error: Nomor baris harus berupa angka.");
        }
    }

    static void handlePaste(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Penggunaan: paste <nomor_baris>");
            return;
        }

        try {
            int lineNumber = Integer.parseInt(parts[1]);
            editor.paste(lineNumber);
        } catch (NumberFormatException e) {
            System.out.println("Error: Nomor baris harus berupa angka.");
        }
    }

    static void printWelcome() {
        System.out.println("\n============================================");
        System.out.println("          TEXT EDITOR - CLI VERSION          ");
        System.out.println("============================================");
    }

    static void printHelp() {
        System.out.println("\n========== PERINTAH ==========");
        System.out.println("append <teks>           - Tambah baris di akhir");
        System.out.println("insert <baris> <teks>   - Sisipkan di nomor baris");
        System.out.println("delete <baris>          - Hapus baris");
        System.out.println("show                    - Tampilkan semua baris");
        System.out.println("undo                    - Batalkan operasi terakhir");
        System.out.println("redo                    - Ulangi operasi yang dibatalkan");
        System.out.println("clear                   - Hapus semua baris");
        System.out.println("copy <baris>            - Salin baris ke clipboard");
        System.out.println("paste <baris>           - Tempel dari clipboard (FIFO)");
        System.out.println("clipboard               - Lihat riwayat clipboard");
        System.out.println("help                    - Tampilkan menu ini");
        System.out.println("exit                    - Keluar dari program");
        System.out.println("==============================");
    }
}
