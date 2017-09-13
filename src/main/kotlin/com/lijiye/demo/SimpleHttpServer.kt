package com.lijiye.demo

import io.vertx.core.Vertx

fun main(args: Array<String>) {
    Vertx.vertx().createHttpServer()
            .requestHandler({ req -> req.response().end("Hello, world") }).listen(8080)
}