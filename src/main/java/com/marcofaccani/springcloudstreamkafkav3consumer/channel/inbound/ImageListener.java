package com.marcofaccani.springcloudstreamkafkav3consumer.channel.inbound;

import java.util.function.Consumer;

import com.marcofaccani.springcloudstreamkafkav3consumer.service.interfaces.FileWriterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

@Configuration
@Log4j2
@RequiredArgsConstructor
public class ImageListener {

  private final FileWriterService fileWriterService;

  @Bean
  public Consumer<Message<byte[]>> listen() {
    return message -> {
      final int partition = (int) message.getHeaders().get(KafkaHeaders.RECEIVED_PARTITION);
      final long offset = (long) message.getHeaders().get(KafkaHeaders.OFFSET);
      log.info("Received new message from partition {} with offset {}", partition, offset);

      // In real life, we wouldn't hard-code these parameters but either read them from Kafka headers or get them from env vars or
      fileWriterService.writeToFileSystem("consumer/src/main/resources/files", "image", "png", message.getPayload());
    };
  }

}