package v2;

public class TestEditorV2 {
    public static void main(String[] args) {
        TextEditorV2 editor = new TextEditorV2();
        
        System.out.println("=================================");
        System.out.println("TEXT EDITOR V2 - COMPREHENSIVE TEST");
        System.out.println("=================================\n");
        
        // Test 1: Show empty document
        testShowEmpty(editor);
        
        // Test 2: Append operations (LinkedList)
        testAppend(editor);
        
        // Test 3: Insert operations (LinkedList)
        testInsert(editor);
        
        // Test 4: Delete operations (LinkedList)
        testDelete(editor);
        
        // Test 5: Undo operations (Stack)
        testUndo(editor);
        
        // Test 6: Redo operations (Stack)
        testRedo(editor);
        
        // Test 7: Clear operations
        testClear(editor);
        
        // Test 8: Copy to clipboard (Queue)
        testCopy(editor);
        
        // Test 9: Paste from clipboard (Queue)
        testPaste(editor);
        
        // Test 10: Clipboard history (Queue)
        testClipboardHistory(editor);
        
        System.out.println("\n=================================");
        System.out.println("ALL TESTS COMPLETED!");
        System.out.println("=================================");
    }
    
    static void testShowEmpty(TextEditorV2 editor) {
        System.out.println(">>> Test 1: Show Empty Document");
        editor.show();
        System.out.println();
    }
    
    static void testAppend(TextEditorV2 editor) {
        System.out.println(">>> Test 2: Append Operations (LinkedList)");
        editor.append("First line");
        editor.append("Second line");
        editor.append("Third line");
        editor.show();
        System.out.println();
    }
    
    static void testInsert(TextEditorV2 editor) {
        System.out.println(">>> Test 3: Insert Operations (LinkedList)");
        System.out.println("Insert 'Zero line' at position 1:");
        editor.insert(1, "Zero line");
        editor.show();
        System.out.println("\nInsert 'Middle line' at position 3:");
        editor.insert(3, "Middle line");
        editor.show();
        System.out.println();
    }
    
    static void testDelete(TextEditorV2 editor) {
        System.out.println(">>> Test 4: Delete Operations (LinkedList)");
        System.out.println("Delete line 3:");
        editor.delete(3);
        editor.show();
        System.out.println("\nDelete invalid line (100):");
        editor.delete(100);
        System.out.println();
    }
    
    static void testUndo(TextEditorV2 editor) {
        System.out.println(">>> Test 5: Undo Operations (Stack)");
        System.out.println("Undo last delete:");
        editor.undo();
        editor.show();
        System.out.println("\nUndo again:");
        editor.undo();
        editor.show();
        System.out.println();
    }
    
    static void testRedo(TextEditorV2 editor) {
        System.out.println(">>> Test 6: Redo Operations (Stack)");
        System.out.println("Redo:");
        editor.redo();
        editor.show();
        System.out.println("\nRedo again:");
        editor.redo();
        editor.show();
        System.out.println();
    }
    
    static void testClear(TextEditorV2 editor) {
        System.out.println(">>> Test 7: Clear Operations");
        System.out.println("Clear all:");
        editor.clear();
        editor.show();
        System.out.println("\nUndo clear:");
        editor.undo();
        editor.show();
        System.out.println();
    }
    
    static void testCopy(TextEditorV2 editor) {
        System.out.println(">>> Test 8: Copy to Clipboard (Queue)");
        editor.copy(1);
        editor.copy(2);
        editor.copy(3);
        System.out.println();
    }
    
    static void testPaste(TextEditorV2 editor) {
        System.out.println(">>> Test 9: Paste from Clipboard (Queue - FIFO)");
        System.out.println("Paste at position 1 (should paste oldest copied):");
        editor.paste(1);
        editor.show();
        System.out.println();
    }
    
    static void testClipboardHistory(TextEditorV2 editor) {
        System.out.println(">>> Test 10: Clipboard History (Queue)");
        System.out.println("Copy 6 more items to test max size (5):");
        editor.copy(1);
        editor.copy(2);
        editor.copy(3);
        editor.copy(4);
        editor.copy(1);
        editor.copy(2);
        System.out.println("\nShow clipboard (should only show last 5):");
        editor.showClipboard();
        System.out.println();
    }
}