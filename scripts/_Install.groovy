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
 * This script is executed by Grails after plugin was installed to project.
 * This script is a Gant script so you can use all special variables provided
 * by Gant (such as 'baseDir' which points on project base dir). You can
 * use 'ant' to access a global instance of AntBuilder
 *
 * @author <a href='mailto:limcheekin@vobject.com'>Lim Chee Kin</a>
 *
 * @since 0.1.0
 */
Ant.copy(todir:"${basedir}/lib") {
     fileset(dir: "${pluginBasedir}/src/lib") 
}
Ant.copy(todir:"${basedir}/web-app/ktrwjr") {
     fileset(dir: "${pluginBasedir}/src/ktrwjr") 
}