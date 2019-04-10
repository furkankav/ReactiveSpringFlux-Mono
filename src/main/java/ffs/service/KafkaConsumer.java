package ffs.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

@Log
@Service
public class KafkaConsumer {
	
    @KafkaListener(topics = "${kafka.topic}", groupId = "group_id")
    public void consume(String message) throws IOException {
        log.info(String.format("#### -> Consumed message -> %s", message));
    }

}
