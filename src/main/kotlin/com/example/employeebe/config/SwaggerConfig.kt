package com.example.employeebe.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.paths.RelativePathProvider
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.*
import javax.servlet.ServletContext


@Configuration
@EnableSwagger2
class SwaggerConfig {
    
        @Value("\${swagger.relativePath}")
        var path:String?=null

        @Value("\${swagger.host}")
        var host:String?=null

        class MyretalivePath(servletContext: ServletContext, val path: String): RelativePathProvider(servletContext) {

            override fun applicationPath(): String {
                var path=""
                if(this.path!=null){
                    path= this.path!!
                }
                return path+super.applicationPath()
            }
        }
        @Autowired
        var servletContext: ServletContext?=null;


        @Bean
        fun api(): Docket {
            return Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(DEFAULT_API_INFO)
                    .produces(DEFAULT_PRODUCES_AND_CONSUMES).
                    host(host).
                    pathProvider(MyretalivePath(servletContext!!,this.path!!))
                    .consumes(DEFAULT_PRODUCES_AND_CONSUMES)

        }


        companion object {

            val DEFAULT_CONTACT = springfox.documentation.service.Contact(
                    "Synycs", "http://www.spring.io", "ramesh@clymbr.in")

            val DEFAULT_API_INFO = ApiInfo(
                    "Employee-BE", "RESTFULL API Description", "1.0",
                    "urn:tos", DEFAULT_CONTACT,
                    "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0")

            private val DEFAULT_PRODUCES_AND_CONSUMES = HashSet(Arrays.asList("application/json"))
        }
    }

