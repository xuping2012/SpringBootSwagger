# SpringBootSwagger

![Java Version](https://img.shields.io/badge/java-8+-blue)
![Spring Boot](https://img.shields.io/badge/spring%20boot-2.x-green)
![License](https://img.shields.io/badge/license-MIT-green)

## 项目简介

`SpringBootSwagger` 是一个基于 Spring Boot 的 Mock Server 项目，集成 Swagger UI 接口文档，用于后端接口测试和前端联调。

## 核心功能

- 🚀 **Mock Server**: 提供模拟接口服务
- 📄 **Swagger UI**: 自动生成接口文档
- 🧪 **接口测试**: 支持接口测试依赖
- 🔧 **配置灵活**: 支持多种配置方式

## 技术栈

- **Java**: 8+
- **Spring Boot**: 2.x
- **Swagger UI**: API 文档
- **Maven**: 依赖管理

## 快速开始

```bash
# 编译项目
mvn clean compile

# 运行项目
mvn spring-boot:run
```

## 项目结构

```
SpringBootSwagger/
├── src/main/java/
│   └── com/example/app/
│       ├── controller/     # REST 控制器
│       ├── service/        # 业务服务
│       ├── model/          # 数据模型
│       └── Application.java # 启动类
├── src/main/resources/
│   ├── application.yml     # 应用配置
│   └── static/             # 静态资源
├── pom.xml                 # Maven 配置
└── README.md
```

## 访问地址

- **应用首页**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html

## 使用场景

1. **前端联调**: 提供 Mock 数据进行前端开发
2. **接口测试**: 验证接口设计和数据格式
3. **文档生成**: 自动生成 API 文档

#### eclipse创建SpringBoot项目
- 没有找到支持快速创建SpringBoot项目的STS插件
- 使用了lombok插件，免去字段的setter和getter方法
- 使用了generate setter插件，创建参数对象，支持alt+enter选择批量生成类的所有setter方法
- 使用比较熟悉的testng框架做单元测试
- 添加了swagger依赖，支持UI接口文档
- - 配置swaggerconfig类

#### 如何配置swagger接口文档
- application.yml配置是否开启swagger开关
- yml配置文件格式，：值后面必须空格，否则不生效
- 在启动application项目时加上@EnableSwagger2注解
- - 在swaggerconfig配置了controller类前有@Api注解的类，在每个接口有@ApiOperation注解的方法

#### springboot快速写生成mock
- 主要用于解决接口依赖，快速进行接口测试
- 也可以使用json对象的字符串形式，再以json对象返回
- - 如果在Result类中有Map集合可以使用Map对象返回
- pom文件写入的依赖，需要停止application重新在启用


#### HTTP请求方法介绍
- @PostMapping post请求
- - 有参数，可以在方法里使用：method(@RequestBody paramType  param):
- - post请求可以看做是map集合，可以使用getparam获取请求的参数：param.getParam()
- - 也可以@RequestParam获取请求的非json的数据
- @GetMapping(value="/api/{id}") get请求
- - @PathVariable注解可以直接获取接口地址后面拼接的变量:method(@PathVariable("id") int id)
- - @RequestParam注解是自定义组装拼接的参数
- @RequestMethod 如果不指定请求方法:method = RequestMethod.GET，它会支持所有http请求的方法
- - 如果有参数，可以在方法里(@RequestParam(""))

#### @DataProvider注解
- 在接口测试框架中，通过注解，从csv、excel、mysql读取数据驱动接口测试


#### testReporterListener监听器
- MyExtentTestNgListener，类似allure-html测试报告样式，继承testng-报告插件，重写
- TestNgReporterListener，同类，继承IReport，是testng的报告，重写
- pom添加reportng依赖，覆盖原来的testng的报告

#### 接口自动化测试及Mock平台

## 许可证

MIT License
