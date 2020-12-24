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