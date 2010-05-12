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

import org.codehaus.groovy.control.CompilationUnit
import org.codehaus.groovy.grails.plugins.PluginInfo
import grails.util.GrailsNameUtils
import grails.util.BuildSettings

/**
 * Gant script that compiles JUnit Web Tests 
 *
 * @author <a href='mailto:limcheekin@vobject.com'>Lim Chee Kin</a>
 *
 * @since 0.1.0
 */
includeTargets << grailsScript("_GrailsInit")
includeTargets << grailsScript("_GrailsEvents")
includeTargets << grailsScript("_GrailsArgParsing")

ant.taskdef (name: 'groovyc', classname : 'org.codehaus.groovy.grails.compiler.GrailsCompiler')
ant.path(id: "grails.compile.classpath", compileClasspath)

compilerPaths = { String classpathId ->
	def excludedPaths = ["views", "i18n", "conf"] // conf gets special handling

	for(dir in new File("${basedir}/grails-app").listFiles()) {
        if(!excludedPaths.contains(dir.name) && dir.isDirectory())
            src(path:"${dir}")
    }
    // Handle conf/ separately to exclude subdirs/package misunderstandings
    src(path: "${basedir}/grails-app/conf")
    
	if (new File("${basedir}/grails-app/conf/spring").exists()) {
        src(path: "${basedir}/grails-app/conf/spring")     // This stops resources.groovy becoming "spring.resources"
    }

    if (new File("${grailsSettings.sourceDir}/groovy").exists()) {
        src(path:"${grailsSettings.sourceDir}/groovy")
    }
    if (new File("${grailsSettings.sourceDir}/java").exists()) {    
        src(path:"${grailsSettings.sourceDir}/java")
    }
	
    if (new File("${basedir}/test/junit-web").exists()) {
        src(path:"${basedir}/test/junit-web")
    }
    
    javac(classpathref:classpathId, encoding:"UTF-8", debug:"yes")
}


target('default': "Implementation of compilation of junit web test phase") {
    depends(compilePlugins)
	
    def classesDirPath = grailsSettings.classesDir.path
    ant.mkdir(dir:classesDirPath)

    profile("Compiling sources to location [$classesDirPath]") {
        try {
            String classpathId = "grails.compile.classpath"
            ant.groovyc(destdir:classesDirPath,
	                    classpathref:classpathId,
	                    encoding:"UTF-8",
	                    verbose: grailsSettings.verboseCompile,
                        listfiles: grailsSettings.verboseCompile,
	                    compilerPaths.curry(classpathId))
        }
        catch(Exception e) {
            event("StatusFinal", ["Compilation error: ${e.message}"])
            exit(1)
        }
        classLoader.addURL(grailsSettings.classesDir.toURI().toURL())

    }
}

target(setCompilerSettings: "Updates the compile build settings based on args") {
    depends(parseArguments)
    if (argsMap.containsKey('verboseCompile')) {
        grailsSettings.verboseCompile = argsMap.verboseCompile as boolean
    }
}

target(compilePlugins: "Compiles source files of all referenced plugins.") {
    depends(setCompilerSettings, resolveDependencies)

    def classesDirPath = grailsSettings.classesDir.path
    ant.mkdir(dir:classesDirPath)

    profile("Compiling sources to location [$classesDirPath]") {
        // First compile the plugins so that we can exclude any
        // classes that might conflict with the project's.
        def classpathId = "grails.compile.classpath"
        def pluginResources = pluginSettings.pluginSourceFiles
        def excludedPaths = ["views", "i18n"] // conf gets special handling
        pluginResources = pluginResources.findAll {
            !excludedPaths.contains(it.file.name) && it.file.isDirectory()
        }

        if (pluginResources) {
            // Only perform the compilation if there are some plugins
            // installed or otherwise referenced.
            ant.groovyc(destdir:classesDirPath,
                    classpathref:classpathId,
                    encoding:"UTF-8",
                    verbose: grailsSettings.verboseCompile,
                    listfiles: grailsSettings.verboseCompile) {
                for(dir in pluginResources.file) {
                    src(path:"${dir}")
                }
                exclude(name: "**/BootStrap.groovy")
                exclude(name: "**/BuildConfig.groovy")
                exclude(name: "**/Config.groovy")
                exclude(name: "**/*DataSource.groovy")
                exclude(name: "**/UrlMappings.groovy")
                exclude(name: "**/resources.groovy")
                javac(classpathref:classpathId, encoding:"UTF-8", debug:"yes")
            }
        }
    }
}

/**
 * Compiles a given plugin descriptor file - *GrailsPlugin.groovy.
 */
compilePluginDescriptor = { File descriptor ->
    def className = descriptor.name - '.groovy'
    def classFile = new File(grailsSettings.classesDir, "${className}.class")

    if (descriptor.lastModified() > classFile.lastModified()) {
        ant.echo(message: "Compiling plugin descriptor...")
        compConfig.setTargetDirectory(classesDir)
        def unit = new CompilationUnit(compConfig, null, new GroovyClassLoader(classLoader))
        unit.addSource(descriptor)
        unit.compile()
    }
}

