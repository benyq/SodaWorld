// GENERATED CODE - DO NOT MODIFY BY HAND

part of '../page_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

PageModel<T> _$PageModelFromJson<T>(
  Map<String, dynamic> json,
  T Function(Object? json) fromJsonT,
) =>
    PageModel<T>(
      curPage: json['curPage'] as int,
      offset: json['offset'] as int,
      over: json['over'] as bool,
      pageCount: json['pageCount'] as int,
      size: json['size'] as int,
      total: json['total'] as int,
      datas: (json['datas'] as List<dynamic>?)?.map(fromJsonT).toList(),
    );

Map<String, dynamic> _$PageModelToJson<T>(
  PageModel<T> instance,
  Object? Function(T value) toJsonT,
) =>
    <String, dynamic>{
      'curPage': instance.curPage,
      'offset': instance.offset,
      'over': instance.over,
      'pageCount': instance.pageCount,
      'size': instance.size,
      'total': instance.total,
      'datas': instance.datas?.map(toJsonT).toList(),
    };
