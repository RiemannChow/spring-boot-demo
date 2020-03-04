
SpringBoot 集成常用功能
==== 

1、AOP面向切面编程
---

>aop
>>WebLogAspect.java

使用场景：权限、缓存、资源池、同步、事务、记录日志等。

使用方法：

[SpringBoot项目中集成AOP](https://blog.csdn.net/riemann_/article/details/100941237)

2、cros跨域
---

>common
>>cros
>>>GlobalCorsConfig.java

使用场景：通过添加HTTP Hearder部分字段请求与获取有权限访问的资源。浏览器会先发送options请求询问后端是否允许跨域，后端看是否配置了该域名，如果配置了          则可以访问服务器中的该资源；反之。

使用方法：

[SpringBoot项目中实现跨域的解决方案](https://blog.csdn.net/riemann_/article/details/100087296)

3、线程池配置
---

>config
>>ConcurrentThreadGlobalConfig.java

使用场景：异步任务处理，比如发送短信、保存日志、上传下载文件等。

使用方法：配置好线程池后，在你所需要做异步任务处理的方法上加 '@Async'

[Java并发编程中四种线程池](https://blog.csdn.net/riemann_/article/details/97617432)


4、swagger
---

>config、controller
>>SwaggerConfig.java、SwaggerController.java

使用场景：作为一个规范和完整的框架，可以用于生成、描述、调用和可视化 RESTful 风格的 Web 服务；支持在线接口测试。

使用方法：

[SpringBoot项目中集成Swagger2](https://blog.csdn.net/riemann_/article/details/103604132)

5、Quartz定时调度 
---

>config、controller
>>TaskConfig、EmailTask

使用场景：提醒和警告、监听事务、定时作业、发送邮件和消息等。

使用方法：

[SpringBoot项目中集成Quartz实现定时调度任务](https://blog.csdn.net/riemann_/article/details/93405380)

6、FreeMarker发送邮件
---

>controller
>>MailController

使用场景：发送邮件通知对方。

使用方法：

[SpringBoot项目中集成FreeMarker实现模板发送邮件](https://blog.csdn.net/riemann_/article/details/93380224)

7、Log Forging日志伪造
---

>util
>>CommonUtil.java

使用场景：防止黑客恶意日志伪造。

使用方法：

[SpringBoot项目中解决Fortify漏洞Log Forging日志伪造](https://blog.csdn.net/riemann_/article/details/93358316)

8、自定义异常处理机制
---

>exception
>>GlobalExceptionHandle.java

使用场景：自定义异常处理机制。

使用方法：

[SpringBoot的异常处理机制](https://blog.csdn.net/riemann_/article/details/91346014)

9、jasypt配置文件自定义加密机制
---

>config、util
>>EncryptablePropertyConfig.java、JasyptUtil.java

使用场景：我们平时的配置文件里有密码这样的敏感信息，很危险，所以我们需要考虑配置文件中的敏感信息加密。

使用方法：

[SpringBoot项目中基于jasypt实现mysql配置文件自定义密码加密](https://blog.csdn.net/riemann_/article/details/92245115)

10、自定义日志配置
---

>controller
>>LogbackController.java

使用场景：按照我们想要的方式去自定义配置日志；对分布式Kibana日志搜集系统很有用。

使用方法：

[SpringBoot项目中使用Logback实现多环境日志配置详解](https://blog.csdn.net/riemann_/article/details/100047010)

11、自定义Filter过滤器、Listener监听器、Interceptor拦截器和Servlet容器
---

>filter、listener、interceptor、servlet
>>MyFilter.java、MyListener.java、MyLoginInterceptor.java、MyServlet.java

使用场景：Web开发四大利器，不多说。

使用方法：

[SpringBoot项目中自定义Filter过滤器、Listener监听器、Interceptor拦截器和Servlet容器](https://blog.csdn.net/riemann_/article/details/100905825)

12、shiro权限管控
---

使用场景：使用JWT进行鉴权，完全实现无状态鉴权。

使用方法：

[SpringBoot项目中集成Shiro进行权限管控](https://blog.csdn.net/riemann_/article/details/101177236)

13、EasyExcel上传下载文件
---

>controller
>>EasyExcelController.java

使用场景：

Java解析、生成Excel比较有名的框架有Apache poi、jxl。但他们都存在一个严重的问题就是非常的耗内存，poi有一套SAX模式的API可以一定程度的解决一          些内存溢出的问题，但POI还是有一些缺陷，比如07版Excel解压缩以及解压后存储都是在内存中完成的，内存消耗依然很大。easyexcel重写了poi对07版            Excel的解析，能够原本一个3M的excel用POI sax依然需要100M左右内存降低到几M，并且再大的excel不会出现内存溢出，03版依赖POI的sax模式。在上层          做了模型转换的封装，让使用者更加简单方便。

使用方法：

[SpringBoot项目中集成EasyExcel实现Excel文件上传至MySQL](https://blog.csdn.net/riemann_/article/details/103639254)

14、JAXB实现XML与Java对象的互相转换
---

>controller、util
>>ObjectConvertXMLController.java、XMLUtil.java

使用场景：

(1)、在项目中，有时候会有很多的XML文件，但如果可以将这些文件通过对象的方式去操作，就会减少很多操作问题，而且更加符合程序员的编码方式，<br>
(2)、在项目中，有时候会遇到一个页面中存在很多的实体类中的数据，而且有时候有些数据不是必需的，就是说可以通过DTO来编写这些实体类，但有时候需要将            这些DTO进行预先存储，不是存储到数据库中，这样就有两种思路，可以存储在内存中，也可以存储在硬盘上，此时就可以通过将Java对象转换成XML文件存            储，或者变成String类型进行存储在内存中。<br>
(3)、一个页面中有很多个模块构成，但是这些模块都是属于一个整体的模块，当用户有操作其中几个模块的时候，但操作后的数据不是最终的数据，那这个时候首            先要保存当前页面中的数据（Done），然后到其他页面进行其他操作后再转到这个页面，那么之前那个页面中的数据应该还会存在，用户可以方便查看。但            是由于模块较多，如果之前就存到数据库中就会造成浪费，因为其不是最终的保存效果，而当用户想进行保存（Save），此时才进行将最终的数据保存到数            据库中。在这个过程中就会用到大量的临时数据，而解决这个问题很好的方法就是可以用XML保存页面中当前的数据。<br>

使用方法：

[SpringBoot项目中集成JAXB实现XML与Java对象的互相转换](https://blog.csdn.net/riemann_/article/details/100147789)



