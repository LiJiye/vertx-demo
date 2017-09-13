package com.lijiye.demo

import io.vertx.core.Vertx

fun main(args: Array<String>) {
    var eventBus = Vertx.vertx().eventBus()
    eventBus.consumer<Any>("address", { message ->
        println("0-Received message: ${message.body()}")
    })

    eventBus.consumer<Any>("address", { message ->
        println("1-Received message: ${message.body()}")
    })


    println("Receiver ready!")
    var i = 0
    Vertx.vertx().setPeriodic(1000, { v ->
        eventBus.publish("address", i)
        i++
    })
}