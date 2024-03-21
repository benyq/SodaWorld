import 'package:equatable/equatable.dart';

class UserState extends Equatable {
  final int id;
  final String username;
  final int coinCount;
  final List<int>? collectIds;
  final String rank;

  const UserState(
      {this.id = -1, this.username = '', this.coinCount = -1, this.collectIds, this.rank = ''});

  bool get isLoggedIn => id != -1 && username.isNotEmpty;

  String get coin => coinCount >= 0 ? coinCount.toString() : '-';

  String get collects => collectIds?.length.toString() ?? '-';

  String get realRank => rank.isNotEmpty ? rank : '-';

  UserState copyWith(
      {int? id, String? username, int? coinCount, List<int>? collectIds, String? rank}) {
    return UserState(
        id: id ?? this.id,
        username: username ?? this.username,
        coinCount: coinCount ?? this.coinCount,
        rank: rank ?? this.rank,
        collectIds: collectIds ?? this.collectIds);
  }

  @override
  List<Object?> get props => [id, username, coinCount, collectIds, rank];
}
