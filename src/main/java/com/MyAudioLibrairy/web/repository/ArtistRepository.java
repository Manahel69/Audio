package com.MyAudioLibrairy.web.repository;


import com.MyAudioLibrairy.web.model.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Long> {



    Artist findByName(String name);
    //List<Artist> findByName(String name);
    List<Artist> findByNameIgnoreCase(String name);
    Page<Artist> findByNameContains(String name, Pageable pageable);

    }




