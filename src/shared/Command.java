package shared;

public class Command {
    private String action;
    private String data;
    private int lineNumber;

    public Command(String action, String data, int lineNumber) {
        this.action = action;
        this.data = data;
        this.lineNumber = lineNumber;
    }

    public String getAction() {
        return action;
    }

    public String getData() {
        return data;
    }

    public int getLineNumber() {
        return lineNumber;
    }

}