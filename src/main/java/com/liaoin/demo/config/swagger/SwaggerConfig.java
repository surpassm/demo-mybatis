package com.liaoin.demo.config.swagger;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author mc
 * Create date 2020/1/3 11:40
 * Version 1.0
 * Description
 */
@EnableSwagger2
@Configuration
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerConfig implements BeanFactoryAware {
    private BeanFactory beanFactory;

    @Resource
    private SwaggerProperties swaggerProperties;


    @Bean
    public List<Docket> createRestApi(SwaggerProperties swaggerProperties) {
        ConfigurableBeanFactory configurableBeanFactory = (ConfigurableBeanFactory) beanFactory;
        List<Docket> docketList = new LinkedList<>();

        Map<String, SwaggerProperties.DocketInfo> docketInfos = swaggerProperties.getDocket();
        for (String groupName : docketInfos.keySet()) {
            SwaggerProperties.DocketInfo docketInfo = docketInfos.get(groupName);

            ApiInfo apiInfo = new ApiInfoBuilder().title(docketInfo.getTitle()).description(docketInfo.getDescription())
                    .version("1.0")
                    .build();
            Docket docket = new Docket(DocumentationType.SWAGGER_12).enable(swaggerProperties.getEnabled()).
                    useDefaultResponseMessages(false)
                    .select()
                    .apis(RequestHandlerSelectors.basePackage(docketInfo.getBasePackage()))
                    .paths(PathSelectors.regex(swaggerProperties.getAuthorization().getAuthRegex()))
                    .build()
                    .securitySchemes(securitySchemes())
                    .securityContexts(securityContexts())
                    .apiInfo(apiInfo)
                    .groupName(groupName);
            configurableBeanFactory.registerSingleton(groupName, docket);
            docketList.add(docket)
            ;
        }
        return docketList;
    }

    private List<ApiKey> securitySchemes() {
        return new ArrayList(
                Collections.singleton(new ApiKey(swaggerProperties.getAuthorization().getName(), swaggerProperties.getAuthorization().getKeyName(), "header")));
    }

    private List<SecurityContext> securityContexts() {
        return new ArrayList(
                Collections.singleton(SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex(swaggerProperties.getAuthorization().getAuthRegex()))
                        .build())
        );
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return new ArrayList(
                Collections.singleton(new SecurityReference(swaggerProperties.getAuthorization().getKeyName(), authorizationScopes)));
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
