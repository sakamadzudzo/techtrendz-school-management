import 'package:flutter/material.dart';
import '../../../core/constants/dimensions.dart';

class CustomAppBar extends StatelessWidget implements PreferredSizeWidget {
  final String title;
  final bool showBackButton;
  final List<Widget>? actions;
  final Widget? leading;
  final double? elevation;
  final Color? backgroundColor;
  final Color? foregroundColor;
  final double? toolbarHeight;

  const CustomAppBar({
    super.key,
    required this.title,
    this.showBackButton = true,
    this.actions,
    this.leading,
    this.elevation,
    this.backgroundColor,
    this.foregroundColor,
    this.toolbarHeight,
  });

  @override
  Widget build(BuildContext context) {
    return AppBar(
      title: Text(
        title,
        style: Theme.of(context).textTheme.titleLarge?.copyWith(
          color: foregroundColor ?? Theme.of(context).colorScheme.onSurface,
          fontWeight: FontWeight.w600,
        ),
      ),
      centerTitle: true,
      elevation: elevation ?? 0,
      backgroundColor: backgroundColor ?? Theme.of(context).colorScheme.surface,
      foregroundColor: foregroundColor ?? Theme.of(context).colorScheme.onSurface,
      toolbarHeight: toolbarHeight ?? AppDimensions.appBarHeightM,
      automaticallyImplyLeading: showBackButton,
      leading: leading,
      actions: actions,
      surfaceTintColor: Colors.transparent,
      shadowColor: Colors.transparent,
    );
  }

  @override
  Size get preferredSize => Size.fromHeight(toolbarHeight ?? AppDimensions.appBarHeightM);
}