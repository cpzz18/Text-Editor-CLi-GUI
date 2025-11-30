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

            // Split input:"command arg1 arg2..."

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
                    System.out.println("\nThank you for using Text Editor V2!");
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Unknown command: '" + command + "'. Type 'help' for commands.");
            }
        }
    }

    static void handleAppend(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: append <text>");
            return;
        }
        editor.append(parts[1]);
    }

    static void handleINsert(String[] parts) {
        if (parts.length <2) {
           System.out.println("Usage: insert <line_number> <text>");
            return;
        }

        // Split args: "line_number text"
        String[] args = parts[1].split(" ",2);
        if (args.length < 2) {
            System.out.println("Usage: insert <line_number> <text>");
            return;
        }
        try {
            int lineNumber = Integer.parselnt(args[0]);
            String text = args[1];
            editor.insert(lineNumber, text);
        } catch (NumberFormatException e) {
            System.out.println("Error: Line number must be an integer.");
        }
        }
        
        static void handleDelete(String[] parts) {
            if (parts.length < 2) {
                System.out.println("Usage: delete ,line_number>");
                return;
            }

            try {
                int lineNumber = Integer.parseInt(parts[1]);
                editor.delete(lineNumber);
            } catch (NumberFormatException e) {
                System.out.println("Error: Line number must be an integer.");
            }
            }

            static void handleCopy(String[] parts) {
                if (parts.length < 2) {
                    System.out.println("Usage: copy<line_number>");
                    return;
                }

                try {
                    int lineNumber = Integer.parseInt(parts[1]);
                    editor.copy(lineNumber);
                } catch (NumberFormatException e) {
                    System.out.println("Error: Line number must be an integer.");
                }
            }

            static void handlePaste(String[] parts) {
                if (parts.length < 2) {
                    System.out.println("usage: paste <line_number>");
                    return;
                }

                try {
                    int lineNumber = Integer.parseInt(parts[1]);
                    editor.paste(lineNumber);
                } catch (NumberFormatException e) {
                    System.out.println("Error: Line number must be an integer.");
                }
            }

                static void printWelcome() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║     TEXT EDITOR V2 - CLI VERSION      ║");
        System.out.println("║  Stack + LinkedList + Queue Demo     ║");
        System.out.println("╚════════════════════════════════════════╝");
            }

            static void printHelp() {
                System.out.println("\n========== COMMANDS ==========");
                System.out.println("append <text>   -Add line at end");
                System.out.println("insert <line> <text>    -Insert at line number");
                System.out.println("delete <line>   -Delete line");
                System.out.println("show    -Display all lines");
                System.out.println("undo    -Undo last operation");
                System.out.println("redo    -Redo undone operation");
                System.out.println("clear   -Clear all lines");
                System.out.println("copy <line> -Copy line to clipboard");
                System.out.println("clipboard   -Show clipboard history");
                System.out.println("help    -Show this menu");
                System.out.println("exit    -Exit program");
                System.out.println("================================");

            }
        }
