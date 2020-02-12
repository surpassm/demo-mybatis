package com.liaoin.demo.config.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author mc
 * Create date 2019/9/6 16:34
 * Version 1.0
 * Description
 */
@Slf4j
@Configuration
public class ApplicationRunnerConfig implements ApplicationRunner {
	@Resource
	private ServiceModelToSwagger2Mapper mapper;
	@Resource
	private DocumentationCache documentationCache;
	@Resource
	private OperationsMapper operationsMapper;
//	@Resource
//	private SwaggerProperties swaggerProperties;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		//后台所有接口更新
//		resourcesUpdate();
	}


//	private void resourcesUpdate(){
//		Map<String, SwaggerProperties.DocketInfo> docket = swaggerProperties.getDocket();
//		if (docket != null && docket.size() > 0) {
//			Iterator<Map.Entry<String, SwaggerProperties.DocketInfo>> iterator = docket.entrySet().iterator();
//			while (iterator.hasNext()){
//				Map.Entry<String, SwaggerProperties.DocketInfo> next = iterator.next();
//				String group = next.getKey();
//				Documentation documentation = documentationCache.documentationByGroup(group);
//				if (documentation != null) {
//					Swagger swagger = mapper.mapDocumentation(documentation);
//					Map<String, Path> paths = swagger.getPaths();
//					paths.forEach((key, value) -> {
//						// 链接
//						String url = key + "**";
//						// 排除不验证的url
//							// 名称
//							if (value.getPost() != null) {
//								String name = value.getPost().getSummary();
//								// 描述
//								String description = value.getPost().getTags().get(0);
//								// 权限
//								Operations build = Operations.builder().name(description).build();
//								Operations operations = operationsMapper.selectOne(build);
//								menuInsertAndUpdata(url, name, description, operations);
//							}
//							if (value.getGet() != null) {
//								String name = value.getGet().getSummary();
//								// 描述
//								String description = value.getGet().getTags().get(0);
//								// 权限
//								Operations build = Operations.builder().name(description).build();
//								Operations operations = operationsMapper.selectOne(build);
//								menuInsertAndUpdata(url, name, description, operations);
//							}
//					});
//				}
//			}
//		}
//	}
	private void menuInsertAndUpdata(String url, String name, String description, Operations operations) {
		if (operations == null){
			//新增父级
			Operations parentOperations = Operations.builder()
					.name(description)
					.type(1)
					.build();
			operationsMapper.insert(parentOperations);
			//在添加当前url为子级
			Operations operationBuild = Operations.builder()
					.apiUrl(url)
					.parentId(parentOperations.getId())
					.describes(name)
					.type(1)
					.createTime(LocalDateTime.now())
					.build();
			operationsMapper.insert(operationBuild);
		}else {
			Operations build = Operations.builder().apiUrl(url).build();
			int selectCount = operationsMapper.selectCount(build);
			if (selectCount == 0){
				//去除重复数据
				Operations operationBuild = Operations.builder()
						.apiUrl(url)
						.type(1)
						.parentId(operations.getId())
						.describes(name)
						.createTime(LocalDateTime.now())
						.build();
				operationsMapper.insert(operationBuild);
			}
		}
	}
}
