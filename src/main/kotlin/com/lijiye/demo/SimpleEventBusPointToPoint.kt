package com.lijiye.demo

import io.vertx.core.Vertx

fun main(args: Array<String>) {
    var eventBus = Vertx.vertx().eventBus()
    eventBus.consumer<Any>("address", { message ->

        println("Received message: ${message.body()}")
        // Now send back reply
        message.reply("pong!")
    })

    println("Receiver ready!")
    Vertx.vertx().setPeriodic(1000, { v ->

        eventBus.send<Any>("address", "ping!", { reply ->
            if (reply.succeeded()) {
                println("Received reply ${reply.result().body()}")
            } else {
                println("No reply")
            }
        })

    })
}