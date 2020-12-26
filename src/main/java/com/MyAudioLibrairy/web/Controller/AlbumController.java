package com.MyAudioLibrairy.web.Controller;

import com.MyAudioLibrairy.web.model.Album;
import com.MyAudioLibrairy.web.model.Artist;
import com.MyAudioLibrairy.web.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/albums")
public class AlbumController {
    @Autowired
    private AlbumRepository albumRepository;

    /*
    @RequestMapping(method = RequestMethod.POST, value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)

    public void createAlbum(@RequestBody String test) {
        System.out.println(test);
    }
    */
    @RequestMapping(method = RequestMethod.POST, value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Album createAlbum(@RequestBody CreateAlbum create) {

        if (albumRepository.findByTitle(create.title) != null) {
            throw new EntityExistsException("Il y a déjà un album de nom " + create.title);
        }

        return albumRepository.save(new Album(create.title,
                create.artist.id));

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)//204
    public void deleteAlbum(@PathVariable Long id) {
        if (!albumRepository.existsById(id)) {
            throw new EntityNotFoundException("L'album d'identifiant " + id + " n'a pas été trouvé");

        }
        albumRepository.deleteById(id);

    }


}
