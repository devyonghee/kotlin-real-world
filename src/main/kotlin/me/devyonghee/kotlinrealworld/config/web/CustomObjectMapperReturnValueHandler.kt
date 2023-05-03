package me.devyonghee.kotlinrealworld.config.web

import com.fasterxml.jackson.annotation.JsonRootName
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpInputMessage
import org.springframework.http.HttpOutputMessage
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter


class DynamicRootValueJsonMessageConverter(
    objectMapper: ObjectMapper,
    rootValueObjectMapper: ObjectMapper,
) : HttpMessageConverter<Any> {

    private val delegate = MappingJackson2HttpMessageConverter(objectMapper)
    private val rootValueDelegate = MappingJackson2HttpMessageConverter(rootValueObjectMapper)

    override fun canRead(clazz: Class<*>, mediaType: MediaType?): Boolean {
        if (clazz.hasJsonRootNameAnnotation) {
            return rootValueDelegate.canRead(clazz, mediaType)
        }
        return delegate.canRead(clazz, mediaType)
    }

    override fun canWrite(clazz: Class<*>, mediaType: MediaType?): Boolean {
        if (clazz.hasJsonRootNameAnnotation) {
            return rootValueDelegate.canWrite(clazz, mediaType)
        }
        return delegate.canWrite(clazz, mediaType)
    }

    override fun getSupportedMediaTypes(): MutableList<MediaType> {
        return delegate.supportedMediaTypes
    }

    override fun write(t: Any, contentType: MediaType?, outputMessage: HttpOutputMessage) {
        if (t.javaClass.hasJsonRootNameAnnotation) {
            rootValueDelegate.write(t, contentType, outputMessage)
            return
        }
        delegate.write(t, contentType, outputMessage)
    }

    override fun read(clazz: Class<out Any>, inputMessage: HttpInputMessage): Any {
        if (clazz.hasJsonRootNameAnnotation) {
            return rootValueDelegate.read(clazz, inputMessage)
        }
        return delegate.read(clazz, inputMessage)
    }

    private val Class<out Any>.hasJsonRootNameAnnotation: Boolean
        get() = isAnnotationPresent(JsonRootName::class.java)
}