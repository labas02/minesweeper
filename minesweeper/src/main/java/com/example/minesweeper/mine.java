package com.example.minesweeper;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

public class mine {
    int mines_around;
    boolean is_flagged;
    boolean is_mine;
    Button but;
    ImageView flag_img;
    ImageView mine_img;

    public mine(int mines_around, boolean is_flagged, boolean is_mine, Button but, ImageView flag_img, ImageView mine_img) {
        this.mines_around = mines_around;
        this.is_flagged = is_flagged;
        this.is_mine = is_mine;
        this.but = but;
        this.flag_img = flag_img;
        this.mine_img = mine_img;
    }
}

