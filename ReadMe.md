#### eclipse创建SpringBoot项目
- 没有找到支持快速创建SpringBoot项目的STS插件
- 使用了lombok插件，免去字段的setter和getter方法
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
