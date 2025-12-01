package v3;

import shared.Command;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class TextEditorGUI extends JFrame {
    // Data Structures
    private LinkedList<String> lines;
    private Stack<Command> undoStack;
    private Stack<Command> redoStack;
    private Queue<String> clipboardQueue;
    private static final int MAX_CLIPBOARD = 5;

    // GUI Components
    private JTextArea textArea;
    private JButton btnUndo, btnRedo, btnCopy, btnPaste, btnClear;
    private JLabel statusLabel;

    public TextEditorGUI() {
        // Initialize data structures
        lines = new LinkedList<>();
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        clipboardQueue = new LinkedList<>();

        // Setup GUI
        setupGUI();
        updateStatus();
    }

    private void setupGUI() {
        setTitle("Editor Teks V3 - GUI Version");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main layout
        setLayout(new BorderLayout());

        // 1. Toolbar (North)
        JPanel toolbar = createToolbar();
        add(toolbar, BorderLayout.NORTH);

        // 2. Text Area (Center)
        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // 3. Status Bar (South)
        statusLabel = new JLabel("Baris: 0 | Karakter: 0 | Clipboard: 0/5");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(statusLabel, BorderLayout.SOUTH);
    }

    private JPanel createToolbar() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Undo Button
        btnUndo = new JButton("↶ Undo");
        btnUndo.addActionListener(e -> undo());
        panel.add(btnUndo);

        // Redo Button
        btnRedo = new JButton("↷ Redo");
        btnRedo.addActionListener(e -> redo());
        panel.add(btnRedo);

        panel.add(new JSeparator(SwingConstants.VERTICAL));

        // Copy Button
        btnCopy = new JButton("📋 Copy");
        btnCopy.addActionListener(e -> copyToClipboard());
        panel.add(btnCopy);

        // Paste Button
        btnPaste = new JButton("📄 Paste");
        btnPaste.addActionListener(e -> pasteFromClipboard());
        panel.add(btnPaste);

        panel.add(new JSeparator(SwingConstants.VERTICAL));

        // Clear Button
        btnClear = new JButton("🗑 Clear");
        btnClear.addActionListener(e -> clearAll());
        panel.add(btnClear);

        // Show Clipboard Button
        JButton btnShowClipboard = new JButton("📑 Clipboard");
        btnShowClipboard.addActionListener(e -> showClipboard());
        panel.add(btnShowClipboard);

        return panel;
    }

    private void undo() {
        if (undoStack.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Tidak ada yang bisa di-undo.",
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Command currentState = createSnapshot("current");
        redoStack.push(currentState);

        Command cmd = undoStack.pop();
        restoreSnapshot(cmd);

        JOptionPane.showMessageDialog(this,
                "Undo berhasil: " + cmd.getAction(),
                "Sukses",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void redo() {
        if (redoStack.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Tidak ada yang bisa di-redo.",
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Command currentState = createSnapshot("current");
        undoStack.push(currentState);

        Command cmd = redoStack.pop();
        restoreSnapshot(cmd);

        JOptionPane.showMessageDialog(this,
                "Redo berhasil: " + cmd.getAction(),
                "Sukses",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void copyToClipboard() {
        String selectedText = textArea.getSelectedText();
        if (selectedText == null || selectedText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Pilih teks terlebih dahulu untuk di-copy.",
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        clipboardQueue.offer(selectedText);

        while (clipboardQueue.size() > MAX_CLIPBOARD) {
            clipboardQueue.poll();
        }

        updateStatus();

        JOptionPane.showMessageDialog(this,
                "Berhasil menyalin: \"" + selectedText + "\"\n" +
                        "Clipboard: " + clipboardQueue.size() + "/" + MAX_CLIPBOARD,
                "Sukses",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void pasteFromClipboard() {
        if (clipboardQueue.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Clipboard kosong. Salin teks terlebih dahulu.",
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Command cmd = createSnapshot("paste");
        undoStack.push(cmd);
        redoStack.clear();

        String textToPaste = clipboardQueue.poll();

        int caretPos = textArea.getCaretPosition();
        String currentText = textArea.getText();
        String newText = currentText.substring(0, caretPos) + textToPaste + currentText.substring(caretPos);

        textArea.setText(newText);
        textArea.setCaretPosition(caretPos + textToPaste.length());

        syncToLinkedList();
        updateStatus();

        JOptionPane.showMessageDialog(this,
                "Berhasil menempel: \"" + textToPaste + "\"\n" +
                        "Clipboard tersisa: " + clipboardQueue.size() + " item",
                "Sukses",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearAll() {
        if (textArea.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Dokumen sudah kosong.",
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Hapus semua isi dokumen?",
                "Konfirmasi Clear",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            Command cmd = createSnapshot("clear");
            undoStack.push(cmd);
            redoStack.clear();

            lines.clear();
            textArea.setText("");
            updateStatus();

            JOptionPane.showMessageDialog(this,
                    "Dokumen berhasil dikosongkan.",
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void showClipboard() {
        if (clipboardQueue.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Clipboard kosong.",
                    "Clipboard History",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== Riwayat Clipboard (Terlama → Terbaru) ===\n\n");

        int num = 1;
        for (String item : clipboardQueue) {
            sb.append(num).append(". ");

            if (item.length() > 50) {
                sb.append(item.substring(0, 47)).append("...");
            } else {
                sb.append(item);
            }

            sb.append("\n");
            num++;
        }

        sb.append("\nTotal: ").append(clipboardQueue.size()).append("/").append(MAX_CLIPBOARD).append(" item");

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(this,
                scrollPane,
                "Clipboard History",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateStatus() {
        String text = textArea.getText();
        int lineCount = text.isEmpty() ? 0 : text.split("\n", -1).length;
        int charCount = text.length();
        int clipboardCount = clipboardQueue.size();

        statusLabel.setText(
                "Baris: " + lineCount + " | " +
                        "Karakter: " + charCount + " | " +
                        "Clipboard: " + clipboardCount + "/" + MAX_CLIPBOARD);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TextEditorGUI editor = new TextEditorGUI();
            editor.setVisible(true);
        });
    }

    private void syncToLinkedList() {
        lines.clear();
        String text = textArea.getText();
        if (text.isEmpty()) {
            return;
        }

        String[] linesArray = text.split("\n", -1);
        for (String line : linesArray) {
            lines.add(line);
        }
    }

    private void syncFromLinkedList() {
        if (lines.isEmpty()) {
            textArea.setText("");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lines.size(); i++) {
            sb.append(lines.get(i));
            if (i < lines.size() - 1) {
                sb.append("\n");
            }
        }
        textArea.setText(sb.toString());
    }

    private Command createSnapshot(String action) {
        syncToLinkedList();

        String data = String.join("\n", lines);
        return new Command(action, data, 0);
    }

    private void restoreSnapshot(Command cmd) {
        String data = cmd.getData();
        lines.clear();

        if (data != null && !data.isEmpty()) {
            String[] linesArray = data.split("\n", -1);
            for (String line : linesArray) {
                lines.add(line);
            }
        }

        syncFromLinkedList();
        updateStatus();
    }

}