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

    //append
    public void append(String text) {
        lines.add(text);
        int addedLineNumber = lines.size() - 1;
        Command cmd = new Command("append", text, addedLineNumber);
        undoStack.push(cmd);
        redoStack.clear();
    }

    //show 
    public void show() {
        if (lines.isEmpty()) {
            System.out.println("Document is empty.");
            return;
        }

        for (int i = 0; i < lines.size(); i++) {
            System.out.println((i + 1) + ": " + lines.get(i));
        }
    }

    //insert 
    public void insert(int lineNumber, String text) {
        if (lineNumber < 1 || lineNumber > lines.size() + 1) {
            System.out.println("Invalid line number.");
            return;
        }

        int index = lineNumber - 1;
        lines.add(index, text);

        Command cmd = new Command("insert", text, index);
        undoStack.push(cmd);
        redoStack.clear(); 
    }

    //delete 
    public void delete(int lineNumber) {
        if (lineNumber < 1 || lineNumber > lines.size()){
            System.out.println("Invalid line number.");
            return;
        }

        int index = lineNumber - 1;

        String deletedLine = lines.get(index);
        lines.remove(index);

        Command cmd = new Command("delete", deletedLine, index);
        undoStack.push(cmd);

        redoStack.clear();
    }

    //undo
    public void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("Nothing to undo.");
            return;
        }

        Command cmd = undoStack.pop();
        String action = cmd.getAction();
        String data = cmd.getData();
        int index = cmd.getLineNumber();

        switch (action) {
            case "append":
            case "insert":
                if (index >= 0 && index < lines.size()){
                    lines.remove(index);
                }
                break;
            case "delete":
            if (index >= 0 && index <= lines.size()) {
                lines.add(index, data);
            }
                break;
            // case "edit":
            // if (index >= 0 && index < lines.size()) {
            //     lines.set(index, data);
            // }
            // break;
         
            default:
            System.out.println("Unknown action: " + action);
            return;
        }

        redoStack.push(cmd);
    }

}