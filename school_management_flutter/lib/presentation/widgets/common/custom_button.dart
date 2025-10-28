import 'package:flutter/material.dart';
import '../../../core/constants/dimensions.dart';

enum ButtonVariant {
  primary,
  secondary,
  outline,
  ghost,
  danger,
}

enum ButtonSize {
  small,
  medium,
  large,
}

class CustomButton extends StatelessWidget {
  final String text;
  final VoidCallback? onPressed;
  final ButtonVariant variant;
  final ButtonSize size;
  final bool isLoading;
  final bool isDisabled;
  final Widget? leadingIcon;
  final Widget? trailingIcon;
  final double? width;
  final double? height;

  const CustomButton({
    super.key,
    required this.text,
    this.onPressed,
    this.variant = ButtonVariant.primary,
    this.size = ButtonSize.medium,
    this.isLoading = false,
    this.isDisabled = false,
    this.leadingIcon,
    this.trailingIcon,
    this.width,
    this.height,
  });

  double get _buttonHeight {
    switch (size) {
      case ButtonSize.small:
        return AppDimensions.buttonHeightS;
      case ButtonSize.medium:
        return AppDimensions.buttonHeightM;
      case ButtonSize.large:
        return AppDimensions.buttonHeightL;
    }
  }

  EdgeInsets get _padding {
    switch (size) {
      case ButtonSize.small:
        return const EdgeInsets.symmetric(
          horizontal: AppDimensions.paddingM,
          vertical: AppDimensions.paddingS,
        );
      case ButtonSize.medium:
        return const EdgeInsets.symmetric(
          horizontal: AppDimensions.paddingL,
          vertical: AppDimensions.paddingM,
        );
      case ButtonSize.large:
        return const EdgeInsets.symmetric(
          horizontal: AppDimensions.paddingXL,
          vertical: AppDimensions.paddingL,
        );
    }
  }

  TextStyle _getTextStyle(BuildContext context) {
    final baseStyle = Theme.of(context).textTheme.labelLarge ?? const TextStyle();

    switch (size) {
      case ButtonSize.small:
        return baseStyle.copyWith(fontSize: AppDimensions.fontSizeS);
      case ButtonSize.medium:
        return baseStyle.copyWith(fontSize: AppDimensions.fontSizeM);
      case ButtonSize.large:
        return baseStyle.copyWith(fontSize: AppDimensions.fontSizeL);
    }
  }

  ButtonStyle _getButtonStyle(BuildContext context) {
    final colorScheme = Theme.of(context).colorScheme;

    switch (variant) {
      case ButtonVariant.primary:
        return ElevatedButton.styleFrom(
          backgroundColor: colorScheme.primary,
          foregroundColor: colorScheme.onPrimary,
          disabledBackgroundColor: colorScheme.onSurface.withOpacity(0.12),
          disabledForegroundColor: colorScheme.onSurface.withOpacity(0.38),
          elevation: 0,
          shadowColor: Colors.transparent,
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(AppDimensions.radiusM),
          ),
        );

      case ButtonVariant.secondary:
        return ElevatedButton.styleFrom(
          backgroundColor: colorScheme.secondaryContainer,
          foregroundColor: colorScheme.onSecondaryContainer,
          disabledBackgroundColor: colorScheme.onSurface.withOpacity(0.12),
          disabledForegroundColor: colorScheme.onSurface.withOpacity(0.38),
          elevation: 0,
          shadowColor: Colors.transparent,
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(AppDimensions.radiusM),
          ),
        );

      case ButtonVariant.outline:
        return OutlinedButton.styleFrom(
          backgroundColor: Colors.transparent,
          foregroundColor: colorScheme.primary,
          disabledBackgroundColor: Colors.transparent,
          disabledForegroundColor: colorScheme.onSurface.withOpacity(0.38),
          side: BorderSide(color: colorScheme.outline),
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(AppDimensions.radiusM),
          ),
        );

      case ButtonVariant.ghost:
        return TextButton.styleFrom(
          backgroundColor: Colors.transparent,
          foregroundColor: colorScheme.primary,
          disabledBackgroundColor: Colors.transparent,
          disabledForegroundColor: colorScheme.onSurface.withOpacity(0.38),
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(AppDimensions.radiusM),
          ),
        );

      case ButtonVariant.danger:
        return ElevatedButton.styleFrom(
          backgroundColor: colorScheme.error,
          foregroundColor: colorScheme.onError,
          disabledBackgroundColor: colorScheme.onSurface.withOpacity(0.12),
          disabledForegroundColor: colorScheme.onSurface.withOpacity(0.38),
          elevation: 0,
          shadowColor: Colors.transparent,
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(AppDimensions.radiusM),
          ),
        );
    }
  }

  @override
  Widget build(BuildContext context) {
    final buttonStyle = _getButtonStyle(context);
    final effectiveOnPressed = (isDisabled || isLoading) ? null : onPressed;

    return SizedBox(
      width: width,
      height: height ?? _buttonHeight,
      child: ElevatedButton(
        onPressed: effectiveOnPressed,
        style: buttonStyle,
        child: Row(
          mainAxisSize: MainAxisSize.min,
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            if (leadingIcon != null && !isLoading) ...[
              leadingIcon!,
              const SizedBox(width: AppDimensions.spacingS),
            ],
            if (isLoading) ...[
              SizedBox(
                width: AppDimensions.iconS,
                height: AppDimensions.iconS,
                child: CircularProgressIndicator(
                  strokeWidth: 2,
                  valueColor: AlwaysStoppedAnimation<Color>(
                    buttonStyle.foregroundColor?.resolve({}) ?? Colors.white,
                  ),
                ),
              ),
              const SizedBox(width: AppDimensions.spacingS),
            ],
            Text(
              text,
              style: _getTextStyle(context),
            ),
            if (trailingIcon != null && !isLoading) ...[
              const SizedBox(width: AppDimensions.spacingS),
              trailingIcon!,
            ],
          ],
        ),
      ),
    );
  }
}