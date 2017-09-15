package com.lijiye.demo

import io.vertx.core.Vertx

fun main(args: Array<String>) {
    var vertx = Vertx.vertx()
    vertx.createNetServer().connectHandler({ sock ->

        // Create a pump
        sock.handler({buffer ->
            println("Server: ${buffer.toString("UTF-8")}")
        })
        sock.write("Receive")

    }).listen(1234)

    println("Echo server is now listening")

    vertx.createNetClient().connect(1234, "localhost", { res ->

        if (res.succeeded()) {
            var socket = res.result()
            socket.handler({ buffer ->
                println("Client: ${buffer.toString("UTF-8")}")
            })

            // Now send some data
            for (i in 0 until 10) {
                var str = "hello $i\n"
                socket.write(str)

            }

        } else {
            println("Failed to connect ${res.cause()}")
        }
    })
}