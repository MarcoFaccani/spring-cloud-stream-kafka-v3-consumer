package com.marcofaccani.springcloudstreamkafkav3consumer.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import com.marcofaccani.springcloudstreamkafkav3consumer.service.interfaces.FileWriterService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class FileWriterServiceImpl implements FileWriterService {

  @Override
  public void writeToFileSystem(final String path, final String fileName, final String fileExtension, final byte[] content) {
    final var uuid = UUID.randomUUID().toString();
    final var fullPath = String.format("%s/%s-%s.%s", path, fileName, uuid, fileExtension);

    try (FileOutputStream fos = new FileOutputStream(fullPath)) {
      fos.write(content);
      fos.flush();
      log.info("File successfully written at {}", fullPath);
    } catch (IOException e) {
      log.error("Error writing {} to filesystem: Exception: {}", fullPath, e.getMessage());
      // throw exception and deal with Dead-Letter Queues configuration
    }
  }
}
