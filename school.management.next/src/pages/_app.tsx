import Loading from "@/components/Loading";
import "@/styles/globals.css";
import { HeroUIProvider } from "@heroui/react";
import type { AppProps } from "next/app";
import React, { useEffect } from "react";
import { Provider, useDispatch, useSelector } from "react-redux";
import { store } from "../store/store";
import { ThemeSwitcher } from "./../components/ThemeSwitcher";
import Navbar_Guest from "@/components/Navbar-Guest";
import Navbar_Staff from "@/components/Navbar-Staff";
import Navbar_Parent from "@/components/Navbar-Parent";
import { rehydrate } from "@/store/slices/authSlice";

// This component is rendered inside the Provider
function AppContent({ Component, pageProps, darkMode, setLoading, saveDarkMode, loading }: any) {
  const { token, user } = useSelector((state: any) => state.auth);
  const dispatch = useDispatch();

  useEffect(() => {
    const auth = localStorage.getItem("auth");
    if (auth) {
      try {
        const parsed = JSON.parse(auth);
        if (parsed.token && parsed.user) {
          dispatch(rehydrate(parsed));
        }
      } catch {}
    }
  }, [dispatch]);

  let navbar = <Navbar_Guest darkMode={darkMode} setLoading={setLoading} />;
  if (token && user) {
    if (user.roles.includes("PARENT")) {
      navbar = <Navbar_Parent darkMode={darkMode} setLoading={setLoading} />;
    } else {
      navbar = <Navbar_Staff darkMode={darkMode} setLoading={setLoading} />;
    }
  }

  return (
    <main
      className={`text-foreground bg-background h-dvh w-dvw relative flex flex-col ${
        darkMode ? "dark" : ""
      }`}
    >
      <ThemeSwitcher saveDarkMode={saveDarkMode} darkMode={darkMode} />
      {navbar}
      <Loading isOpen={loading} darkMode={darkMode} />
      <Component {...pageProps} setLoading={setLoading} />
    </main>
  );
}

export default function App({ Component, pageProps }: AppProps) {
  const [darkMode, setDarkMode] = React.useState(false);
  const [loading, setLoading] = React.useState(false);

  const saveDarkMode = (darkMode: boolean) => {
    setDarkMode(darkMode);
    window.localStorage.setItem("darkMode", darkMode.toString());
  };

  React.useEffect(() => {
    const darkMode = window.localStorage.getItem("darkMode");
    if (darkMode) {
      setDarkMode(darkMode === "true");
    } else {
      saveDarkMode(false);
    }
  }, []);

  return (
    <HeroUIProvider className="h-dvh w-dvw overflow-hidden">
      <Provider store={store}>
        <AppContent
          Component={Component}
          pageProps={pageProps}
          darkMode={darkMode}
          setLoading={setLoading}
          saveDarkMode={saveDarkMode}
          loading={loading}
        />
      </Provider>
    </HeroUIProvider>
  );
}
