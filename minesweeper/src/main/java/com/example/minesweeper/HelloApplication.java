package com.example.minesweeper;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        HBox root = new HBox();
        FlowPane mine_field = new FlowPane();
        mine_field.setVgap(0);
        mine_field.setMinHeight(351);
        mine_field.setMaxWidth(351);
        mine_field.setAlignment(Pos.CENTER);
        mine[][] mine_arr = new mine[20][20];

        for (int i = 0; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                final int i1=i;
                final int j1=j;
                Button but_tmp = new Button();
                but_tmp.setMaxSize(35,35);
                but_tmp.setMinSize(35,35);
                but_tmp.setId(i+"-"+j);
                but_tmp.setOnMouseClicked(e ->{
                    if(e.getButton() == MouseButton.PRIMARY ) {
                        if (mine_arr[i1][j1].is_mine){
                            but_tmp.setText("bum");
                        }
                        if (!mine_arr[i1][j1].is_mine){
                            but_tmp.setText(String.valueOf(mine_arr[i1][j1].mines_around));
                        }
                    }
                });
                mine_arr[i][j] = new mine(0,false,false,but_tmp);
                mine_field.getChildren().add(mine_arr[i][j].but);
            }
        }
        for (int i = 0; i < 20; i++) {
            ThreadLocalRandom random = ThreadLocalRandom.current();
            int x = 0;
            int y = 0;
            x = random.nextInt(0, 10);
            y = random.nextInt(0, 10);
            System.out.println(x+" "+y);
            mine_arr[x][y].is_mine=true;
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                for (int k = i-1; k <= i+1; k++) {
                    for (int l = j-1; l <=j+1 ; l++) {
                            if (k>-1 && k<10 && l>-1 && l<10 &&mine_arr[k][l].is_mine) {
                                mine_arr[i][j].mines_around+=1;
                                System.out.println(mine_arr[i][j].mines_around);
                            }
                    }

                }
            }
        }


        root.getChildren().add(mine_field);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    public static void main(String[] args) {
        launch();
    }
}
