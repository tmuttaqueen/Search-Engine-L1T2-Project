/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import static javafx.scene.paint.Color.color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Tanveer Muttaqueen
 */
public class MySearchWithGui extends Application
{

    Stage window;
    Scene scene1;
    MyEngine myengine;

    public MySearchWithGui()
    {
        myengine = new MyEngine();
    }

    public static void main(String[] args)
    {
        //new MySearchWithGui();
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception
    {
        window = primaryStage;
        window.setTitle("Anteros Engine");
        HBox mainlayout = new HBox();
        mainlayout.setAlignment(Pos.CENTER);
        mainlayout.setPadding(new Insets(25, 25, 25, 25));
        mainlayout.setSpacing(25);
        
        TextField searchField = new TextField();
        
        searchField.setMinWidth(500);

        mainlayout.getChildren().add(searchField);

        Button searchBtn = new Button("Search");
        mainlayout.getChildren().add(searchBtn);

        searchBtn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                myengine.search(searchField.getText());
                searchField.clear();
            }

        });

        searchField.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent keyEvent)
            {
                if (keyEvent.getCode() == KeyCode.ENTER)
                {
                    myengine.search(searchField.getText());
                    searchField.clear();
                    keyEvent.consume();
                }
            }
        });

        scene1 = new Scene(mainlayout, 650, 100);
        window.setScene(scene1);

        scene1.getStylesheets().add(MySearchWithGui.class.getResource("BinaryContent\\myjava.css").toExternalForm());
        window.show();
        //new ClientGui("fasf");

    }
}
