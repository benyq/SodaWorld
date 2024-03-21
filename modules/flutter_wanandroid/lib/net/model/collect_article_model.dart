// To parse this JSON data, do
//
//     final collectArticleModel = collectArticleModelFromJson(jsonString);

import 'dart:convert';

CollectArticleModel collectArticleModelFromJson(String str) => CollectArticleModel.fromJson(json.decode(str));

String collectArticleModelToJson(CollectArticleModel data) => json.encode(data.toJson());

class CollectArticleModel {
  String author;
  int chapterId;
  String chapterName;
  int courseId;
  String desc;
  String envelopePic;
  int id;
  String link;
  String niceDate;
  String origin;
  int originId;
  int publishTime;
  String title;
  int userId;
  int visible;
  int zan;

  CollectArticleModel({
    required this.author,
    required this.chapterId,
    required this.chapterName,
    required this.courseId,
    required this.desc,
    required this.envelopePic,
    required this.id,
    required this.link,
    required this.niceDate,
    required this.origin,
    required this.originId,
    required this.publishTime,
    required this.title,
    required this.userId,
    required this.visible,
    required this.zan,
  });

  factory CollectArticleModel.fromJson(Map<String, dynamic> json) => CollectArticleModel(
    author: json["author"],
    chapterId: json["chapterId"],
    chapterName: json["chapterName"],
    courseId: json["courseId"],
    desc: json["desc"],
    envelopePic: json["envelopePic"],
    id: json["id"],
    link: json["link"],
    niceDate: json["niceDate"],
    origin: json["origin"],
    originId: json["originId"],
    publishTime: json["publishTime"],
    title: json["title"],
    userId: json["userId"],
    visible: json["visible"],
    zan: json["zan"],
  );

  Map<String, dynamic> toJson() => {
    "author": author,
    "chapterId": chapterId,
    "chapterName": chapterName,
    "courseId": courseId,
    "desc": desc,
    "envelopePic": envelopePic,
    "id": id,
    "link": link,
    "niceDate": niceDate,
    "origin": origin,
    "originId": originId,
    "publishTime": publishTime,
    "title": title,
    "userId": userId,
    "visible": visible,
    "zan": zan,
  };
}
