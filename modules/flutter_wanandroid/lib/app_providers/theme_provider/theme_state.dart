import 'package:equatable/equatable.dart';
import 'package:flutter/material.dart';

class ThemeState extends Equatable {
  const ThemeState({required this.mode});

  final ThemeMode mode;

  bool get isDark => mode == ThemeMode.dark;

  ThemeState copyWith({ThemeMode? mode}) {
    return ThemeState(mode: mode?? this.mode);
  }

  @override
  List<Object?> get props => [mode];
}


