package com.MyAudioLibrairy.web.model;

import javax.persistence.*;

@Entity
@Table
public class Album {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long AlbumId;
    private String title;
    private Long artistId;

    public Long getId() {
        return AlbumId;
    }

    public void setId(Long id) {
        AlbumId = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    @Override
    public String toString() {
        return "Album{" +
                "AlbumId=" + AlbumId +
                ", title='" + title + '\'' +
                ", artistId=" + artistId +
                '}';
    }

    public Album() {
    }

    public Album(String title, Long artistId) {
        this.title = title;
        this.artistId = artistId;
    }
}
