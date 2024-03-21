// To parse this JSON data, do
//
//     final hotWordModel = hotWordModelFromJson(jsonString);

import 'dart:convert';

HotWordModel hotWordModelFromJson(String str) => HotWordModel.fromJson(json.decode(str));

String hotWordModelToJson(HotWordModel data) => json.encode(data.toJson());

class HotWordModel {
  int id;
  String link;
  String name;
  int order;
  int visible;

  HotWordModel({
    required this.id,
    required this.link,
    required this.name,
    required this.order,
    required this.visible,
  });

  factory HotWordModel.fromJson(Map<String, dynamic> json) => HotWordModel(
    id: json["id"],
    link: json["link"],
    name: json["name"],
    order: json["order"],
    visible: json["visible"],
  );

  Map<String, dynamic> toJson() => {
    "id": id,
    "link": link,
    "name": name,
    "order": order,
    "visible": visible,
  };
}
