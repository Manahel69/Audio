package com.MyAudioLibrairy.web.exceptions;

public class ArtistException extends Throwable {

    public ArtistException(String message) {
        super(message);
        System.out.println(this.getMessage());
    }
}
