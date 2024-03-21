// GENERATED CODE - DO NOT MODIFY BY HAND

part of '../project_provider.dart';

// **************************************************************************
// RiverpodGenerator
// **************************************************************************

String _$projectTreeHash() => r'0fa2e628c8263b07a241157f7b807c853dda69d7';

/// See also [projectTree].
@ProviderFor(projectTree)
final projectTreeProvider =
    AutoDisposeFutureProvider<List<ProjectTreeModel>>.internal(
  projectTree,
  name: r'projectTreeProvider',
  debugGetCreateSourceHash:
      const bool.fromEnvironment('dart.vm.product') ? null : _$projectTreeHash,
  dependencies: null,
  allTransitiveDependencies: null,
);

typedef ProjectTreeRef = AutoDisposeFutureProviderRef<List<ProjectTreeModel>>;
String _$projectNotifierHash() => r'00d31cd9543a319cfaa4182e0731300ee891c6ca';

/// Copied from Dart SDK
class _SystemHash {
  _SystemHash._();

  static int combine(int hash, int value) {
    // ignore: parameter_assignments
    hash = 0x1fffffff & (hash + value);
    // ignore: parameter_assignments
    hash = 0x1fffffff & (hash + ((0x0007ffff & hash) << 10));
    return hash ^ (hash >> 6);
  }

  static int finish(int hash) {
    // ignore: parameter_assignments
    hash = 0x1fffffff & (hash + ((0x03ffffff & hash) << 3));
    // ignore: parameter_assignments
    hash = hash ^ (hash >> 11);
    return 0x1fffffff & (hash + ((0x00003fff & hash) << 15));
  }
}

abstract class _$ProjectNotifier
    extends BuildlessAutoDisposeNotifier<ProjectState> {
  late final int cid;

  ProjectState build(
    int cid,
  );
}

/// See also [ProjectNotifier].
@ProviderFor(ProjectNotifier)
const projectNotifierProvider = ProjectNotifierFamily();

/// See also [ProjectNotifier].
class ProjectNotifierFamily extends Family<ProjectState> {
  /// See also [ProjectNotifier].
  const ProjectNotifierFamily();

  /// See also [ProjectNotifier].
  ProjectNotifierProvider call(
    int cid,
  ) {
    return ProjectNotifierProvider(
      cid,
    );
  }

  @override
  ProjectNotifierProvider getProviderOverride(
    covariant ProjectNotifierProvider provider,
  ) {
    return call(
      provider.cid,
    );
  }

  static const Iterable<ProviderOrFamily>? _dependencies = null;

  @override
  Iterable<ProviderOrFamily>? get dependencies => _dependencies;

  static const Iterable<ProviderOrFamily>? _allTransitiveDependencies = null;

  @override
  Iterable<ProviderOrFamily>? get allTransitiveDependencies =>
      _allTransitiveDependencies;

  @override
  String? get name => r'projectNotifierProvider';
}

/// See also [ProjectNotifier].
class ProjectNotifierProvider
    extends AutoDisposeNotifierProviderImpl<ProjectNotifier, ProjectState> {
  /// See also [ProjectNotifier].
  ProjectNotifierProvider(
    int cid,
  ) : this._internal(
          () => ProjectNotifier()..cid = cid,
          from: projectNotifierProvider,
          name: r'projectNotifierProvider',
          debugGetCreateSourceHash:
              const bool.fromEnvironment('dart.vm.product')
                  ? null
                  : _$projectNotifierHash,
          dependencies: ProjectNotifierFamily._dependencies,
          allTransitiveDependencies:
              ProjectNotifierFamily._allTransitiveDependencies,
          cid: cid,
        );

  ProjectNotifierProvider._internal(
    super._createNotifier, {
    required super.name,
    required super.dependencies,
    required super.allTransitiveDependencies,
    required super.debugGetCreateSourceHash,
    required super.from,
    required this.cid,
  }) : super.internal();

  final int cid;

  @override
  ProjectState runNotifierBuild(
    covariant ProjectNotifier notifier,
  ) {
    return notifier.build(
      cid,
    );
  }

  @override
  Override overrideWith(ProjectNotifier Function() create) {
    return ProviderOverride(
      origin: this,
      override: ProjectNotifierProvider._internal(
        () => create()..cid = cid,
        from: from,
        name: null,
        dependencies: null,
        allTransitiveDependencies: null,
        debugGetCreateSourceHash: null,
        cid: cid,
      ),
    );
  }

  @override
  AutoDisposeNotifierProviderElement<ProjectNotifier, ProjectState>
      createElement() {
    return _ProjectNotifierProviderElement(this);
  }

  @override
  bool operator ==(Object other) {
    return other is ProjectNotifierProvider && other.cid == cid;
  }

  @override
  int get hashCode {
    var hash = _SystemHash.combine(0, runtimeType.hashCode);
    hash = _SystemHash.combine(hash, cid.hashCode);

    return _SystemHash.finish(hash);
  }
}

mixin ProjectNotifierRef on AutoDisposeNotifierProviderRef<ProjectState> {
  /// The parameter `cid` of this provider.
  int get cid;
}

class _ProjectNotifierProviderElement
    extends AutoDisposeNotifierProviderElement<ProjectNotifier, ProjectState>
    with ProjectNotifierRef {
  _ProjectNotifierProviderElement(super.provider);

  @override
  int get cid => (origin as ProjectNotifierProvider).cid;
}
// ignore_for_file: type=lint
// ignore_for_file: subtype_of_sealed_class, invalid_use_of_internal_member, invalid_use_of_visible_for_testing_member
