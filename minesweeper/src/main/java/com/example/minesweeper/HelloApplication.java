package com.example.minesweeper;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.IdentityHashMap;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        int size_x = 15;
        int size_y = 15;
        HBox root = new HBox();
        root.setMinSize(size_x*35,size_y*35);
        FlowPane mine_field = new FlowPane();
        mine_field.setVgap(0);
        mine_field.setMaxSize(size_x*35,size_y*35);
        mine_field.setMaxSize(size_x*35,size_y*35);

        mine_field.setAlignment(Pos.CENTER);
         mine mine_arr[][] = new mine[size_x][size_y];
          mine_arr = initialize_field(stage,size_x,size_y);

        for (int i = 0; i < size_x; i++) {
            for (int j = 0; j < size_y; j++) {
                StackPane stack = new StackPane(mine_arr[i][j].but);
                mine_field.getChildren().add(stack);
            }
        }

        for (int i = 0; i < 20; i++) {
            ThreadLocalRandom random = ThreadLocalRandom.current();
            int x = 0;
            int y = 0;
            x = random.nextInt(0, size_x);
            y = random.nextInt(0, size_y);
            mine_arr[x][y].is_mine=true;
        }

        for (int i = 0; i < size_x; i++) {
            for (int j = 0; j < size_y; j++) {

                for (int k = i-1; k <= i+1; k++) {
                    for (int l = j-1; l <= j+1 ; l++) {
                            if (k>-1 && k<size_x && l>-1 && l<size_y &&mine_arr[k][l].is_mine) {
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
    private mine[][] initialize_field(Stage stage,int pos_x,int pos_y){
        mine[][] mine_arr = new mine[pos_x+1][pos_y+1];

        for (int i = 0; i <= pos_x; i++) {
            for (int j = 0; j <= pos_y; j++) {
                Button but_tmp = initializeButton(mine_arr,i,j,stage);

                mine_arr[i][j] = new mine(0,false,false,but_tmp);

            }
        }
        return mine_arr;
    }

   private Button initializeButton(mine mine_arr[][], int i1,int j1,Stage stage) {
Button but_tmp = new Button();
        but_tmp.setMaxSize(35,35);
        but_tmp.setMinSize(35,35);
        but_tmp.setOnMouseClicked(e ->{
            if(e.getButton() == MouseButton.PRIMARY && !mine_arr[i1][j1].is_flagged) {
                if (mine_arr[i1][j1].is_mine){
                    but_tmp.setText("bum");
                    Alert close = new Alert(Alert.AlertType.INFORMATION);
                    close.show();
                    close.setContentText("your luck has run out");
                    stage.close();
                }
                if (!mine_arr[i1][j1].is_mine){
                    but_tmp.setText(String.valueOf(mine_arr[i1][j1].mines_around));
                }
            }
            else if (e.getButton() == MouseButton.SECONDARY){
                if (!mine_arr[i1][j1].is_flagged){
                    mine_arr[i1][j1].but.setText("F");
                    mine_arr[i1][j1].is_flagged=true;
                }
                else {
                    mine_arr[i1][j1].is_flagged=false;
                    mine_arr[i1][j1].but.setText("");

                }

            }
        });
        return but_tmp;
    }


    public static void main(String[] args) {
        launch();
    }
}
