package com.MyAudioLibrairy.web.repository;


import com.MyAudioLibrairy.web.model.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ArtistRepository extends JpaRepository<Artist, Long> {



    Artist findByName(String name);
    Page<Artist> findByNameContains(String name, Pageable pageable);

    }




