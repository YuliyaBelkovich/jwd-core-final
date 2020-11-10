package com.epam.jwd.core_final.context.menu;

import java.util.Objects;

public class KeyMenu {
    private Menu menu;
    private Integer input;

    public KeyMenu(Menu menu, Integer input) {
        this.menu = menu;
        this.input = input;
    }

    public Menu getMenu() {
        return menu;
    }

    public Integer getInput() {
        return input;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KeyMenu)) return false;
        KeyMenu keyMenu = (KeyMenu) o;
        return getMenu() == keyMenu.getMenu() &&
                getInput().equals(keyMenu.getInput());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMenu(), getInput());
    }


}
