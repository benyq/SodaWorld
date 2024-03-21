// GENERATED CODE - DO NOT MODIFY BY HAND

part of '../category_provider.dart';

// **************************************************************************
// RiverpodGenerator
// **************************************************************************

String _$categoryHash() => r'f33b8c1ad8e8f3cc659eddb6aca957ecb8fddbba';

/// See also [category].
@ProviderFor(category)
final categoryProvider =
    AutoDisposeFutureProvider<List<CategoryModel>>.internal(
  category,
  name: r'categoryProvider',
  debugGetCreateSourceHash:
      const bool.fromEnvironment('dart.vm.product') ? null : _$categoryHash,
  dependencies: null,
  allTransitiveDependencies: null,
);

typedef CategoryRef = AutoDisposeFutureProviderRef<List<CategoryModel>>;
String _$categoryNotifierHash() => r'f5efdf7e918064d00f0f1a0fb303533f5b7ce742';

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

abstract class _$CategoryNotifier
    extends BuildlessAutoDisposeNotifier<CategoryState> {
  late final int cid;

  CategoryState build(
    int cid,
  );
}

/// See also [CategoryNotifier].
@ProviderFor(CategoryNotifier)
const categoryNotifierProvider = CategoryNotifierFamily();

/// See also [CategoryNotifier].
class CategoryNotifierFamily extends Family<CategoryState> {
  /// See also [CategoryNotifier].
  const CategoryNotifierFamily();

  /// See also [CategoryNotifier].
  CategoryNotifierProvider call(
    int cid,
  ) {
    return CategoryNotifierProvider(
      cid,
    );
  }

  @override
  CategoryNotifierProvider getProviderOverride(
    covariant CategoryNotifierProvider provider,
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
  String? get name => r'categoryNotifierProvider';
}

/// See also [CategoryNotifier].
class CategoryNotifierProvider
    extends AutoDisposeNotifierProviderImpl<CategoryNotifier, CategoryState> {
  /// See also [CategoryNotifier].
  CategoryNotifierProvider(
    int cid,
  ) : this._internal(
          () => CategoryNotifier()..cid = cid,
          from: categoryNotifierProvider,
          name: r'categoryNotifierProvider',
          debugGetCreateSourceHash:
              const bool.fromEnvironment('dart.vm.product')
                  ? null
                  : _$categoryNotifierHash,
          dependencies: CategoryNotifierFamily._dependencies,
          allTransitiveDependencies:
              CategoryNotifierFamily._allTransitiveDependencies,
          cid: cid,
        );

  CategoryNotifierProvider._internal(
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
  CategoryState runNotifierBuild(
    covariant CategoryNotifier notifier,
  ) {
    return notifier.build(
      cid,
    );
  }

  @override
  Override overrideWith(CategoryNotifier Function() create) {
    return ProviderOverride(
      origin: this,
      override: CategoryNotifierProvider._internal(
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
  AutoDisposeNotifierProviderElement<CategoryNotifier, CategoryState>
      createElement() {
    return _CategoryNotifierProviderElement(this);
  }

  @override
  bool operator ==(Object other) {
    return other is CategoryNotifierProvider && other.cid == cid;
  }

  @override
  int get hashCode {
    var hash = _SystemHash.combine(0, runtimeType.hashCode);
    hash = _SystemHash.combine(hash, cid.hashCode);

    return _SystemHash.finish(hash);
  }
}

mixin CategoryNotifierRef on AutoDisposeNotifierProviderRef<CategoryState> {
  /// The parameter `cid` of this provider.
  int get cid;
}

class _CategoryNotifierProviderElement
    extends AutoDisposeNotifierProviderElement<CategoryNotifier, CategoryState>
    with CategoryNotifierRef {
  _CategoryNotifierProviderElement(super.provider);

  @override
  int get cid => (origin as CategoryNotifierProvider).cid;
}
// ignore_for_file: type=lint
// ignore_for_file: subtype_of_sealed_class, invalid_use_of_internal_member, invalid_use_of_visible_for_testing_member
