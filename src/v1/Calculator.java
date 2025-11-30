package v1;

import java.util.Scanner;
import java.util.Stack;

public class Calculator {

    static double currentValue = 0;

    static Stack<String> undoStack = new Stack<>();
    static Stack<String> redoStack = new Stack<>();

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("=== Sistem Perhitungan ===");

        while (true) {
            System.out.print("> ");
            String command = input.nextLine().trim();

            if (command.startsWith("add")) {
                double n = Double.parseDouble(command.split(" ")[1]);
                doOperation("ADD", n);

            } else if (command.startsWith("sub")) {
                double n = Double.parseDouble(command.split(" ")[1]);
                doOperation("SUB", n);

            } else if (command.startsWith("mul")) {
                double n = Double.parseDouble(command.split(" ")[1]);
                doOperation("MUL", n);

            } else if (command.startsWith("div")) {
                double n = Double.parseDouble(command.split(" ")[1]);
                if (n == 0) {
                    System.out.println("Tidak bisa membagi dengan 0.");
                    continue;
                }
                doOperation("DIV", n);

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
                System.out.println("Keluar...");
                break;

            } else {
                System.out.println("Perintah tidak dikenal!");
            }
        }

        input.close();
    }

    static void doOperation(String op, double value) {
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

        System.out.println("OK. Hasil sekarang: " + currentValue);
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
            System.out.println("History kosong.");
            return;
        }

        System.out.println("Riwayat operasi:");
        for (String op : undoStack) {
            System.out.println(" - " + op);
        }
    }

    static void showHelp() {
        System.out.println("\n=== COMMANDS ===");
        System.out.println("add <number>    - Tambah");
        System.out.println("sub <number>    - Kurang");
        System.out.println("mul <number>    - Kali");
        System.out.println("div <number>    - Bagi");
        System.out.println("undo            - Batalkan operasi");
        System.out.println("redo            - Ulangi operasi");
        System.out.println("result          - Tampilkan hasil");
        System.out.println("history         - Tampilkan riwayat");
        System.out.println("clear           - Reset ke 0");
        System.out.println("help            - Tampilkan menu ini");
        System.out.println("exit            - Keluar");
        System.out.println("================");
    }

}
