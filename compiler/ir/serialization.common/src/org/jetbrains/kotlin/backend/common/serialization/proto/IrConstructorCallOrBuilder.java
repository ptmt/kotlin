// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: compiler/ir/serialization.common/src/KotlinIr.proto

package org.jetbrains.kotlin.backend.common.serialization.proto;

public interface IrConstructorCallOrBuilder extends
    // @@protoc_insertion_point(interface_extends:org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCall)
    org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder {

  /**
   * <code>required .org.jetbrains.kotlin.backend.common.serialization.proto.IrDataIndex symbol = 1;</code>
   */
  boolean hasSymbol();
  /**
   * <code>required .org.jetbrains.kotlin.backend.common.serialization.proto.IrDataIndex symbol = 1;</code>
   */
  org.jetbrains.kotlin.backend.common.serialization.proto.IrDataIndex getSymbol();

  /**
   * <code>required int32 constructor_type_arguments_count = 2;</code>
   */
  boolean hasConstructorTypeArgumentsCount();
  /**
   * <code>required int32 constructor_type_arguments_count = 2;</code>
   */
  int getConstructorTypeArgumentsCount();

  /**
   * <code>required .org.jetbrains.kotlin.backend.common.serialization.proto.MemberAccessCommon member_access = 3;</code>
   */
  boolean hasMemberAccess();
  /**
   * <code>required .org.jetbrains.kotlin.backend.common.serialization.proto.MemberAccessCommon member_access = 3;</code>
   */
  org.jetbrains.kotlin.backend.common.serialization.proto.MemberAccessCommon getMemberAccess();
}