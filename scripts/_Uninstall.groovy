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
 * This script is executed by Grails when the plugin is uninstalled from project.
 *
 * @author <a href='mailto:limcheekin@vobject.com'>Lim Chee Kin</a>
 *
 * @since 0.1.0
 */
Ant.delete(dir:"${basedir}/web-app/ktrwjr")
Ant.delete(file:"${basedir}/lib/gwt-servlet.jar")
Ant.delete(file:"${basedir}/lib/junit-3.8.2.jar")
Ant.delete(file:"${basedir}/lib/ktr-wjr.jar")
 