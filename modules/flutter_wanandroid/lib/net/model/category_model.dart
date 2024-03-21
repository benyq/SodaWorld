// To parse this JSON data, do
//
//     final categoryModel = categoryModelFromJson(jsonString);

import 'dart:convert';

import 'package:flutter_wanandroid/net/model/article_model.dart';

CategoryModel categoryModelFromJson(String str) => CategoryModel.fromJson(json.decode(str));

String categoryModelToJson(CategoryModel data) => json.encode(data.toJson());

class CategoryModel {
  String author;
  List<CategoryModel> children;
  int courseId;
  String cover;
  String desc;
  int id;
  String? link;
  String lisense;
  String lisenseLink;
  String name;
  int order;
  int parentChapterId;
  int type;
  bool userControlSetTop;
  int visible;

  CategoryModel({
    required this.author,
    required this.children,
    required this.courseId,
    required this.cover,
    required this.desc,
    required this.id,
    required this.lisense,
    required this.lisenseLink,
    required this.name,
    required this.order,
    required this.parentChapterId,
    required this.type,
    required this.userControlSetTop,
    required this.visible,
  });

  factory CategoryModel.fromJson(Map<String, dynamic> json) => CategoryModel(
    author: json["author"],
    children: List<CategoryModel>.from(json["children"].map((x) => CategoryModel.fromJson(x))),
    courseId: json["courseId"],
    cover: json["cover"],
    desc: json["desc"],
    id: json["id"],
    lisense: json["lisense"],
    lisenseLink: json["lisenseLink"],
    name: json["name"],
    order: json["order"],
    parentChapterId: json["parentChapterId"],
    type: json["type"],
    userControlSetTop: json["userControlSetTop"],
    visible: json["visible"],
  );

  Map<String, dynamic> toJson() => {
    "author": author,
    "children": List<dynamic>.from(children.map((x) => x.toJson())),
    "courseId": courseId,
    "cover": cover,
    "desc": desc,
    "id": id,
    "link": link,
    "lisense": lisense,
    "lisenseLink": lisenseLink,
    "name": name,
    "order": order,
    "parentChapterId": parentChapterId,
    "type": type,
    "userControlSetTop": userControlSetTop,
    "visible": visible,
  };
}
