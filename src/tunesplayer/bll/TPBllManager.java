/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunesplayer.bll;

import java.io.IOException;
import java.util.ArrayList;
import tunesplayer.be.TPSongs;
import tunesplayer.dal.TPDalManager;

/**
 *
 * @author luttejensen
 */
public class TPBllManager 
{
  private final TPDalManager dalManager;
  
  public TPBllManager()
  {
      dalManager = new TPDalManager();
  }
  
  /**
   * a part of the way for a song to be saved
   * dal-bll-gui/model-gui/addsongcontroller
   * @param songs 
   */
  public void saveSongs(TPSongs songs)
  {
      dalManager.saveSongs(songs);
  }
  
    /**
     *get the songs from dalmanager and arrange them in a arraylist to be passed on
     * @return
     * @throws IOException
     */
    public ArrayList<TPSongs> allSongsOnFile() throws IOException
  {
       return (ArrayList) dalManager.allSongsOnFile();
  }
  
}
