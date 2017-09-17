package com.lijiye.demo

import io.vertx.core.DeploymentOptions
import io.vertx.core.Vertx

class Worker: io.vertx.core.AbstractVerticle {
    constructor(){
        vertx = Vertx.vertx()
    }

    override fun start() {
        println("[Worker] Starting in ${java.lang.Thread.currentThread().name}")

        vertx.eventBus().consumer<String>("sample.data", { message ->
            println("[Worker] Consuming data in ${java.lang.Thread.currentThread().name}")
            var body = message.body()
            message.reply(body.toUpperCase())
        })
    }
}

class Main: io.vertx.core.AbstractVerticle {
    constructor() {
        vertx = Vertx.vertx()
    }

    override fun start() {
        println("[Main] Running in ${java.lang.Thread.currentThread().name}")
        var option = DeploymentOptions()
        option.isWorker = true
        vertx.deployVerticle("com.lijiye.demo.Worker", option)
        vertx.setPeriodic(1000, {handler ->
            vertx.eventBus().send<Any>("sample.data", "Hello, world!", { r ->
                println("[Main] Receiving reply ' ${r.result().body()}' in ${java.lang.Thread.currentThread().name}")
            })
        })

    }
}

fun main(args: Array<String>) {
    var main = Main()
    main.start()
}