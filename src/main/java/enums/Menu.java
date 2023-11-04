package enums;

import lombok.Getter;

@Getter
public enum Menu {
    ADD("Add (A)"),
    TAKE("Take (T)"),
    SHOW("Show (S)"),
    EXIT("Exit (E)");

    private String action;

    Menu(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return action;
    }
}