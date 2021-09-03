// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Gemis.proto

package me.takara.gemis.grpc;

public interface GrpcPullRespOrBuilder extends
    // @@protoc_insertion_point(interface_extends:me.takara.core.GrpcPullResp)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.me.takara.core.GrpcSyncStamp syncStamp = 1;</code>
   * @return Whether the syncStamp field is set.
   */
  boolean hasSyncStamp();
  /**
   * <code>.me.takara.core.GrpcSyncStamp syncStamp = 1;</code>
   * @return The syncStamp.
   */
  me.takara.gemis.grpc.GrpcSyncStamp getSyncStamp();
  /**
   * <code>.me.takara.core.GrpcSyncStamp syncStamp = 1;</code>
   */
  me.takara.gemis.grpc.GrpcSyncStampOrBuilder getSyncStampOrBuilder();

  /**
   * <code>repeated .me.takara.core.GrpcBond bonds = 2;</code>
   */
  java.util.List<me.takara.gemis.grpc.GrpcBond> 
      getBondsList();
  /**
   * <code>repeated .me.takara.core.GrpcBond bonds = 2;</code>
   */
  me.takara.gemis.grpc.GrpcBond getBonds(int index);
  /**
   * <code>repeated .me.takara.core.GrpcBond bonds = 2;</code>
   */
  int getBondsCount();
  /**
   * <code>repeated .me.takara.core.GrpcBond bonds = 2;</code>
   */
  java.util.List<? extends me.takara.gemis.grpc.GrpcBondOrBuilder> 
      getBondsOrBuilderList();
  /**
   * <code>repeated .me.takara.core.GrpcBond bonds = 2;</code>
   */
  me.takara.gemis.grpc.GrpcBondOrBuilder getBondsOrBuilder(
      int index);

  /**
   * <code>repeated .me.takara.core.GrpcEquity equities = 3;</code>
   */
  java.util.List<me.takara.gemis.grpc.GrpcEquity> 
      getEquitiesList();
  /**
   * <code>repeated .me.takara.core.GrpcEquity equities = 3;</code>
   */
  me.takara.gemis.grpc.GrpcEquity getEquities(int index);
  /**
   * <code>repeated .me.takara.core.GrpcEquity equities = 3;</code>
   */
  int getEquitiesCount();
  /**
   * <code>repeated .me.takara.core.GrpcEquity equities = 3;</code>
   */
  java.util.List<? extends me.takara.gemis.grpc.GrpcEquityOrBuilder> 
      getEquitiesOrBuilderList();
  /**
   * <code>repeated .me.takara.core.GrpcEquity equities = 3;</code>
   */
  me.takara.gemis.grpc.GrpcEquityOrBuilder getEquitiesOrBuilder(
      int index);
}
