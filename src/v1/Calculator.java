package v1;

import java.util.Scanner;
import java.util.Stack;

public class Calculator {

    static double currentValue = 0;
    static Stack<String> undoStack = new Stack<>();
    static Stack<String> redoStack = new Stack<>();

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        printWelcome();
        showHelp();

        while (true) {
            System.out.print("\n> ");
            String command = input.nextLine().trim();

            if (command.isEmpty()) {
                continue;
            }

            if (command.startsWith("add")) {
                handleOperation(command, "ADD");

            } else if (command.startsWith("sub")) {
                handleOperation(command, "SUB");

            } else if (command.startsWith("mul")) {
                handleOperation(command, "MUL");

            } else if (command.startsWith("div")) {
                handleOperation(command, "DIV");

            } else if (command.equals("undo")) {
                undoOperation();

            } else if (command.equals("redo")) {
                redoOperation();

            } else if (command.equals("result")) {
                System.out.println("Hasil sekarang: " + currentValue);

            } else if (command.equals("history")) {
                showHistory();

            } else if (command.equals("clear")) {
                currentValue = 0;
                undoStack.clear();
                redoStack.clear();
                System.out.println("Calculator telah di-reset ke 0.");

            } else if (command.equals("help")) {
                showHelp();
            }

            else if (command.equals("exit")) {
                System.out.println("Terima kasih telah menggunakan kalkulator. Sampai jumpa!");
                break;

            } else {
                System.out.println("Perintah tidak dikenal: '" + command + "'. Ketik 'help' untuk daftar perintah.");
            }
        }

        input.close();
    }

    static void handleOperation(String command, String op) {
        String[] parts = command.split(" ");
        if (parts.length < 2) {
            System.out.println("Penggunaan: " + op.toLowerCase() + " <angka>");
            return;
        }

        try {
            double value = Double.parseDouble(parts[1]);

            if (op.equals("DIV") && value == 0) {
                System.out.println("Error: Tidak bisa membagi dengan 0.");
                return;
            }

            doOperation(op, value);
        } catch (NumberFormatException e) {
            System.out.println("Error: Input harus berupa angka.");
        }
    }

    static void doOperation(String op, double value) {
        double oldValue = currentValue;

        switch (op) {
            case "ADD":
                currentValue += value;
                break;
            case "SUB":
                currentValue -= value;
                break;
            case "MUL":
                currentValue *= value;
                break;
            case "DIV":
                currentValue /= value;
                break;
        }

        undoStack.push(op + " " + value);
        redoStack.clear();

        System.out.println("Operasi berhasil: " + oldValue + " " + getSymbol(op) + " " + value + " = " + currentValue);
    }

    static void undoOperation() {
        if (undoStack.isEmpty()) {
            System.out.println("Tidak ada operasi yang bisa di-undo.");
            return;
        }

        String lastOp = undoStack.pop();
        redoStack.push(lastOp);

        recompute();
        System.out.println("Undo berhasil. Hasil sekarang: " + currentValue);
    }

    static void redoOperation() {
        if (redoStack.isEmpty()) {
            System.out.println("Tidak ada operasi yang bisa di-redo.");
            return;
        }

        String op = redoStack.pop();
        undoStack.push(op);

        recompute();
        System.out.println("Redo berhasil. Hasil sekarang: " + currentValue);

    }

    static void recompute() {
        currentValue = 0;
        for (String op : undoStack) {
            String[] parts = op.split(" ");
            String cmd = parts[0];
            double value = Double.parseDouble(parts[1]);

            switch (cmd) {
                case "ADD":
                    currentValue += value;
                    break;
                case "SUB":
                    currentValue -= value;
                    break;
                case "MUL":
                    currentValue *= value;
                    break;
                case "DIV":
                    currentValue /= value;
                    break;
            }
        }
    }

    static void showHistory() {
        if (undoStack.isEmpty()) {
            System.out.println("Riwayat kosong.");
            return;
        }

        System.out.println("\n=== Riwayat Operasi ===");
        double tempValue = 0;
        int num = 1;

        for (String op : undoStack) {
            String[] parts = op.split(" ");
            String cmd = parts[0];
            double value = Double.parseDouble(parts[1]);

            double before = tempValue;

            switch (cmd) {
                case "ADD":
                    tempValue += value;
                    break;
                case "SUB":
                    tempValue -= value;
                    break;
                case "MUL":
                    tempValue *= value;
                    break;
                case "DIV":
                    tempValue /= value;
                    break;
            }

            System.out.println(num + ". " + before + " " + getSymbol(cmd) + " " + value + " = " + tempValue);
            num++;
        }
        System.out.println("=======================");
    }

    static String getSymbol(String op) {
        switch (op) {
            case "ADD":
                return "+";
            case "SUB":
                return "-";
            case "MUL":
                return "×";
            case "DIV":
                return "÷";
            default:
                return "?";
        }
    }

    static void printWelcome() {
        System.out.println("\n┌─────────────────────────────────────────────────────────────┐");
        System.out.println("│                  KALKULATOR CLI - UNDO/REDO                 │");
        System.out.println("└─────────────────────────────────────────────────────────────┘\n");
    }
    
    

    static void showHelp() {
        System.out.println("\n┌─────────────────────── COMMAND LIST ────────────────────────┐");
        System.out.println("│  add <number>     →  Tambah angka                           │");
        System.out.println("│  sub <number>     →  Kurangi angka                          │");
        System.out.println("│  mul <number>     →  Kali angka                             │");
        System.out.println("│  div <number>     →  Bagi angka                             │");
        System.out.println("│                                                             │");
        System.out.println("│  undo              →  Batalkan operasi terakhir             │");
        System.out.println("│  redo              →  Ulangi operasi yang dibatalkan        │");
        System.out.println("│                                                             │");
        System.out.println("│  result            →  Lihat hasil sekarang                  │");
        System.out.println("│  history           →  Lihat riwayat operasi                 │");
        System.out.println("│  clear             →  Reset kalkulator ke 0                 │");
        System.out.println("│  help              →  Tampilkan daftar perintah             │");
        System.out.println("│  exit              →  Keluar dari aplikasi                  │");
        System.out.println("└─────────────────────────────────────────────────────────────┘");
    }

}
