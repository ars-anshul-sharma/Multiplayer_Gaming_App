//package com.app.game.service;
//
//import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
//import com.google.api.client.http.ByteArrayContent;
//import com.google.api.client.http.FileContent;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.services.drive.Drive;
//import com.google.api.services.drive.DriveScopes;
//import com.google.api.services.drive.model.File;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//import java.util.Collections;
//
//@Service
//public class GoogleDriveService {
//
//    private final String APPLICATION_NAME = "Your Application Name";
//    private final JsonFactory JSON_FACTORY = JsonFactory.getDefaultInstance();
//    private final String CREDENTIALS_FILE_PATH = "/META-INF/credentials.json"; // Path to your service account credentials JSON file
//
//    public Drive getDriveService() throws IOException, GeneralSecurityException {
//        final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
//        GoogleCredential credential = GoogleCredential.fromStream(getClass().getResourceAsStream(CREDENTIALS_FILE_PATH))
//                .createScoped(Collections.singleton(DriveScopes.DRIVE_FILE));
//        return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
//                .setApplicationName(APPLICATION_NAME)
//                .build();
//    }
//
//    public String uploadFile(java.io.File file, String mimeType) throws IOException, GeneralSecurityException {
//        Drive driveService = getDriveService();
//        File fileMetadata = new File();
//        fileMetadata.setName(file.getName());
//
//        FileContent mediaContent = new FileContent(mimeType, file);
//        File uploadedFile = driveService.files().create(fileMetadata, mediaContent)
//                .setFields("id")
//                .execute();
//        return uploadedFile.getId();
//    }
//
//    public String uploadFile(byte[] fileBytes, String fileName, String mimeType) throws IOException, GeneralSecurityException {
//        Drive driveService = getDriveService();
//        File fileMetadata = new File();
//        fileMetadata.setName(fileName);
//
//        ByteArrayContent mediaContent = new ByteArrayContent(mimeType, fileBytes);
//        File uploadedFile = driveService.files().create(fileMetadata, mediaContent)
//                .setFields("id")
//                .execute();
//        return uploadedFile.getId();
//    }
//}
