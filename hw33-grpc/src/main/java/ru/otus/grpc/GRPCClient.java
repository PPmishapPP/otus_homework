package ru.otus.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import ru.otus.protobuf.generated.EnumeratorGrpc;
import ru.otus.protobuf.generated.Request;

public class GRPCClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;


    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        EnumeratorGrpc.EnumeratorStub stub = EnumeratorGrpc.newStub(channel);
        MyStreamObserver myObserver = new MyStreamObserver();
        stub.sequenceOfNumber(
                Request.newBuilder().setFirstValue(0).setLastValue(30).build(),
                myObserver
        );

        int currentValue = 0;
        for (int i = 0; i <= 50; i++) {
            currentValue = currentValue + myObserver.poll() + 1;
            System.out.println("currentValue: " + currentValue);
            Thread.sleep(1000);
        }

        channel.shutdown();
    }
}
