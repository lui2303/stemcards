package com.luiswiederhold.backend.flashcards.imagestorage;

import com.luiswiederhold.backend.flashcards.Flashcard;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Primary // change to @Profile("test") later
public class DiskImageStorageService implements ImageStorageService {
    private void checkArgumentValidity(String username, String hierachy){
        assert hierachy != null;
        assert username != null;
    }
    @Override
    public URI constructFlashcardImageURI(Long ID, String username, String hierachy, boolean isAnswer) throws URISyntaxException {
        checkArgumentValidity(username, hierachy);
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(DiskImageStorageServiceConfig.STORAGE_LOCATION);
        stringBuilder.append("/");
        stringBuilder.append(username);

        hierachy = hierachy.replace("::", "/");

        if (hierachy.equals("/")) {
            stringBuilder.append("/");
        } else {
            stringBuilder.append("/");
            stringBuilder.append(hierachy);
            stringBuilder.append("/");
        }

        stringBuilder.append(ID);
        stringBuilder.append("/");

        if (isAnswer) stringBuilder.append("answer/image.png");
        else stringBuilder.append("question/image.png");

        return Paths.get(stringBuilder.toString()).toUri();
    }

    @Override
    public URI storeFlashcardContent(MultipartFile image, URI targetURI) {
        File file = new File(targetURI);

        if (!file.exists()) {
            try {
                Files.createDirectories(Path.of(file.getParent()));
                if(file.createNewFile()) System.out.println("Stored image under: " + targetURI);;
            } catch (IOException e) {
                System.out.println(e);
            }
        }

        try {
            image.transferTo(file);
        } catch (IOException e) {
            System.out.println(e);
        }

        return targetURI;
    }

    @Override
    public Flashcard getFlashcard(Long ID) {
        return null;
    }

    // TODO: Add logging to the application

}
