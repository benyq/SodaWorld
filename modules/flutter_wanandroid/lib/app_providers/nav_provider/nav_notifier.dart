import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'nav_state.dart';

class NavNotifier extends AutoDisposeNotifier<NavState> {
  @override
  NavState build() {
    return const NavState();
  }

  void changeIndex(int index) {
    state = state.copyWith(index: index);
  }
}

final navStateProvider = NotifierProvider.autoDispose<NavNotifier, NavState>(()=> NavNotifier());
