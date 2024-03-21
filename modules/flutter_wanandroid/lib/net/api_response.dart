import 'package:json_annotation/json_annotation.dart';

part 'generated/api_response.g.dart';

@JsonSerializable(genericArgumentFactories: true)
class ApiResponse<T> {
  final String errorMsg;
  final int errorCode;
  final T? data;

  ApiResponse({required this.errorMsg, required this.errorCode, this.data});

  bool get isSuccess => errorCode == 0 && data != null;

  factory ApiResponse.fromJson(
      Map<String, dynamic> json, T Function(Object? json) fromJsonT) =>
      _$ApiResponseFromJson(json, fromJsonT);

  Map<String, dynamic> toJson(Object Function(T value) toJsonT) =>
      _$ApiResponseToJson(this, toJsonT);
}