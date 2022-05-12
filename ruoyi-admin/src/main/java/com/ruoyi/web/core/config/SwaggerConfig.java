package com.ruoyi.web.core.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.ruoyi.common.config.RuoYiConfig;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger2的接口配置
 *
 * @author ruoyi
 */
@Configuration
public class SwaggerConfig
{
    /** 系统基础配置 */
    @Autowired
    private RuoYiConfig ruoyiConfig;

    /** 是否开启swagger */
    @Value("${swagger.enabled}")
    private boolean enabled;

    /** 设置请求的统一前缀 */
    @Value("${swagger.pathMapping}")
    private String pathMapping;

    /**
     * 创建API
     */
    @Bean
    public Docket createToolRestApi()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("工具模块")
                // 是否启用Swagger
                .enable(enabled)
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(toolApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ruoyi.web.controller.tool"))
                .paths(PathSelectors.any())
                .build()
                // 设置哪些接口暴露给Swagger展示

                // 扫描所有有注解的api，用这种方式更灵活
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 扫描指定包中的swagger注解

                // 扫描所有 .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build()
                /* 设置安全模式，swagger可以设置访问token */
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                .pathMapping(pathMapping);
    }
    /**
     * 创建API
     */
    @Bean
    public Docket createAppRestApi()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("业务模块")
                // 是否启用Swagger
                .enable(enabled)
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）

                .apiInfo(appApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ruoyi.web.controller.app"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                .pathMapping(pathMapping);
    }
    /**
     * 创建API
     */
    @Bean
    public Docket createSysRestApi()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("系统模块")
                // 是否启用Swagger
                .enable(enabled)
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）

                .apiInfo(sysApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ruoyi.web.controller.system"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                .pathMapping(pathMapping);
    }
    /**
     * 创建API
     */
    @Bean
    public Docket createMonitorRestApi()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("服务器模块")
                // 是否启用Swagger
                .enable(enabled)
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）

                .apiInfo(monitorApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ruoyi.web.controller.monitor"))
                .paths(PathSelectors.any())
                .build()

                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                .pathMapping(pathMapping);
    }
    /**
     * 创建API
     */
    @Bean
    public Docket createCommonRestApi()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("通用模块")
                // 是否启用Swagger
                .enable(enabled)
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）

                .apiInfo(commonApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ruoyi.web.controller.common"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                .pathMapping(pathMapping);
    }
    /**
     * 创建API
     */
    @Bean
    public Docket createJobRestApi()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("定时任务模块")
                // 是否启用Swagger
                .enable(enabled)
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）

                .apiInfo(jobApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ruoyi.quartz.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                .pathMapping(pathMapping);
    }

    /**
     * 安全模式，这里指定token通过Authorization头请求头传递
     */
    private List<SecurityScheme> securitySchemes()
    {
        List<SecurityScheme> apiKeyList = new ArrayList<SecurityScheme>();
        apiKeyList.add(new ApiKey("Authorization", "Authorization", In.HEADER.toValue()));
        return apiKeyList;
    }

    /**
     * 安全上下文
     */
    private List<SecurityContext> securityContexts()
    {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .operationSelector(o -> o.requestMappingPattern().matches("/.*"))
                        .build());
        return securityContexts;
    }

    /**
     * 默认的安全上引用
     */
    private List<SecurityReference> defaultAuth()
    {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }

    /**
     * 添加摘要信息
     */
    private ApiInfo sysApiInfo()
    {
        // 用ApiInfoBuilder进行定制
        return new ApiInfoBuilder()
                // 设置标题
                .title("标题：若依管理系统_system")
                // 描述
                .description("描述：system模块")
                // 作者信息
                .contact(new Contact(ruoyiConfig.getName(), null, null))
                // 版本
                .version("版本号:" + ruoyiConfig.getVersion())
                .build();
    }
    /**
     * 添加摘要信息
     */
    private ApiInfo toolApiInfo()
    {
        // 用ApiInfoBuilder进行定制
        return new ApiInfoBuilder()
                // 设置标题
                .title("标题：若依管理系统_tool")
                // 描述
                .description("描述：工具模块")
                // 作者信息
                .contact(new Contact(ruoyiConfig.getName(), null, null))
                // 版本
                .version("版本号:" + ruoyiConfig.getVersion())
                .build();
    }
    /**
     * 添加摘要信息
     */
    private ApiInfo appApiInfo()
    {
        // 用ApiInfoBuilder进行定制
        return new ApiInfoBuilder()
                // 设置标题
                .title("标题：若依管理系统_app")
                // 描述
                .description("描述：业务模块")
                // 作者信息
                .contact(new Contact(ruoyiConfig.getName(), null, null))
                // 版本
                .version("版本号:" + ruoyiConfig.getVersion())
                .build();
    }
    /**
     * 添加摘要信息
     */
    private ApiInfo commonApiInfo()
    {
        // 用ApiInfoBuilder进行定制
        return new ApiInfoBuilder()
                // 设置标题
                .title("标题：若依管理系统_common")
                // 描述
                .description("描述：通用模块")
                // 作者信息
                .contact(new Contact(ruoyiConfig.getName(), null, null))
                // 版本
                .version("版本号:" + ruoyiConfig.getVersion())
                .build();
    }
    /**
     * 添加摘要信息
     */
    private ApiInfo monitorApiInfo()
    {
        // 用ApiInfoBuilder进行定制
        return new ApiInfoBuilder()
                // 设置标题
                .title("标题：若依管理系统_monitor")
                // 描述
                .description("描述：服务器模块")
                // 作者信息
                .contact(new Contact(ruoyiConfig.getName(), null, null))
                // 版本
                .version("版本号:" + ruoyiConfig.getVersion())
                .build();
    }
    /**
     * 添加摘要信息
     */
    private ApiInfo jobApiInfo()
    {
        // 用ApiInfoBuilder进行定制
        return new ApiInfoBuilder()
                // 设置标题
                .title("标题：若依管理系统_job")
                // 描述
                .description("描述：调度任务")
                // 作者信息
                .contact(new Contact(ruoyiConfig.getName(), null, null))
                // 版本
                .version("版本号:" + ruoyiConfig.getVersion())
                .build();
    }
}
