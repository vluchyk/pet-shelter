package enums;

import lombok.Getter;

@Getter
public enum Menu {
    ADD("Add (A)", "a"),
    TAKE("Take (T)", "t"),
    SHOW("Show (S)", "s"),
    EXIT("Exit (E)", "e");

    private String action;
    private String command;

    Menu(String action, String command) {
        this.action = action;
        this.command = command;
    }

    @Override
    public String toString() {
        return action;
    }
}