/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunesplayer.be;

/**
 *
 * @author luttejensen
 */
public class TPSongs 
{
    private String title;
    private String artist;
    private String songPath;

    /**
     * construct songs and assign them parameters
     * @param title
     * @param artist
     * @param songPath 
     */
    public TPSongs(String title, String artist, String songPath) 
    {
        this.title = title;
        this.artist = artist;
        this.songPath = songPath;
    }

    // setters og getters for the song parameters
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSongPath() {
        return songPath;
    }

    public void setSongPath(String songPath) {
        this.songPath = songPath;
    }
    
   
}
