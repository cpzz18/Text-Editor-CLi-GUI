package v2;

public class TestEditor {
    public static void main(String[] args) {
        TextEditorV2 editor = new TextEditorV2();

        System.out.println("=== Test 1: Dokumen Kosong ===");
        editor.show();
        
        // Test 2: Append 1 baris
        System.out.println("\n=== Test 2: Append 1 Baris ===");
        editor.append("Baris pertama");
        editor.show();
        
        // Test 3: Append beberapa baris
        System.out.println("\n=== Test 3: Append Beberapa Baris ===");
        editor.append("Baris kedua");
        editor.append("Baris ketiga");
        editor.show();

        // Test 4: Insert di posisi valid
        System.out.println("\n=== Test 4: Insert di Posisi Valid ===");
        editor.insert(0, null);
    }
}
