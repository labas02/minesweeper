package com.example.minesweeper;

import javafx.scene.control.Button;

public class mine {
    int mines_around;
    boolean is_revealed;
    boolean is_mine;
    Button but;

    public mine(int mines_around, boolean is_revealed, boolean is_mine, Button but) {
        this.mines_around = mines_around;
        this.is_revealed = is_revealed;
        this.is_mine = is_mine;
        this.but = but;
    }
}

