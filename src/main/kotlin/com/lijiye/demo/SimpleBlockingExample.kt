package com.lijiye.demo

import io.vertx.core.Vertx
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    var vertx = Vertx.vertx()
    vertx.createHttpServer().requestHandler({ req ->
        vertx.executeBlocking<String>({ future ->
            TimeUnit.SECONDS.sleep(5)
            future.complete("Hello, world!")
        }, { result ->
            req.response().end(result.result())

        })
    }).listen(8080)
}