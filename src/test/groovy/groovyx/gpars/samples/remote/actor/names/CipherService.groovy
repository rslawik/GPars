// GPars - Groovy Parallel Systems
//
// Copyright © 2015  The original author or authors
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

import groovyx.gpars.actor.Actors
import groovyx.gpars.actor.remote.RemoteActors

def HOST = InetAddress.localHost.hostAddress
def PORT = 22550

def context = RemoteActors.create "cipher.service"
context.startServer HOST, PORT

println "Starting Cipher Service(s)"
def SECRET_KEY = 5

def caesarCipherActor = Actors.reactor { msg ->
    msg.text.chars.collect { c ->
        int off = c.isUpperCase() ? 'A' : 'a'
        c.isLetter() ? (((c as int) - off + msg.key) % 26 + off) as char : c
    }.join()
}

def caesarCipherEncoderActor = Actors.reactor { msg ->
    println "Encoding \"$msg\""
    caesarCipherActor.sendAndWait(["key": SECRET_KEY, "text": msg])
}
context.publish caesarCipherEncoderActor, "caesar-encoder"
println "caesar-encoder started"

def caesarCipherDecoderActor = Actors.reactor { msg ->
    println "Decoding \"$msg\""
    caesarCipherActor.sendAndWait(["key": 26 - SECRET_KEY, "text": msg])
}
context.publish caesarCipherDecoderActor, "caesar-decoder"
println "caesar-decoder started"

[caesarCipherActor, caesarCipherEncoderActor, caesarCipherDecoderActor]*.join()
context.stopServer()
