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

}