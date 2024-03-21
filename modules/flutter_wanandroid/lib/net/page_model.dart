import 'package:json_annotation/json_annotation.dart';

part 'generated/page_model.g.dart';

@JsonSerializable(genericArgumentFactories: true)
class PageModel<T> {
  final int curPage;
  final int offset;
  final bool over;
  final int pageCount;
  final int size;
  final int total;
  final List<T>? datas;


  PageModel({required this.curPage, required this.offset, required this.over, required this.pageCount, required this.size,
    required this.total, this.datas});

  factory PageModel.fromJson(
      Map<String, dynamic> json, T Function(Object? json) fromJsonT) =>
      _$PageModelFromJson(json, fromJsonT);

  Map<String, dynamic> toJson(Object Function(T value) toJsonT) =>
      _$PageModelToJson(this, toJsonT);
}