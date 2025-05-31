package com.luiswiederhold.backend.flashcards.imagestorage;

import com.luiswiederhold.backend.flashcards.FlashCardController;
import com.luiswiederhold.backend.flashcards.Flashcard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Primary // change to @Profile("test") later
public class DiskImageStorageService implements ImageStorageService {
    private static final Logger logger = LoggerFactory.getLogger(DiskImageStorageService.class);

    private void checkArgumentValidity(String username, String hierachy){
        assert hierachy != null;
        assert username != null;
    }
    @Override
    public URI constructFlashcardImageURI(Long ID, String username, String hierachy, boolean isAnswer) throws URISyntaxException {
        logger.debug("Starting construction of FlashcardImage URI");
        checkArgumentValidity(username, hierachy);
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(DiskImageStorageServiceConfig.STORAGE_LOCATION); // TODO: make this relative
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

        if (isAnswer) stringBuilder.append("answer/image.svg");
        else stringBuilder.append("question/image.svg");

        URI result = Paths.get(stringBuilder.toString()).toUri();
        logger.debug("Constructed URI: " + result);
        return result;
    }

    @Override
    public URI storeFlashcardContent(MultipartFile image, URI targetURI) throws IOException {
        logger.debug("Storing flashcard content image now");
        File file = new File(targetURI);

        if (!file.exists()) {
            logger.debug("file with URI: " + targetURI + " doesn't exist");
                logger.debug("creating parent directories up to: " + file.getParent());
                Files.createDirectories(Path.of(file.getParent()));
                logger.debug("Created directories");

                logger.debug("Creating the image file at targetURI: " + targetURI);
                if(!file.createNewFile()) throw new IOException("Couldn't create file");
        }

            logger.debug("Transfering image contents to created file");
            image.transferTo(file);
            logger.debug("Transfer success");

        return targetURI;
    }

    @Override
    public Flashcard getFlashcard(Long ID) {
        return null;
    }

}
