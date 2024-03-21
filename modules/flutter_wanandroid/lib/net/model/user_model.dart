// To parse this JSON data, do
//
//     final userModel = userModelFromJson(jsonString);

import 'dart:convert';

UserModel userModelFromJson(String str) => UserModel.fromJson(json.decode(str));

String userModelToJson(UserModel data) => json.encode(data.toJson());

class UserModel {
  CoinInfo coinInfo;
  UserInfo userInfo;

  UserModel({
    required this.coinInfo,
    required this.userInfo,
  });

  factory UserModel.fromJson(Map<String, dynamic> json) => UserModel(
    coinInfo: CoinInfo.fromJson(json["coinInfo"]),
    userInfo: UserInfo.fromJson(json["userInfo"]),
  );

  Map<String, dynamic> toJson() => {
    "coinInfo": coinInfo.toJson(),
    "userInfo": userInfo.toJson(),
  };
}

class CoinInfo {
  int coinCount;
  int level;
  String nickname;
  String rank;
  int userId;
  String username;

  CoinInfo({
    required this.coinCount,
    required this.level,
    required this.nickname,
    required this.rank,
    required this.userId,
    required this.username,
  });

  factory CoinInfo.fromJson(Map<String, dynamic> json) => CoinInfo(
    coinCount: json["coinCount"],
    level: json["level"],
    nickname: json["nickname"],
    rank: json["rank"],
    userId: json["userId"],
    username: json["username"],
  );

  Map<String, dynamic> toJson() => {
    "coinCount": coinCount,
    "level": level,
    "nickname": nickname,
    "rank": rank,
    "userId": userId,
    "username": username,
  };
}

class UserInfo {
  bool admin;
  List<int> chapterTops;
  int coinCount;
  List<int> collectIds;
  String email;
  String icon;
  int id;
  String nickname;
  String password;
  String publicName;
  String token;
  int type;
  String username;

  UserInfo({
    required this.admin,
    required this.chapterTops,
    required this.coinCount,
    required this.collectIds,
    required this.email,
    required this.icon,
    required this.id,
    required this.nickname,
    required this.password,
    required this.publicName,
    required this.token,
    required this.type,
    required this.username,
  });

  factory UserInfo.fromJson(Map<String, dynamic> json) => UserInfo(
    admin: json["admin"],
    chapterTops: List<int>.from(json["chapterTops"].map((x) => x)),
    coinCount: json["coinCount"],
    collectIds: List<int>.from(json["collectIds"].map((x) => x)),
    email: json["email"],
    icon: json["icon"],
    id: json["id"],
    nickname: json["nickname"],
    password: json["password"],
    publicName: json["publicName"],
    token: json["token"],
    type: json["type"],
    username: json["username"],
  );

  Map<String, dynamic> toJson() => {
    "admin": admin,
    "chapterTops": List<dynamic>.from(chapterTops.map((x) => x)),
    "coinCount": coinCount,
    "collectIds": List<dynamic>.from(collectIds.map((x) => x)),
    "email": email,
    "icon": icon,
    "id": id,
    "nickname": nickname,
    "password": password,
    "publicName": publicName,
    "token": token,
    "type": type,
    "username": username,
  };
}
