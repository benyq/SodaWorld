import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:flutter_wanandroid/app_providers/theme_provider/themes.dart';
import 'package:flutter_wanandroid/app_providers/theme_provider/themes_provider.dart';
import 'package:flutter_wanandroid/ui/project/provider/project_provider.dart';
import 'project_page.dart';

class ProjectTreePage extends ConsumerStatefulWidget {
  const ProjectTreePage({super.key});

  @override
  ConsumerState<ConsumerStatefulWidget> createState() =>
      _ProjectTreePageState();
}

class _ProjectTreePageState extends ConsumerState<ProjectTreePage>
    with AutomaticKeepAliveClientMixin {

  @override
  Widget build(BuildContext context) {
    super.build(context);
    final projectTree = ref.watch(projectTreeProvider);
    final themeState = ref.watch(themesProvider);
    return projectTree.when(
      loading: () => Center(
        child: SizedBox.square(
          dimension: 60.w,
          child: const CircularProgressIndicator(),
        ),
      ),
      error: (err, stack) => Center(child: Text('Error: $err')),
      data: (projects) => DefaultTabController(
        length: projects.length,
        child: Column(
          children: [
            Container(
              height: 35.h,
              color: themeState.isDark ? Themes.darkColor : Themes.lightColor,
              child: TabBar(
                  physics: const BouncingScrollPhysics(),
                  indicator: const BoxDecoration(),
                  unselectedLabelColor:
                      themeState.isDark ? Colors.white : Colors.black,
                  labelColor: Colors.blue,
                  isScrollable: true,
                  tabs: projects.map((e) => Text(e.name)).toList()),
            ),
            Expanded(
                child: TabBarView(
                    physics: const BouncingScrollPhysics(),
                    children: projects.map((e) => ProjectPage(e.id)).toList())),
          ],
        ),
      ),
    );
  }

  @override
  bool get wantKeepAlive => true;
}
