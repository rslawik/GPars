// GPars - Groovy Parallel Systems
//
// Copyright © 2008-10  The original author or authors
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package groovyx.gpars.memoize

/**
 * @author Vaclav Pech
 * Date: Jun 22, 2010
 */
public class NullValueTest extends GroovyTestCase {
    public void testEquals() throws Exception {
        assert new NullValue() == new NullValue()
    }

    public void testHashCode() throws Exception {
        assert new NullValue().hashCode() == new NullValue().hashCode()
    }
}
