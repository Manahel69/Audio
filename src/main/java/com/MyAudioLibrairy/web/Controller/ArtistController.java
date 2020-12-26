package com.MyAudioLibrairy.web.Controller;

import com.MyAudioLibrairy.web.model.Album;
import com.MyAudioLibrairy.web.model.Artist;
import com.MyAudioLibrairy.web.repository.AlbumRepository;
import com.MyAudioLibrairy.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.websocket.server.PathParam;
import java.util.Optional;
@RestController
@RequestMapping("/artists")
public class ArtistController {
        @Autowired
        private ArtistRepository artistRepository;
        @Autowired
        private AlbumRepository albumRepository;
        @RequestMapping(method = RequestMethod.GET, value ="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        public Artist getArtist (@PathVariable(value= "id") Long id){

                Optional<Artist> optionalArtist = artistRepository.findById(id);
                if(optionalArtist.isEmpty()){
                        //404
                        throw new EntityNotFoundException("l'artiste d'identifiant " +id+  " n'a pas été trouvé.");
                }
                return optionalArtist.get();
        }

        @RequestMapping(method =  RequestMethod.GET,value="/name",produces = MediaType.APPLICATION_JSON_VALUE)
        public Artist searchByName(@RequestParam String name) {
                Artist artist = artistRepository.findByName(name);
                if (artist == null) {
                        throw new EntityNotFoundException("l'artiste de nom" + name + " n'a pas été trouvé");
                }
                return artist;
        }

        @RequestMapping(method =RequestMethod.GET, value = "",produces = MediaType.APPLICATION_JSON_VALUE)
        public Page<Artist> listArtists(
                @RequestParam Integer page,
                @RequestParam (defaultValue = "10") Integer size,
                @RequestParam (defaultValue = "name") String sortProperty,
                @RequestParam (value = "sortDirection", defaultValue = "ASC")String sortDirection)
        {
                if (page<0){
                        //400
                        throw new IllegalArgumentException("Le parametre paage doit être positif ou nul!");
                }
                if (size <=0 || size >50){
                        throw new IllegalArgumentException("le parametre size doit être compris entre 0 et 50");
                }
                if(!"ASC".equalsIgnoreCase(sortDirection)&& !"DESC".equalsIgnoreCase(sortDirection)){
                        throw new IllegalArgumentException("Le paramètre sortDirection doit valoir ASC ou DESC");
                }
                return artistRepository.findAll(PageRequest.of(page,size, Sort.Direction.fromString(sortDirection),sortProperty));
        }

        @RequestMapping(method = RequestMethod.POST, value = "",consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)

        public Artist createArtist(@RequestBody Artist artist){
                if (artistRepository.findByName(artist.getName()) !=null){
                        throw new EntityExistsException("Il y a déjà un artist de nom " + artist.getName());
                }

                return artistRepository.save(artist);

        }

        @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
        public Artist updateArtist(@PathVariable("id") Long id, @RequestBody Artist artist){
                if(!artistRepository.existsById(id)){
                        throw new EntityNotFoundException("L'artist d'identifiant " + id + " n'a pas été trouvé");
                }
                if(!id.equals(artist.getId())) {
                        throw new IllegalArgumentException("Requête invalide");
                }
                return artistRepository.save(artist);

        }

        @RequestMapping(method = RequestMethod.DELETE,value = "/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)//204

        public void deleteArtist(@PathVariable Long id){
                Artist artist = artistRepository.findById(id)
                        .orElseThrow(()->new EntityNotFoundException("l'artiste n'existe pas"));
                if(!albumRepository.findAllByArtistId(id).isEmpty()){
                        throw new EntityNotFoundException("Pour supprimer un manager, il faut que son équipe soit vide.");
                }
                artistRepository.deleteById(id);

        }




}

