package ru.otus.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import ru.otus.grpc.service.EnumeratorService;

import java.io.IOException;

public class GRPCServer {

    public static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder
                .forPort(SERVER_PORT)
                .addService(new EnumeratorService()).build();
        server.start();
        server.awaitTermination();
    }
}
