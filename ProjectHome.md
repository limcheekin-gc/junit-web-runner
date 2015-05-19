# Overview #
Grails JUnit Web Runner Plugin is created based on Kotori Web JUnit Runner project (http://code.google.com/p/ktrwjr/) which is a JUnit Runner GWT application that helps to run in-container test cases in Google App Engine(GAE) Development or Production Server.

**Note :** The project owner no longer support this project since 30 Dec 2010, if you would like to take over, please contact the project owner.

# Installation #
As the plugin depends on app-engine plugin, so install app-engine plugin first:
```
grails install-plugin app-engine
```

Then, install the plugin:
```
grails install-plugin junit-web-runner
```

# Create JUnit Test #
Create JUnit Web Test:
```
grails create-junit-web-test my.package.ClassName
```

For example:
```
grails create-junit-web-test com.vobject.appengine.User
```

com.vobject.appengine.UserTests extends from GroovyTestCase will be created in test/junit-web directory of your grails project. The plugin supports test cases extends from GroovyTestCase only.

# Compile JUnit Web Tests #
Compile JUnit tests for new or updated test cases before run-app:
```
grails compile-junit-web-tests
```

# Execute JUnit Web Tests #
JUnit Web Tests can be executed by access to the URL: http://localhost:8080/ktrwjr/

# Use this plugin with GORM-JPA plugin #
If your application has [gorm-jpa](http://www.grails.org/plugin/gorm-jpa) plugin installed and you would like to write JUnit Web Test that perform testing for the JPA domain classes, you need to add the configuration below to src/templates/war/web.xml:
```
<filter>
    <filter-name>openEntityManagerInViewFilter</filter-name>
    <filter-class>
        org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter
    </filter-class>
</filter>

<filter-mapping>
    <filter-name>openEntityManagerInViewFilter</filter-name>
    <url-pattern>/ktrwjr/ktrwjr/ktrwjr.s3gwt</url-pattern>
</filter-mapping>
```
If the src/templates/war/web.xml is not found, you need to run command below:
```
grails install-templates
```

# Version History #
  * **14-May-2010 0.2.0** - Upgrade to ktrwjr-1.0.0 and junit-4.8.1. Dependency to app-engine plugin 0.8.8 included.
  * **12-May-2010 0.1.0** - First release with ktrwjr-0.1.2-alpha and junit-3.8.2.

# To do #
  * Auto-compile for new created and updated test cases in test/junit-web directory and auto-reload application context while grails application is running.