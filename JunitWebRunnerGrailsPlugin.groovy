/* Copyright 2006-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
 import grails.util.BuildSettings

 /**
 *
 * @author <a href='mailto:limcheekin@vobject.com'>Lim Chee Kin</a>
 *
 * @since 0.1.0
 */
class JunitWebRunnerGrailsPlugin {
    // the plugin version
    def version = "0.1.0"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.2.2 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]
	def watchedResources = "file:./test/junit-web/**/*Tests.groovy"

    // TODO Fill in these fields
    def author = "Lim Chee Kin"
    def authorEmail = "limcheekin@vobject.com"
    def title = "Grails JUnit Web Runner Plugin"
    def description = '''\\
	Grails JUnit Web Runner Plugin is created based on Kotori Web JUnit Runner project
	(http://code.google.com/p/kotori/) which is a JUnit Runner GWT application that 
	helps to run in-container test cases on grails application. Test cases execution in
	Google App Engine(GAE) Development or Production Server is supported. 
	'''	

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/junit-web-runner"

    def doWithWebDescriptor = { webXml ->
		def servletElements = webXml.'servlet'
		def servletElement = servletElements[servletElements.size()-1]
		servletElement + {
			'servlet' {
				'servlet-name'("KtrWjrServiceServlet")
				'servlet-class'("bufferings.ktr.wjr.server.service.KtrWjrServiceServlet")
			}
		}
		
		def mappingElements = webXml.'servlet-mapping'
		def mappingElement = mappingElements[mappingElements.size()-1]
		mappingElement + {
			'servlet-mapping' {
				'servlet-name'("KtrWjrServiceServlet")
				'url-pattern'("/ktrwjr/ktrwjr/ktrwjr.s3gwt")
			}
		}		
    }

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
		
		// TODO: auto-compile of edited JUnit Web Test and reload Application Context. 
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }
}
