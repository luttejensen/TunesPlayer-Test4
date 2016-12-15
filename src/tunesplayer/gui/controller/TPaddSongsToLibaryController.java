/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunesplayer.gui.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tunesplayer.be.TPSongs;
import tunesplayer.gui.model.TPModelManager;



/**
 * FXML Controller class
 *
 * @author luttejensen
 */
public class TPaddSongsToLibaryController implements Initializable 
{
    private FileChooser filechooser;
    private final File openMappePC = new File("sange");
    private TPModelManager modelManager;
    private TPSongs tpSongs;
    
    @FXML
    private TextField txtFSongTitle;
    @FXML
    private TextField txtFiSongArtist;
    @FXML
    private TextField txtFSongPath;

    private TPSongs thisTitle;
    private TPSongs thisArtist;
    private TPSongs thisSongPath;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    /**
     * let us choose a mp3 song from a file
     * set to a specifick folder, but any folder will do
     * @param event 
     */
    @FXML
    private void chooseSongFromFile(ActionEvent event) 
    {
        filechooser = new FileChooser();
        filechooser.setInitialDirectory(openMappePC);
        filechooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MP3 Files", "*.mp3"));
        File file = filechooser.showOpenDialog(null);
        if (file != null)
        {
            String path = file.getAbsolutePath();
            path = path.replace("\\", "/");
            txtFSongPath.setText(path);
        }
    }
    
    /**
     * save song and close the window
     * @param event 
     */
    @FXML
    private void saveSong(ActionEvent event) 
    {
        if (!txtFSongTitle.getText().isEmpty() && !txtFiSongArtist.getText().isEmpty() && !txtFSongPath.getText().isEmpty())
        {
            beforSaveSong();
        }
    }
    
    /**
     * assign properties to song befor vi save it
     */
      private void beforSaveSong() 
      {
          // get song information from textFields
          String title = txtFSongTitle.getText();       
          String artist = txtFiSongArtist.getText();
          String songPath = txtFSongPath.getText();
          songPath.replace("\\", "/");          // ellers kan programmet ikke læse filstien
          
          // set a new song and save it
          modelManager = new TPModelManager();
          tpSongs = new TPSongs(title, artist, songPath);
          modelManager.saveSongs(tpSongs);
                    
          // close this window and return to mainview
          Stage stage = (Stage) txtFSongPath.getScene().getWindow();
          stage.close();
          
      }
      
    /**
     * close this window and return to the music player
     * used for the cancel button
     * @param event 
     */
    @FXML
    private void exitTPAddSongsToLibary(ActionEvent event) 
    {
        Stage stage = (Stage) txtFSongPath.getScene().getWindow();
        stage.close();
    }
   
    
    // sets each parameter for a song
    
    public void setTxtFSongTitle(TPSongs title) 
    {
        thisTitle = title;
        txtFSongTitle.setText(thisTitle.getTitle());
    }

    public void setTxtFiSongArtist(TPSongs artist) 
    {
        thisArtist = artist;
        txtFiSongArtist.setText(thisArtist.getArtist());
    }

    public void setTxtFSongPath(TPSongs songPath) 
    {
        thisSongPath = songPath;
        txtFSongPath.setText(thisSongPath.getSongPath());
    }

} 
