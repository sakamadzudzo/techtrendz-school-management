import { IconSunThin } from "./../components/icons/sun-thin";
import { IconMoonThin } from "./../components/icons/moon-thin";

export function ThemeSwitcher({
  saveDarkMode,
  darkMode,
}: {
  saveDarkMode: (darkMode: boolean) => void;
  darkMode: boolean;
}) {
  return (
    <div
      className="absolute right-3 bottom-3 bg-gradient-to-b from-background to-background/50 dark:from-background dark:to-background/50 z-50 border rounded-md overflow-hidden p-1"
      onClick={() => saveDarkMode(!darkMode)}
    >
      {darkMode ? (
        <IconMoonThin className="w-6 h-6" />
      ) : (
        <IconSunThin className="w-6 h-6" />
      )}
    </div>
  );
}
