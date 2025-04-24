package com.luiswiederhold.backend.flashcards;

import com.luiswiederhold.backend.flashcards.imagestorage.DiskImageStorageService;
import com.luiswiederhold.backend.flashcards.imagestorage.ImageStorageService;
import com.luiswiederhold.backend.flashcards.imagestorage.S3ImageStorageService;
import com.luiswiederhold.backend.flashcards.imagestorage.S3ImageStorageServiceTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FlashcardServiceTest {
    @Mock
    FlashcardRepository flashcardRepository;

    @Mock
    ImageStorageService imageStorageService;

    @InjectMocks
    private FlashcardService flashcardService;

    @Test
    void testStorageImageReal() throws IOException, URISyntaxException {
        // test the storage of a real image in /test/resources/images

        InputStream is = getClass().getResourceAsStream("/test-image.svg");
        byte[] svgBytes = StreamUtils.copyToByteArray(is);

        MultipartFile svgFile = new MockMultipartFile("file", "test-image.svg", "image/svg+xml", svgBytes);

        when(imageStorageService.constructFlashcardImageURI( 1L, "user1", "/", false)).thenReturn(new URI("notNull"));

        assertEquals("notNull", flashcardService.storeImage(svgFile, 1L, "user1", "/", false).toString()); // just check if the URI is not null as the path varies for different testing environments
    }

    @Test
    void testStoreImageEmpty() throws IOException {
        // simulate storage of empty file;

        MultipartFile emptyFile = Mockito.mock(MultipartFile.class);

        when(emptyFile.getSize()).thenReturn(0L);

        assertThrows(IllegalArgumentException.class, () -> {flashcardService.storeImage(emptyFile, 1L, "user1", "/", true);});

    }


    @Test
    void testStoreImageNull() throws URISyntaxException {
        // simulate storage of null file passed

        URI uri1 = flashcardService.storeImage(null, 1L, "user1", "/", true);
        URI uri2 = flashcardService.storeImage(null, 1L, "user1", "/", false);

        assertNull(uri1);
        assertNull(uri2);
    }

}
