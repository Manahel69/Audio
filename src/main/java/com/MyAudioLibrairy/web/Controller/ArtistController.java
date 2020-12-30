package com.MyAudioLibrairy.web.Controller;


import com.MyAudioLibrairy.web.model.Album;
import com.MyAudioLibrairy.web.model.Artist;
import com.MyAudioLibrairy.web.repository.AlbumRepository;
import com.MyAudioLibrairy.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;


@Controller
@RequestMapping("/artists")
public class ArtistController {


        @Autowired
        private ArtistRepository artistRepository;

        @Autowired
        private AlbumRepository albumRepository;

        @RequestMapping(method = RequestMethod.GET)
        public String index(final ModelMap model) {
                return "accueil";
        }

        @RequestMapping(method = RequestMethod.GET, value = "/{idArtist}")
        public String getArtistById(final ModelMap model, @PathVariable Long idArtist) {
                Optional<Artist> artist = artistRepository.findById(idArtist);
                artist.ifPresent(value -> model.put("artist", value));
                return "detailArtist";
        }

        @RequestMapping(method = RequestMethod.GET, value = "/artists", params = "name")
        public String getArtistByName(final ModelMap model, @RequestParam String name, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "name") String sortProperty, @RequestParam(defaultValue = "ASC") String sortDirection) {
                Page<Artist> artists = artistRepository.findByNameContains(name, PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortProperty));
                if(!artists.isEmpty()) {
                        model.put("artists", artists);
                        model.put("start", (page) * size + 1);
                        model.put("end", (page) * size + artists.getNumberOfElements());
                        model.put("totalArtist", artists.getTotalElements());
                        model.put("previousPage", page - 1);
                        model.put("currentPage", page);
                        model.put("nextPage", page + 1);
                        model.put("isLastPage", artists.isLast());
                } else {
                        model.put("start", 0);
                        model.put("end", 0);
                        model.put("totalArtist", 0);
                        model.put("previousPage", 0);
                        model.put("currentPage", 0);
                        model.put("nextPage", 0);
                        model.put("isLastPage", true);
                }
                return "listeArtists";
        }

        @RequestMapping(method = RequestMethod.GET, value = "/artists")
        public String getArtistPage(final ModelMap model, @RequestParam Integer page, @RequestParam Integer size, @RequestParam String sortProperty, @RequestParam String sortDirection) {
                Page<Artist> artists = artistRepository.findAll(PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortProperty));
                if(!artists.isEmpty()) {
                        model.put("artists", artists);
                        model.put("start", (page) * size + 1);
                        model.put("end", (page) * size + artists.getNumberOfElements());
                        model.put("totalArtist", artists.getTotalElements());
                        model.put("previousPage", page - 1);
                        model.put("currentPage", page);
                        model.put("nextPage", page + 1);
                        model.put("isLastPage", artists.isLast());
                } else {
                        model.put("start", 0);
                        model.put("end", 0);
                        model.put("totalArtist", 0);
                        model.put("previousPage", 0);
                        model.put("currentPage", 0);
                        model.put("nextPage", 0);
                        model.put("isLastPage", true);
                }

                return "listeArtists";
        }

        @RequestMapping(method = RequestMethod.GET, value = "new")
        public String getPageCreationArtist(final ModelMap model) {
                model.put("artist", new Artist());

                return "detailArtist";
        }

        @RequestMapping(method = RequestMethod.POST, value = "/update")
        public String saveArtist(final ModelMap model, Artist artistCreated) {

                artistRepository.save(artistCreated);
                model.put("artist", artistCreated);
                return "detailArtist";
        }

        @RequestMapping(method = RequestMethod.GET, value = "/{idArtist}/delete")
        public RedirectView deleteArtist(@PathVariable Long idArtist) {
                Optional<Artist> artist = artistRepository.findById(idArtist);
                if(artist.isPresent()) { //Si l'artiste est présent alors il existe
                        artistRepository.delete(artist.get());
                        return new RedirectView("/artists?page=0&size=10&sortProperty=name&sortDirection=ASC");
                } else {
                        //404
                        return new RedirectView("/artists");
                }
        }

        @RequestMapping(method = RequestMethod.POST, value = "/{idArtist}/album")
        public RedirectView createAlbum(/*final ModelMap model, */@PathVariable Long idArtist, Album album) {
                Optional<Artist> artist = artistRepository.findById(idArtist);
                if(artist.isEmpty()) {
                        //L'artiste n'existe pas donc on redirige vers la liste des artistes
                        return new RedirectView("/artists?page=0&size=10&sortProperty=name&sortDirection=ASC");
                }

                if(!album.getTitle().isEmpty()) {

                        albumRepository.save(album);

                }

                return new RedirectView("/artists/"+artist.get().getId());
        }

        @RequestMapping(method = RequestMethod.GET, value = "/{idArtist}/album/delete/{idAlbum}")
        public RedirectView deleteAlbum(@PathVariable Long idArtist, @PathVariable Long idAlbum) {
                Optional<Artist> artistOptional = artistRepository.findById(idArtist);
                Optional<com.MyAudioLibrairy.web.model.Album> albumOptional = albumRepository.findById(idAlbum);
                if(artistOptional.isEmpty()) {
                        return new RedirectView("/artists?page=0&size=10&sortProperty=name&sortDirection=ASC");
                }

                albumOptional.ifPresent(album -> albumRepository.delete(album));


                return new RedirectView("/artists/"+artistOptional.get().getId());
        }
}




