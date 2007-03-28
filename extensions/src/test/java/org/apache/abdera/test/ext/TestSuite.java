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
package org.apache.abdera.test.ext;

import org.apache.abdera.test.ext.media.MediaTest;
import org.apache.abdera.test.ext.opensearch.*;
import org.apache.abdera.test.ext.thread.ThreadTest;
import org.apache.abdera.test.ext.bidi.BidiTest;
import org.apache.abdera.test.ext.features.FeatureTest;
import org.apache.abdera.test.ext.history.FeedPagingTest;
import org.apache.abdera.test.license.LicenseTest;

public class TestSuite extends junit.framework.TestSuite {
  public static void main(String[] args)
  {
    junit.textui.TestRunner.run(new TestSuite());
  }

  public TestSuite()
  {
    addTestSuite(OpenSearchTest.class);
    addTestSuite(FeedPagingTest.class);
    addTestSuite(ThreadTest.class);
    addTestSuite(MediaTest.class);
    addTestSuite(BidiTest.class);
    addTestSuite(FeatureTest.class);
    addTestSuite(LicenseTest.class);
  }
}
