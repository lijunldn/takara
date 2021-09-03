package me.takara.gemis.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: Gemis.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class RemotePullingServiceGrpc {

  private RemotePullingServiceGrpc() {}

  public static final String SERVICE_NAME = "me.takara.core.RemotePullingService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<me.takara.gemis.grpc.GrpcPullReq,
      me.takara.gemis.grpc.GrpcPullResp> getPullMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Pull",
      requestType = me.takara.gemis.grpc.GrpcPullReq.class,
      responseType = me.takara.gemis.grpc.GrpcPullResp.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<me.takara.gemis.grpc.GrpcPullReq,
      me.takara.gemis.grpc.GrpcPullResp> getPullMethod() {
    io.grpc.MethodDescriptor<me.takara.gemis.grpc.GrpcPullReq, me.takara.gemis.grpc.GrpcPullResp> getPullMethod;
    if ((getPullMethod = RemotePullingServiceGrpc.getPullMethod) == null) {
      synchronized (RemotePullingServiceGrpc.class) {
        if ((getPullMethod = RemotePullingServiceGrpc.getPullMethod) == null) {
          RemotePullingServiceGrpc.getPullMethod = getPullMethod =
              io.grpc.MethodDescriptor.<me.takara.gemis.grpc.GrpcPullReq, me.takara.gemis.grpc.GrpcPullResp>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Pull"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  me.takara.gemis.grpc.GrpcPullReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  me.takara.gemis.grpc.GrpcPullResp.getDefaultInstance()))
              .setSchemaDescriptor(new RemotePullingServiceMethodDescriptorSupplier("Pull"))
              .build();
        }
      }
    }
    return getPullMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static RemotePullingServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RemotePullingServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RemotePullingServiceStub>() {
        @java.lang.Override
        public RemotePullingServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RemotePullingServiceStub(channel, callOptions);
        }
      };
    return RemotePullingServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static RemotePullingServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RemotePullingServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RemotePullingServiceBlockingStub>() {
        @java.lang.Override
        public RemotePullingServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RemotePullingServiceBlockingStub(channel, callOptions);
        }
      };
    return RemotePullingServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static RemotePullingServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RemotePullingServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RemotePullingServiceFutureStub>() {
        @java.lang.Override
        public RemotePullingServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RemotePullingServiceFutureStub(channel, callOptions);
        }
      };
    return RemotePullingServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class RemotePullingServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void pull(me.takara.gemis.grpc.GrpcPullReq request,
        io.grpc.stub.StreamObserver<me.takara.gemis.grpc.GrpcPullResp> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPullMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getPullMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                me.takara.gemis.grpc.GrpcPullReq,
                me.takara.gemis.grpc.GrpcPullResp>(
                  this, METHODID_PULL)))
          .build();
    }
  }

  /**
   */
  public static final class RemotePullingServiceStub extends io.grpc.stub.AbstractAsyncStub<RemotePullingServiceStub> {
    private RemotePullingServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RemotePullingServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RemotePullingServiceStub(channel, callOptions);
    }

    /**
     */
    public void pull(me.takara.gemis.grpc.GrpcPullReq request,
        io.grpc.stub.StreamObserver<me.takara.gemis.grpc.GrpcPullResp> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPullMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class RemotePullingServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<RemotePullingServiceBlockingStub> {
    private RemotePullingServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RemotePullingServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RemotePullingServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public me.takara.gemis.grpc.GrpcPullResp pull(me.takara.gemis.grpc.GrpcPullReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPullMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class RemotePullingServiceFutureStub extends io.grpc.stub.AbstractFutureStub<RemotePullingServiceFutureStub> {
    private RemotePullingServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RemotePullingServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RemotePullingServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<me.takara.gemis.grpc.GrpcPullResp> pull(
        me.takara.gemis.grpc.GrpcPullReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPullMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_PULL = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final RemotePullingServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(RemotePullingServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PULL:
          serviceImpl.pull((me.takara.gemis.grpc.GrpcPullReq) request,
              (io.grpc.stub.StreamObserver<me.takara.gemis.grpc.GrpcPullResp>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class RemotePullingServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    RemotePullingServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return me.takara.gemis.grpc.GemisGrpc.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("RemotePullingService");
    }
  }

  private static final class RemotePullingServiceFileDescriptorSupplier
      extends RemotePullingServiceBaseDescriptorSupplier {
    RemotePullingServiceFileDescriptorSupplier() {}
  }

  private static final class RemotePullingServiceMethodDescriptorSupplier
      extends RemotePullingServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    RemotePullingServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (RemotePullingServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new RemotePullingServiceFileDescriptorSupplier())
              .addMethod(getPullMethod())
              .build();
        }
      }
    }
    return result;
  }
}
