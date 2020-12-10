package com.liaoin.demo.config.web;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.ysytech.imc.config.security.TokenInterceptor;
import com.ysytech.imc.config.security.TokenMethodArgumentResolver;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author mc
 * Create date 2019/3/4 15:53
 * Version 1.0
 * Description
 */

@Configuration
public class WebAppConfigurer extends WebMvcConfigurationSupport {
    @Resource
    private TokenMethodArgumentResolver tokenMethodArgumentResolver;
    @Resource
	private TokenInterceptor tokenInterceptor;
	@Resource
	private List<String> noVerify;



    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(tokenMethodArgumentResolver);
    }

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration interceptorRegistration = registry.addInterceptor(tokenInterceptor).addPathPatterns("/**");
		interceptorRegistration.excludePathPatterns(noVerify);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry){
		try {
			String path  = new File("").getCanonicalPath();
			registry.addResourceHandler("/upload/**").addResourceLocations("file:///"+path+"/upload/");
			registry.addResourceHandler("/static/**").addResourceLocations("file:///"+path+"/static/");
		}catch (Exception e){
		}
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "OPTIONS", "PUT")
				.allowedHeaders("Authorization", "Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method","Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
				.allowCredentials(false).maxAge(36000);
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		/**
		 * 替换默认的MappingJackson2HttpMessageConverter，过滤(json请求参数)xss
		 */
		ListIterator<HttpMessageConverter<?>> listIterator = converters.listIterator();
		while(listIterator.hasNext()) {
			HttpMessageConverter<?> next = listIterator.next();
			if(next instanceof MappingJackson2HttpMessageConverter) {
				listIterator.remove();
				break;
			}
		}
		converters.add(getMappingJackson2HttpMessageConverter());
	}

	public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
		// 创建自定义ObjectMapper
		SimpleModule module = new SimpleModule();
		module.addDeserializer(String.class, new JsonHtmlXssDeserializer(String.class));
		ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().applicationContext(this.getApplicationContext()).build();
		objectMapper.registerModule(module);
		// 创建自定义消息转换器
		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
		//设置中文编码格式
		List<MediaType> list = new ArrayList<>();
		list.add(MediaType.APPLICATION_JSON_UTF8);
		mappingJackson2HttpMessageConverter.setSupportedMediaTypes(list);
		return mappingJackson2HttpMessageConverter;
	}

}

/**
 * 对入参的json进行转义
 */
class JsonHtmlXssDeserializer extends JsonDeserializer<String> {

	public JsonHtmlXssDeserializer(Class<String> string) {
		super();
	}

	@Override
	public Class<String> handledType() {
		return String.class;
	}

	@Override
	public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		String value = jsonParser.getValueAsString();
		if (value != null) {
			return StringEscapeUtils.escapeHtml4(value.toString());
		}
		return value;
	}
}
