package com.p2.microservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.p2.microservice.event.OrderPlaceEvent;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class OrderPlaceEventDeserializer implements Deserializer<OrderPlaceEvent> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    
    private static final Logger logger = LoggerFactory.getLogger(OrderPlaceEventDeserializer.class);

    public OrderPlaceEventDeserializer() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public OrderPlaceEvent deserialize(String topic, byte[] data) {
        try {
            String json = new String(data);
            logger.debug("Attempting to deserialize JSON: {}", json);
            return objectMapper.readValue(data, OrderPlaceEvent.class);
        } catch (Exception e) {
            logger.error("Error deserializing OrderEvent. Raw data: {}", new String(data), e);
            return null;
        }
    }

    @Override
    public void close() {
    }
}