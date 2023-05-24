package com.zyc;

import com.zyc.proto.HelloGrpc;
import com.zyc.proto.HelloReply;
import com.zyc.proto.HelloRequest;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;

@GrpcService
@Slf4j
public class HelloGrpcService implements HelloGrpc {

    @Override
    public Uni<HelloReply> sayHello(HelloRequest request) {
        log.info("[sayHello]-被调用，name={}", request.getName());
        return Uni.createFrom().item("Hello " + request.getName() + "!")
            .map(msg -> HelloReply.newBuilder().setMessage(msg).build());
    }

}
