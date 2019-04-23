# grails-wildfly13-deployment-fix
grails 3.3.5 deployment in wildfly-13.0.0.Final, issue 404 - Not Found & context path fixing
### Step By Step This Application
  #### 01. Environment
    grails-3.3.5 framework lib, jboss wildfly-13.0.0.Final installed in pc.
  #### 02. create a grails application then edit [grails-wildfly13-deployment-fix/build.gradle] file's dependencies block like below
      dependencies {
		compile "org.springframework.boot:spring-boot-starter-logging"
		compile "org.springframework.boot:spring-boot-autoconfigure"
		compile "org.grails:grails-core"
		compile "org.springframework.boot:spring-boot-starter-actuator"
		//compile "org.springframework.boot:spring-boot-starter-tomcat"
		provided "org.springframework.boot:spring-boot-starter-tomcat"
		compile "org.grails:grails-web-boot"
		compile "org.grails:grails-logging"
		compile "org.grails:grails-plugin-rest"
		compile "org.grails:grails-plugin-databinding"
		compile "org.grails:grails-plugin-i18n"
		compile "org.grails:grails-plugin-services"
		compile "org.grails:grails-plugin-url-mappings"
		compile "org.grails:grails-plugin-interceptors"
		compile "org.grails.plugins:cache"
		compile "org.grails.plugins:async"
		compile "org.grails.plugins:scaffolding"
		compile "org.grails.plugins:events"
		compile "org.grails.plugins:hibernate5"
		compile "org.hibernate:hibernate-core:5.1.5.Final"
		compile "org.grails.plugins:gsp"
		console "org.grails:grails-console"
		profile "org.grails.profiles:web"
		runtime "org.glassfish.web:el-impl:2.1.2-b03"
		runtime "com.h2database:h2"
		runtime "org.apache.tomcat:tomcat-jdbc"
		runtime "com.bertramlabs.plugins:asset-pipeline-grails:2.14.8"
		testCompile "org.grails:grails-gorm-testing-support"
		testCompile "org.grails:grails-web-testing-support"
		testCompile "org.grails.plugins:geb:1.1.2"
		testRuntime "org.seleniumhq.selenium:selenium-chrome-driver:2.47.1"
		testRuntime "org.seleniumhq.selenium:selenium-htmlunit-driver:2.47.1"
		testRuntime "net.sourceforge.htmlunit:htmlunit:2.18"

		// have add below one lib for jboss xml api support
		runtime 'javax.xml.bind:jaxb-api:2.2.12'
    }
  just make org.springframework.boot:spring-boot-starter-tomcat, compile to provided
  and add runtime 'javax.xml.bind:jaxb-api:2.2.12' dependency.
  
  #### 03. Then edit [grails-wildfly13-deployment-fix/grails-app/conf/application.yml] this file. just add the below content
  	server:
		context-path: /grailsinwildfly
  grails 3^* application by default deployed in root so it's context path is just "/", but tomcat and jboss container looking for a context path when it dosen't found any context path then it shows 404 - Not Found. This works for tomcat fine but jboss need more config like below.
  #### 04. So go to [grails-wildfly13-deployment-fix/src/main/webapp]
  first create a folder **[/WEB-INF]** then within that folder create a file **[jboss-web.xml]** and in that file place below content
  
  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
<jboss-web version="10.0"
           xmlns="http://www.jboss.com/xml/ns/javaee"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://www.jboss.com/xml/ns/javaee http://www.jboss.org/j2ee/schema/jboss-web_10_0.xsd">
    <context-root>/grailsinwildfly</context-root>
</jboss-web>
```
  look at context-root which value should be same as context-path value.
  #### 05. Create another file in  [grails-wildfly13-deployment-fix/src/main/webapp/WEB-INF] named  **[jboss-deployment-structure.xml]** having content like below
  
  ```xml
  <?xml version='1.0' encoding='UTF-8'?>
<jboss-deployment-structure xmlns="urn:jboss:deployment-structure:1.1">
    <deployment>
        <exclusions>
            <module name="org.hibernate.validator"></module>
        </exclusions>
    </deployment>
</jboss-deployment-structure>
```
  jboss use it's own hibernate lib that may conflict with grails app hibernate lib that's why that should be exclude in deployment.
  #### 06. Now one more config by edit file [grails-wildfly13-deployment-fix/grails-app/init/grails/wildfly13/deployment/fix/Application.groovy] with content like below
  ```groovy
    package grails.wildfly13.deployment.fix
    
    import grails.boot.GrailsApp
    import grails.boot.config.GrailsAutoConfiguration
    import org.springframework.boot.autoconfigure.EnableAutoConfiguration
    import org.springframework.boot.autoconfigure.transaction.jta.*
    
    @EnableAutoConfiguration(exclude=[JtaAutoConfiguration])
    class Application extends GrailsAutoConfiguration {
        static void main(String[] args) {
            GrailsApp.run(Application, args)
        }
    }
  ```
  #### 07. Deployment grails-3.3.5 in JBoss WildFly-13.0.0.Final container
  go to the application root folder then apply command
  
    clean
    compile
    war prod
  
  within some times war file will be create in folder **[grails-wildfly13-deployment-fix/build/libs/grails-wildfly13-deployment-fix-0.1.war]**
  now copy the file **[grails-wildfly13-deployment-fix-0.1.war]** to other place and rename the file as **[grailsinwildfly.war]** mind it **[grailsinwildfly]** is our context path.
  #### 08. Start WildFly-13.0.0.Final server from terminal
     some@somepc:/opt/wildfly13/bin $ ./standalone.sh 
  and browse link http://127.0.0.1:9990/console/index.html
  then click on Deployment tab then click on (+) button browse the **[grails-wildfly13-deployment-fix-0.1.war]** file and follow the snap shoot below __
  
  upload done -> Next -> Finish
  
  ![Resources](https://github.com/javagrails/grails-wildfly13-deployment-fix/blob/master/mdres/gw-06.png)
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fjavagrails%2Fgrails-wildfly13-deployment-fix.svg?type=shield)](https://app.fossa.io/projects/git%2Bgithub.com%2Fjavagrails%2Fgrails-wildfly13-deployment-fix?ref=badge_shield)
  
  View Deployment
  
  ![Resources](https://github.com/javagrails/grails-wildfly13-deployment-fix/blob/master/mdres/gw-07.png)
  
  Click the blue link which is application context path
  
  ![Resources](https://github.com/javagrails/grails-wildfly13-deployment-fix/blob/master/mdres/gw-08.png)
  
  So deployment done and browse you app here http://127.0.0.1:8080/grailsinwildfly
  
  ![Resources](https://github.com/javagrails/grails-wildfly13-deployment-fix/blob/master/mdres/gw-09.png)
  
  
  you can also visit http://127.0.0.1:8080/grailsinwildfly/open/index
  
  # Thank you all
  

## License
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fjavagrails%2Fgrails-wildfly13-deployment-fix.svg?type=large)](https://app.fossa.io/projects/git%2Bgithub.com%2Fjavagrails%2Fgrails-wildfly13-deployment-fix?ref=badge_large)