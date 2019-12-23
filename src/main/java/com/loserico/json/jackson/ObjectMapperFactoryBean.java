package com.loserico.json.jackson;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.loserico.json.jackson.deserializer.LocalDateDeserializer;
import com.loserico.json.jackson.deserializer.LocalDateTimeDeserializer;
import com.loserico.json.jackson.serializer.LocalDateTimeSerializer;
import org.springframework.beans.factory.FactoryBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import java.util.HashSet;
import java.util.Set;

import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * ObjectMapper 工厂类
 * <p>
 * Copyright: Copyright (c) 2019-10-15 10:01
 * <p>
 * Company: Sexy Uncle Inc.
 * <p>
 *
 * @author Rico Yu  ricoyu520@gmail.com
 * @version 1.0
 */
public class ObjectMapperFactoryBean implements FactoryBean<ObjectMapper> {

	private Set<String> enumProperties = new HashSet<>();
	private boolean epochBased = true;

	@Override
	public ObjectMapper getObject() throws Exception {
		if (epochBased) {
			return epochMilisBasedObjectMapper();
		} else {
			return stringBasedObjectMapper();
		}
	}

	private ObjectMapper stringBasedObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		javaTimeModule.addSerializer(LocalDateTime.class, new com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer(ofPattern("yyyy-MM-dd HH:mm:ss")));
		javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(ofPattern("yyyy-MM-dd HH:mm:ss")));

		javaTimeModule.addSerializer(LocalDate.class, new com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer(ofPattern("yyyy-MM-dd")));
		javaTimeModule.addDeserializer(LocalDate.class, new com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer(ofPattern("yyyy-MM-dd")));

		javaTimeModule.addSerializer(LocalTime.class, new com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer(ofPattern("HH:mm:ss")));
		javaTimeModule.addDeserializer(LocalTime.class, new com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer(ofPattern("HH:mm:ss")));
		objectMapper.registerModule(javaTimeModule);

		objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		objectMapper.setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
				.withFieldVisibility(JsonAutoDetect.Visibility.ANY)
				.withGetterVisibility(JsonAutoDetect.Visibility.PUBLIC_ONLY)
				.withSetterVisibility(JsonAutoDetect.Visibility.PUBLIC_ONLY)
				.withCreatorVisibility(JsonAutoDetect.Visibility.NONE));

		return objectMapper;
	}

	private ObjectMapper epochMilisBasedObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		// formatter that accepts an epoch millis value
		DateTimeFormatter epochMilisFormatter = new DateTimeFormatterBuilder()
				.appendValue(ChronoField.INSTANT_SECONDS, 1, 19, SignStyle.NEVER)
				.appendValue(ChronoField.MILLI_OF_SECOND, 3)
				.toFormatter().withZone(ZoneOffset.ofHours(8));

		JavaTimeModule module = new JavaTimeModule();
		module.addSerializer(LocalDate.class, new com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer(ofPattern("yyyy-MM-dd")));
		module.addDeserializer(LocalDate.class, new LocalDateDeserializer());

		module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
		module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(epochMilisFormatter));

		module.addSerializer(LocalTime.class, new LocalTimeSerializer(ofPattern("HH:mm:ss")));
		objectMapper.registerModule(module);

		objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		objectMapper.setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
				.withFieldVisibility(JsonAutoDetect.Visibility.ANY)
				.withGetterVisibility(JsonAutoDetect.Visibility.PUBLIC_ONLY)
				.withSetterVisibility(JsonAutoDetect.Visibility.PUBLIC_ONLY)
				.withCreatorVisibility(JsonAutoDetect.Visibility.NONE));

		return objectMapper;
	}

	@Override
	public Class<ObjectMapper> getObjectType() {
		return ObjectMapper.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public Set<String> getEnumProperties() {
		return enumProperties;
	}

	public void setEnumProperties(Set<String> enumProperties) {
		this.enumProperties = enumProperties;
	}

	public boolean isEpochBased() {
		return epochBased;
	}

	public void setEpochBased(boolean epochBased) {
		this.epochBased = epochBased;
	}
}