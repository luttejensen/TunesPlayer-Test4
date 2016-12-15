/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunesplayer.gui.controller;

//import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tunesplayer.be.TPSongs;
import tunesplayer.gui.model.TPModelManager;


/**
 *
 * @author luttejensen
 */
public class TunesPlayerViewController implements Initializable 
{
    private final TPModelManager modelManager;
    
    private String selectedSong;
    private File file;
    private ObservableList<TPSongs> songLibary;
    
    private Media me;
    private MediaPlayer mp;

    @FXML
    private TableView<TPSongs> tblVLibary;
    @FXML
    private TableColumn<TPSongs, String> tclColumnTitle;
    @FXML
    private TableColumn<TPSongs, String> tblColumnArtist;    
    @FXML
    private Label lblNowPlaying;
    @FXML
    private Button playPauseBTN;
    @FXML
    private Label lblStatus;
    @FXML
    private Button btnAddNewSong;
       
    
    public TunesPlayerViewController()
    {
        modelManager = new TPModelManager();
    }
    
    /**
     * 
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        //loadSongsToLibary();   // brugt indtil jeg fik songs to libary til at virke      
        try 
        {
            songsToLibary();
            
        } catch (IOException ex) {
            Logger.getLogger(TunesPlayerViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tclColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tblColumnArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
            
        setStartSong();

    }    
    
     /**
      *  -----> brugt indtil jeg fandt ud af at hente sange fra mapper
     * get all songs 
     * 
     */
//    private void loadSongsToLibary()
//    {
//        
//        songLibary = FXCollections.observableArrayList
//        (
//                new TPSongs("Human", "Ukendt", "sange/Human.mp3"),
//                new TPSongs("Rasputin", "BoneyM", "sange/Rasputin.mp3"),
//                new TPSongs("Lift me up", "Moby", "sange/LiftMeUp.mp3")
//        );
//        
//        tblVLibary.setItems(songLibary); 
//         tclColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
//         tblColumnArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
//    } 
    
    /**
     * get all songs from files, from dal-bll-gui/view
     * arrange them in a observablelist
     * @throws IOException 
     */
    private void songsToLibary() throws IOException
    {
        ObservableList<TPSongs> songsFromFileToLibary = FXCollections.observableArrayList(modelManager.allSongsOnFile());
        tblVLibary.setItems(songsFromFileToLibary);
    }
    
   /**
    * play song when play is pressed and pause it when pause is pressed
    * change the symbol acordanly to the state
    * @param event 
    */
    @FXML
    private void playButton(ActionEvent event) 
    {          
        if (mp.getStatus() == MediaPlayer.Status.STOPPED)
        {
            if (tblVLibary.getSelectionModel().isEmpty())
            {
                playFirstSongInTable();
            }
            else
            {
                playSongWithMouse();
            }
        }
        else if (mp.getStatus() == MediaPlayer.Status.PLAYING)
        {
            mp.pause();
             lblStatus.setText("Song is paused");
            playPauseBTN.setText("Play");
        }
        else
        {
            
            mp.play(); 
             lblStatus.setText("Now Playing");
            playPauseBTN.setText("Pause");
        }
    }
    
    
    /**
     * play song when mouse is double clicked
     * @param event 
     */
    @FXML
    private void doubleClickMouse(MouseEvent event) 
    {    
        if(event.getClickCount()== 2)
         {
              mp.stop();
              playSongWithMouse(); 
         } 
     }
    
     /**
     * stop song when stopbutton is pressed and reset it to start
     * @param event 
     */
    @FXML
    private void stopButton(ActionEvent event) 
    {
        mp.stop();
        playPauseBTN.setText("Play");
        lblStatus.setText("Song is stopped"); 
    }
    
    /**
     * close application when close is pressed
     */
    @FXML
    private void exitButton(ActionEvent event) 
    {
        System.exit(1);
    }
     
    /**
     * get selectedSongWithMouse and assign it to a new media
     */
    private void playSongWithMouse()
    {
        file = new File(getSelecSongWithMouse().getSongPath());
         me = new Media(file.toURI().toString());
         mp = new MediaPlayer(me);
         mp.play();
         playPauseBTN.setText("Pause");
         lblStatus.setText("Now Playing");
         lblNowPlaying.setText(
                 getSelecSongWithMouse().getTitle() 
                 + " \t"
                 + "Performed by: "
                 + getSelecSongWithMouse().getArtist()); 
    }
    
    /**
     * return selected song in songLibary
     * @return 
     */
    private TPSongs getSelecSongWithMouse() 
    {
        return tblVLibary.getSelectionModel().getSelectedItem();
    }
    
     /**
     * set song to play when the application is started
     */
    private void setStartSong()
    {
        
        if (tblVLibary.getItems().isEmpty())
        {
            selectedSong = null;
        }
        else
        {
          playFirstSongInTable();
        }
    }  
    
    /**
     * get first song in table and assign it to the media
     */
    private void playFirstSongInTable()
    {
            me = new Media(new File(getFirstSongInTable()).toURI().toString());
            mp = new MediaPlayer(me);
            mp.play();
            playPauseBTN.setText("Pause");
            lblStatus.setText("Now Playing");
            lblNowPlaying.setText(
                    tblVLibary.getItems().get(0).getTitle() 
                    + "\t " 
                    + "Performed by: "
                    + tblVLibary.getItems().get(0).getArtist());
     }
    
    /**
     * return the first song in the table
     */
    private String getFirstSongInTable()
    {
        return tblVLibary.getItems().get(0).getSongPath();
    } 
    
    /**
     * opens the window where a new song can be added
     * if a new song is added, we send it to the songlibary
     * opens a new window
     * @param event 
     */
     @FXML
    private void addNewSong(ActionEvent event) throws IOException
    {
        try 
        {
            newWindowAddSong("/tunesplayer/gui/view/TPaddSongsToLibary.fxml");
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(TunesPlayerViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      songsToLibary();
    }
    /**
     * opens a new window, where songs can be selected to save on file
     * @param as
     * @throws IOException 
     */
    private void newWindowAddSong(String as) throws IOException
    {
        Stage primaryStage = (Stage) btnAddNewSong.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(as));
        
        Parent root = loader.load();
        
        Stage addSongs = new Stage();
        addSongs.initStyle(StageStyle.TRANSPARENT);
        addSongs.setScene(new Scene(root, Color.TRANSPARENT));      
        addSongs.initModality(Modality.WINDOW_MODAL);
        addSongs.initOwner(primaryStage);
        addSongs.showAndWait();         // let the song continue playing while we look for more songs
    }
}
