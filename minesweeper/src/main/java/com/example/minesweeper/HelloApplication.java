package com.example.minesweeper;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import java.util.concurrent.ThreadLocalRandom;

public class HelloApplication extends Application {

    int mine_flagged=0;
    int mine_count = 25;
    int size_x = 15;
    int size_y = 15;
    mine mine_arr[][];

    TextField minecnt= new TextField("number of mines");
    TextField sizex = new TextField("sizex");
    TextField sizey = new TextField("sizey");
    public void start(Stage stage) {
        change_window(1,stage);
    }

    private void change_window(int window_number,Stage stage){

     switch (window_number){
         case 1:
             HBox iroot = new HBox();
             iroot.setMinSize(250,250);
             StackPane istack = new StackPane();
             istack.setMinSize(250,250);
             Button play = new Button();
             play.setTranslateX(50);
             play.setTranslateY(25);
             play.setText("play");
             play.setStyle("-fx-background-color:grey");
             play.setOnMouseClicked(e-> change_window(2,stage));
             Button quit = new Button();
             quit.setText("quit");
             quit.setTranslateX(-50);
             quit.setTranslateY(25);
             //TextField minecnt= new TextField("number of mines");
             Text minetxt = new Text("zadejte pocet min");
             minecnt.setTextFormatter(new TextFormatter<>((new NumberStringConverter())));
             minecnt.setTranslateY(-20);
             minetxt.setTranslateY(-40);
             //TextField sizex = new TextField("sizex");
             Text sizex_txt = new Text("zadejte x velikost");
             sizex.setTranslateY(-60);
             sizex_txt.setTranslateY(-80);
             sizex.setTextFormatter(new TextFormatter<>((new NumberStringConverter())));
             //TextField sizey = new TextField("sizey");
             Text sizey_txt = new Text("zadejte y velikost");
             sizey.setTextFormatter(new TextFormatter<>((new NumberStringConverter())));
             sizey_txt.setTranslateY(-120);
             sizey.setTranslateY(-100);
             istack.getChildren().addAll(sizex,minecnt,sizey,play,quit,minetxt,sizex_txt,sizey_txt);

             iroot.getChildren().add(istack);
             Scene scene2 = new Scene(iroot);
             stage.setScene(scene2);
             stage.show();
             break;
         case 2:
             mine_count=Integer.parseInt(String.valueOf(minecnt.getCharacters()));
             size_x= Integer.parseInt(String.valueOf(sizex.getCharacters()));
             size_y=Integer.parseInt(String.valueOf(sizey.getCharacters()));
             HBox root = new HBox();
             FlowPane mine_field = new FlowPane();

             mine_field.setPrefSize(size_y* 35, size_x * 35);

             mine_field.setAlignment(Pos.CENTER);
             mine_arr = initialize_field(stage,size_x,size_y);

             for (int i = 0; i < size_x; i++) {
                 for (int j = 0; j < size_y; j++) {
                     StackPane stack = new StackPane(mine_arr[i][j].but);
                     mine_field.getChildren().add(stack);
                 }
             }

             for (int i = 0; i < mine_count; i++) {
                 ThreadLocalRandom random = ThreadLocalRandom.current();
                 int x;
                 int y;
                 x = random.nextInt(0, size_x);
                 y = random.nextInt(0, size_y);
                 if (mine_arr[x][y].is_mine){
                     i--;
                 }else {
                     mine_arr[x][y].is_mine = true;
                 }
                 }


             for (int i = 0; i < size_y; i++) {
                 for (int j = 0; j < size_x; j++) {

                     for (int k = j-1; k <= j+1; k++) {
                         for (int l = i-1; l <= i+1 ; l++) {
                             if (k>-1 && k<size_x && l>-1 && l<size_y &&mine_arr[k][l].is_mine) {
                                 mine_arr[j][i].mines_around+=1;
                                 System.out.println(mine_arr[j][i].mines_around);
                             }
                         }
                     }
                 }
             }

             root.getChildren().add(mine_field);
             Scene scene = new Scene(root);
             stage.setScene(scene);
             stage.show();
             break;
         default:
             throw new IllegalStateException("Unexpected value: " + window_number);
     }
    }
    private mine[][] initialize_field(Stage stage,int pos_x,int pos_y){
        mine[][] mine_arr = new mine[pos_x+1][pos_y+1];

        for (int i = 0; i <= pos_x; i++) {
            for (int j = 0; j <= pos_y; j++) {
                Button but_tmp = initializeButton(i,j,stage);

                mine_arr[i][j] = new mine(0,false,false,but_tmp,false);

            }
        }
        return mine_arr;
    }

   private Button initializeButton( int i1,int j1,Stage stage) {
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
                    change_window(1,stage);
                }
                if (!mine_arr[i1][j1].is_mine){
                    but_tmp.setText(String.valueOf(mine_arr[i1][j1].mines_around));
                    if (mine_arr[i1][j1].mines_around==0){
                        reveal_button(i1,j1);
                    }
                }
            }

            else if (e.getButton() == MouseButton.SECONDARY){
                if (!mine_arr[i1][j1].is_flagged){
                    mine_arr[i1][j1].but.setText("F");
                    mine_arr[i1][j1].is_flagged=true;
                    mine_arr[i1][j1].but.setStyle("-fx-background-color: Red");
                    if (mine_arr[i1][j1].is_mine){
                        mine_flagged+=1;
                    }
                    if (mine_count==mine_flagged){
                        System.out.println(mine_flagged+" ----- "+mine_count);
                        Alert close = new Alert(Alert.AlertType.CONFIRMATION);
                        close.setContentText("you win");
                        close.show();
                        change_window(1,stage);

                    }
                }
                else {
                    mine_arr[i1][j1].is_flagged=false;
                    mine_arr[i1][j1].but.setText("");
                    mine_arr[i1][j1].but.setStyle("-fx-background-color: white");
                    if (mine_arr[i1][j1].is_mine){
                        mine_flagged-=1;
                    }
                }

            }
        });
        return but_tmp;
    }

private void reveal_button(int i1, int j1){
    if (i1<=0||j1<=0||i1>=size_x||j1>=size_y||mine_arr[i1][j1].is_revealed) {
        return;
    }
    mine_arr[i1][j1].is_revealed=true;
            for (int i = i1 - 1; i <= i1 + 1; i++) {
                for (int j = j1 - 1; j <= j1 + 1; j++) {

                    mine_arr[i][j].but.setText(String.valueOf(mine_arr[i][j].mines_around));
                    if (mine_arr[i][j].mines_around==0){
                        reveal_button(i,j);
                    }
                    if (!mine_arr[i][j].is_mine) {
                        mine_arr[i][j].is_revealed = true;
                    }
                }
        }


}

    public static void main(String[] args) {
        launch();
    }
}
