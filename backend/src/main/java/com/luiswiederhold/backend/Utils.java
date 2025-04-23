package com.luiswiederhold.backend;
public class Utils {
    public static boolean verifyFileContentType(MultipartFile file, List<String> expectedFileTypes) {

        String contentType = file.getContentType();
        for (String expectedContent: expectedFileTypes) {
            if (expectedContent.equalsIgnoreCase(contentType)) {
                return true;
            }
        }
        return false;
    }

}