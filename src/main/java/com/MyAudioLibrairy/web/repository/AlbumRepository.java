package com.MyAudioLibrairy.web.repository;

import com.MyAudioLibrairy.web.model.Album;
import com.MyAudioLibrairy.web.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    Album findByTitle(String title);
    List<Album> findAllByArtistId(Long artistId);



}
