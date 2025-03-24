package com.luiswiederhold.backend.flashcards;

import com.luiswiederhold.backend.flashcards.DTOs.FlashcardSideDTO;
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
    @Override
    public URI constructFlashcardImageURI(FlashcardSideDTO flashcardSideDTO) throws URISyntaxException {
        if (flashcardSideDTO == null) throw new IllegalArgumentException("flashcardSideDTO must be not null");
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(DiskImageStorageServiceConfig.STORAGE_LOCATION);
        stringBuilder.append("/");
        stringBuilder.append(flashcardSideDTO.getUsername());

        String hierachyPath = flashcardSideDTO.getHierachy().replace("::", "/");

        if (flashcardSideDTO.getHierachy().equals("/")) {
            stringBuilder.append("/");
        } else {
            stringBuilder.append("/");
            stringBuilder.append(hierachyPath);
            stringBuilder.append("/");
        }

        stringBuilder.append(flashcardSideDTO.getID());
        stringBuilder.append("/");

        if (flashcardSideDTO.isAnswerSide()) stringBuilder.append("answer/image.png");
        else stringBuilder.append("question/image.png");

        return Paths.get(stringBuilder.toString()).toUri();
    }

    @Override
    public URI storeFlashcardContent(MultipartFile image, FlashcardSideDTO flashcardSideDTO) throws URISyntaxException {
        URI targetURI = constructFlashcardImageURI(flashcardSideDTO);
        File file = new File(targetURI);

        ;

        if (!file.exists()) {
            try {
                Files.createDirectories(Path.of(file.getParent()));
                file.createNewFile();
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

    // !!for testing purposes!!

}
