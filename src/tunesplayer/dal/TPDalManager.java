/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunesplayer.dal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tunesplayer.be.TPSongs;

/**
 *
 * @author luttejensen
 */
public class TPDalManager 
{   
   /**
    * save songs on file
    * @param songs 
    */
   public void saveSongs(TPSongs songs)
   {
       String songsString = "";
       String fileName = songs.getTitle() + ".sng";
       File file = new File("DataFiler/Songs/" + fileName);
       
       System.out.println(file.getAbsolutePath());
       
       if (!file.exists())      // tjekker om vi allerede har sangen og hvis vi har så gemmer vi den ikke
         {
            try 
             {
               file.createNewFile();
             } 
            catch (IOException ex) 
             {
               Logger.getLogger(TPDalManager.class.getName()).log(Level.SEVERE, null, ex);
             }           
          }
        songsString += songs.getTitle()             // her fortæller vi hvilke parameter vores sang skal have
                        + ","
                        + songs.getArtist()
                        + ","
                        + songs.getSongPath();
        
        //  her bruger vi bufferedwriter til at gemme sangen og det gør vi ved hjælp af FileWriter
            try (BufferedWriter bufWriter = new BufferedWriter(new FileWriter("DataFiler/Songs/" + fileName)))
             {
                bufWriter.write(songsString);   
             } 
            catch (IOException ex) 
             {
                Logger.getLogger(TPDalManager.class.getName()).log(Level.SEVERE, null, ex);
             }
    }
   
   /**
    * get songs from files and return them in a list, 
    * is to be used in the controler dal-bll-gui-controler
     * @return 
     * @throws java.io.IOException 
    */
   public List<TPSongs> allSongsOnFile() throws IOException
        {
            File dir1 = new File("DataFiler/Songs");    // her henter vi de gemte sange
                if (!dir1.exists())                     //skal være her, ellers virker koden ikke, af for mig ukendte grunde
                {
                    dir1.mkdir();                  
                }
            
            List<TPSongs> allSongsOnFile = new ArrayList<>();   //liste til at samle alle sangene
            
            Path dir = Paths.get("DataFiler/Songs/");           //her finder vi sangene
              try (DirectoryStream<Path> stream =  Files.newDirectoryStream(dir, "*.sng"))  //finder alle sange
                {
                    for (Path entry : stream) 
                    {
                        try (BufferedReader buffGetSongs = new BufferedReader(new FileReader("DataFiler/Songs/" + entry.getFileName())))
                        {
                            String dataLinje = buffGetSongs.readLine();     // her læser vi en linje
                            String[] splitData = dataLinje.split(",");      // og her splitter vi den op
                            
                            String title = splitData[0];                    // vi splitter linjen op i 3
                            String artist = splitData[1];
                            String songPath = splitData[2];
                            
                            TPSongs tpSongs = new TPSongs(title, artist, songPath);     // her sætter vi en ny sang
                            allSongsOnFile.add(tpSongs);                                //og den sendes til vores liste
                        }
                    }   
                }
            return allSongsOnFile;              // her returnere vi alle de sange vi har fundet
        }
    
  
}