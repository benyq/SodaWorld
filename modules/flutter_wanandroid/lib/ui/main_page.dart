import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_wanandroid/app_providers/nav_provider/nav_notifier.dart';
import 'package:flutter_wanandroid/app_providers/user_provider/user_provider.dart';
import 'package:flutter_wanandroid/generated/l10n.dart';
import 'package:flutter_wanandroid/ui/category/category_page.dart';
import 'package:flutter_wanandroid/ui/home/home_page.dart';
import 'package:flutter_wanandroid/ui/me/me_page.dart';
import 'package:flutter_wanandroid/ui/project/project_tree_page.dart';
import 'package:flutter_wanandroid/utils/navigate_util.dart';

import 'search/search_page.dart';

class MainPage extends ConsumerStatefulWidget {
  const MainPage({super.key});

  @override
  ConsumerState<MainPage> createState() => _MainPageState();
}

class _MainPageState extends ConsumerState<MainPage> {

  static const List<Widget> _pages = [
    HomePage(),
    ProjectTreePage(),
    CategoryPage(),
    MePage()
  ];

  late PageController _pageController;

  @override
  void initState() {
    super.initState();
    final navState = ref.read(navStateProvider);
    _pageController = PageController(initialPage: navState.index);
    ref.read(userProvider.notifier).getUserInfo();
  }

  @override
  void dispose() {
    super.dispose();
    _pageController.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final navState = ref.watch(navStateProvider);
    List<_BottomData> _bottomItemData = [
      _BottomData(S.of(context).tab_home, Icons.home),
      _BottomData(S.of(context).tab_project, Icons.local_fire_department),
      _BottomData(S.of(context).tab_category, Icons.category),
      _BottomData(S.of(context).tab_me, Icons.person)
    ];
    return Scaffold(
      appBar: navState.index != 3 ? AppBar(
        title: Text(_bottomItemData[navState.index].label),
        automaticallyImplyLeading: false,
        actions: [
          Offstage(
            offstage: navState.index != 0,
            child: InkWell(
                onTap: () {
                  navigateTo(context, const SearchPage());
                },
                child: const SizedBox.square(
                  dimension: 50,
                  child: Icon(
                    Icons.search_rounded,
                    size: 30,
                  ),
                )),
          )
        ],
        elevation: 0,
        toolbarHeight: 50,
      ): null,
      body: PageView(
        physics: const NeverScrollableScrollPhysics(),
        controller: _pageController,
        onPageChanged: (index){
          ref.read(navStateProvider.notifier).changeIndex(index);
        },
        children: _pages,
      ),
      bottomNavigationBar: BottomNavigationBar(
          items: [
            ..._bottomItemData.map((e) => _bottomItem(e.label, e.iconData))
          ],
          onTap: (index) {
            _changeNavIndex(index);
          },
          currentIndex: navState.index),
    );
  }

  void _changeNavIndex(int index) {
    ref.read(navStateProvider.notifier).changeIndex(index);
    _pageController.jumpToPage(index);
  }

  BottomNavigationBarItem _bottomItem(String label, IconData iconData) {
    return BottomNavigationBarItem(
        icon: Icon(iconData),
        label: label,
        backgroundColor: Theme.of(context).bottomNavigationBarTheme.backgroundColor);
  }
}

class _BottomData {
  IconData iconData;
  String label;

  _BottomData(this.label, this.iconData);
}
