package com.MyAudioLibrairy.web.repository;


import com.MyAudioLibrairy.web.model.Album;
import com.MyAudioLibrairy.web.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Artist findByName(String name);
    //List<Artist> findByName(String name);
    List<Artist> findByNameIgnoreCase(String name);

}
