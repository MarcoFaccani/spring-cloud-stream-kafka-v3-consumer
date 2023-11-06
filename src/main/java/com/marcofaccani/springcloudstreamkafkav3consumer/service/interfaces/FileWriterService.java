package com.marcofaccani.springcloudstreamkafkav3consumer.service.interfaces;

public interface FileWriterService {

  void writeToFileSystem(String path, String fileName, String extension, byte[] content);

}
