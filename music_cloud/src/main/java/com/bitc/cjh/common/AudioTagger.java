package com.bitc.cjh.common;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.images.Artwork;

import lombok.Data;

@Data
public class AudioTagger implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -751015957563019002L;
	
	AudioFile inputFile;
	Tag tag;
	Artwork cover;
	
	String title;
	String artist;
	String album;
	String year;
	int genre;
	
	String thumb_tmp_path;
	
	public void getAllTag() {
		tag = inputFile.getTag();
		
		cover = tag.getFirstArtwork();
		
		title  = tag.getFirst(FieldKey.TITLE);
        artist = tag.getFirst(FieldKey.ARTIST);
        album  = tag.getFirst(FieldKey.ALBUM);
        year   = tag.getFirst(FieldKey.YEAR);
        
        switch (tag.getFirst(FieldKey.GENRE)) {
        
	        case "Ballad" :
	        	genre = 1;
	        	break;
	        case "Rap" :
	        	genre = 2;
	        	break;
	        case "Pop" :
	        	genre = 3;
	        	break;
	        case "Dance" :
	        	genre = 4;
	        	break;
	        default : 
	        	genre = 5;
	        	break;
        }
	}
	

	public AudioTagger(File inputFile) {
		try {
			
			this.inputFile = AudioFileIO.read(inputFile);
			getAllTag();
			
		} catch (CannotReadException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TagException e) {
			e.printStackTrace();
		} catch (ReadOnlyFileException e) {
			e.printStackTrace();
		} catch (InvalidAudioFrameException e) {
			e.printStackTrace();
		}
	}
}
