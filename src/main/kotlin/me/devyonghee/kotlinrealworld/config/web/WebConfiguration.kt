package me.devyonghee.kotlinrealworld.config.web

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfiguration(
    private val objectMapper: ObjectMapper,
    private val builder: Jackson2ObjectMapperBuilder
) : WebMvcConfigurer {


    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        converters.add(
            0,
            DynamicRootValueJsonMessageConverter(
                builder.createXmlMapper(false).build<ObjectMapper>()
                    .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                    .configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false),
                objectMapper
            )
        )
    }
}