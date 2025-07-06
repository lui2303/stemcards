package com.luiswiederhold.backend.flashcards;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.TranscoderOutput;

@Service
public class MathPixService {
    @Value("${mathpix.app-id}")
    private String mathpixApiAppID;

    @Value("${mathpix.api.url}")
    private String mathpixApiUrl;

    @Value("${mathpix.api.key}")
    private String mathpixApiKey;

    private final RestTemplate restTemplate;

    public MathPixService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String convertToLatex(MultipartFile file) throws Exception {
        JPEGTranscoder transcoder = new JPEGTranscoder();
        transcoder.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, 0.95f);
        byte[] imageBytes;

        try (ByteArrayOutputStream jpgOutputStream = new ByteArrayOutputStream()) {
            TranscoderInput input = new TranscoderInput(file.getInputStream());
            TranscoderOutput output = new TranscoderOutput(jpgOutputStream);

            transcoder.transcode(input, output);
            imageBytes = jpgOutputStream.toByteArray();
        } catch (Exception e) {
            return null;
        }

        String base64 = Base64.getEncoder().encodeToString(imageBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.set("app_id", mathpixApiAppID);
        headers.set("app_key", mathpixApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonBody = String.format(
                "{\"src\": \"data:%s;base64,%s\", \"formats\": [\"latex_simplified\"]}",
                "image/jpeg", base64
        );

        return new RestTemplate()
                .postForObject(
                        mathpixApiUrl,
                        new HttpEntity<>(jsonBody, headers),
                        String.class
                );
    }
}