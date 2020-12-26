package com.MyAudioLibrairy.web.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/artists")
public class ArtistController {
        @GetMapping(value = "/")
        public String index(final ModelMap model) {
                model.put("nom", "IPI");
                return "accueil";
        }


}

