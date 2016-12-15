/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunesplayer.gui.model;

import java.io.IOException;
import java.util.ArrayList;
import tunesplayer.be.TPSongs;
import tunesplayer.bll.TPBllManager;

/**
 *
 * @author luttejensen
 */
public class TPModelManager 
{
   private final TPBllManager bllManager;
   
   
   /**
    * constructor
    * arrange the saved songs in a new observable arraylist
    */
   public TPModelManager()
   {
       bllManager = new TPBllManager();
   }
   
   /**
    * get all songs from file from dal-bll
    * 
    * @return
    * @throws IOException 
    */
   public ArrayList<TPSongs> allSongsOnFile() throws IOException
   {
       return bllManager.allSongsOnFile();
   }
   
   /**
    * send a song we saved to bllmanager - dalmanager 
    * @param songs 
    */
   public void saveSongs(TPSongs songs)
   {
       bllManager.saveSongs(songs);
   }
   
}


