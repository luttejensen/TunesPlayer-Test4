/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunesplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author luttejensen
 */
public class TunesPlayer extends Application {
    
    @Override
    public void start(Stage stage) throws Exception 
    {
        stage.initStyle(StageStyle.TRANSPARENT);
        Parent root = FXMLLoader.load(getClass().getResource("gui/view/TunesPlayerView.fxml"));
        
        Scene scene = new Scene(root, Color.TRANSPARENT);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
