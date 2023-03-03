package ru.otus.grpc.service;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.EnumeratorGrpc;
import ru.otus.protobuf.generated.Request;
import ru.otus.protobuf.generated.Response;

public class EnumeratorService extends EnumeratorGrpc.EnumeratorImplBase {

    @Override
    public void sequenceOfNumber(Request request, StreamObserver<Response> responseObserver) {
        for (int i = request.getFirstValue(); i <= request.getLastValue(); i++) {
            responseObserver.onNext(Response.newBuilder().setCurrentValue(i).build());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignore) {
            }
        }
        responseObserver.onCompleted();
    }
}
