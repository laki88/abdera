/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  The ASF licenses this file to You
* under the Apache License, Version 2.0 (the "License"); you may not
* use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.  For additional information regarding
* copyright in this work, please see the NOTICE file in the top level
* directory of this distribution.
*/
package org.apache.abdera.examples.ext;

import org.apache.abdera.Abdera;
import org.apache.abdera.model.Entry;

/**
 * The JSONWriter can be used to serialize an Abdera entry into a JSON structure
 */
public class Json {

  public static void main(String... args) throws Exception {
    
    Abdera abdera = new Abdera();
    Entry entry = abdera.newEntry();
    entry.newId();
    entry.setTitle("test");
    entry.setContentAsHtml("<b>foo</b>");
    entry.addAuthor("James");
    entry.addCategory("term");
    entry.writeTo("json", System.out);
    
    /**
     * Produces: 
     * 
     * {
     * "id":"urn:uuid:97893C35372BE77BD51200273434152",
     *  "title":"test",
     *  "content":{
     *   "attributes":{"type":"html"},
     *   "children":[{
     *     "name":"b",
     *     "attributes":{},
     *     "children":["foo"]}]},
     *  "authors":[{"name":"James"}],
     *  "categories":[{"term":"term"}]
     * }
     */
  }
  
}