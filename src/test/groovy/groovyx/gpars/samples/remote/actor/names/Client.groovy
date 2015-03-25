// GPars - Groovy Parallel Systems
//
// Copyright © 2014  The original author or authors
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

package groovyx.gpars.samples.remote.actor.names

import groovyx.gpars.actor.remote.RemoteActors

import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

def locateService(RemoteActors context, String serviceUrl) {
    while (true) {
        try {
            return context.get(serviceUrl).get(20, TimeUnit.MILLISECONDS)
        } catch (TimeoutException e) {
            System.err << "."
        }
    }
}

println "Starting client"
def context = RemoteActors.create "client-1"

print "Locating services"
def encoder = locateService context, "cipher.service/caesar-encoder"
def decoder = locateService context, "cipher.service/caesar-decoder"
println " ok"

def msg = "Groovy GPars"
println "Encoding \"$msg\""
def encodedMsg = encoder.sendAndWait msg
println "Encoded \"$encodedMsg\""
def decodedMsg = decoder.sendAndWait encodedMsg
println "Decoded \"$decodedMsg\""
