
import 'dart:convert';

BannerModel bannerModelFromJson(String str) => BannerModel.fromJson(json.decode(str));

String bannerModelToJson(BannerModel data) => json.encode(data.toJson());

class BannerModel {
	String desc;
	int id;
	String imagePath;
	int isVisible;
	int order;
	String title;
	int type;
	String url;

	BannerModel({
		required this.desc,
		required this.id,
		required this.imagePath,
		required this.isVisible,
		required this.order,
		required this.title,
		required this.type,
		required this.url,
	});

	factory BannerModel.fromJson(Map<String, dynamic> json) => BannerModel(
		desc: json["desc"],
		id: json["id"],
		imagePath: json["imagePath"],
		isVisible: json["isVisible"],
		order: json["order"],
		title: json["title"],
		type: json["type"],
		url: json["url"],
	);

	Map<String, dynamic> toJson() => {
		"desc": desc,
		"id": id,
		"imagePath": imagePath,
		"isVisible": isVisible,
		"order": order,
		"title": title,
		"type": type,
		"url": url,
	};
}
