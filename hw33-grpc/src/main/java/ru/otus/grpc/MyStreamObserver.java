package ru.otus.grpc;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.Response;

public class MyStreamObserver implements StreamObserver<Response> {

    private int currentValue;

    @Override
    public void onNext(Response value) {
        setCurrentValue(value.getCurrentValue());
        System.out.println("число от сервера: " + currentValue);
    }

    @Override
    public void onError(Throwable t) {
        //empty
    }

    @Override
    public void onCompleted() {
        //empty
    }

    private synchronized void setCurrentValue(int value) {
        this.currentValue = value;
    }

    public synchronized int poll() {
        int i = currentValue;
        currentValue = 0;
        return i;
    }
}
