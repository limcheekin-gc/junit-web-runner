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

/**
 * Gant script that create JUnit Web Test 
 *
 * @author <a href='mailto:limcheekin@vobject.com'>Lim Chee Kin</a>
 *
 * @since 0.1.0
 */

includeTargets << grailsScript("_GrailsInit")
includeTargets << grailsScript("_GrailsCreateArtifacts")

target ('default': "Creates a new JUnit web test.") {
	depends(checkVersion, parseArguments)

    promptForName(type: "JUnit web test")

    def name = argsMap["params"][0]
    createJUnitWebTest(name: name, suffix: "")
}                            

createJUnitWebTest = { Map args = [:] ->
    def superClass = args["superClass"] ?: "GroovyTestCase"
	createArtifact(name: args["name"], suffix: "${args['suffix']}Tests", type: "Tests", path: "test/junit-web", superClass: superClass)
}
