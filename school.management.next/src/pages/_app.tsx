import Loading from "@/components/Loading";
import "@/styles/globals.css";
import { HeroUIProvider, ToastProvider } from "@heroui/react";
import type { AppProps } from "next/app";
import React, { useEffect } from "react";
import { Provider, useDispatch, useSelector } from "react-redux";
import { store } from "../store/store";
import { ThemeSwitcher } from "./../components/ThemeSwitcher";
import Navbar_Guest from "@/components/Navbar-Guest";
import Navbar_Staff from "@/components/Navbar-Staff";
import Navbar_Parent from "@/components/Navbar-Parent";
import { rehydrate } from "@/store/slices/authSlice";
import Head from "next/head";

function AppContent({
  Component,
  pageProps,
  darkMode,
  incrementLoading,
  decrementLoading,
  saveDarkMode,
  loading,
}: any) {
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

  let navbar = (
    <Navbar_Guest
      incrementLoading={incrementLoading}
      decrementLoading={decrementLoading}
    />
  );
  if (token && user) {
    if (user.roles.includes("PARENT")) {
      navbar = <Navbar_Parent />;
    } else {
      navbar = <Navbar_Staff />;
    }
  }

  return (
    <main
      className={`text-foreground bg-background h-full w-full relative flex flex-col`}
    >
      <Head>
        <title>TechTrendz - School Management</title>
      </Head>
      <ThemeSwitcher saveDarkMode={saveDarkMode} darkMode={darkMode} />
      {navbar}
      <Loading isOpen={loading} />
      <ToastProvider
        toastProps={{
          radius: "full",
          color: "primary",
          variant: "flat",
          timeout: 1000,
          hideIcon: true,
          classNames: {
            closeButton:
              "opacity-100 absolute right-4 top-1/2 -translate-y-1/2",
          },
        }}
      />
      <Component
        {...pageProps}
        incrementLoading={incrementLoading}
        decrementLoading={decrementLoading}
      />
    </main>
  );
}

export default function App({ Component, pageProps }: AppProps) {
  const [darkMode, setDarkMode] = React.useState(false);
  const [loading, setLoading] = React.useState(0);

  const incrementLoading = () => {
    setLoading((prev) => prev + 1);
  };

  const decrementLoading = () => {
    setLoading((prev) => prev - 1);
  };

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

  React.useEffect(() => {
    if (darkMode) {
      document.body.classList.add("dark");
    } else {
      document.body.classList.remove("dark");
    }
  }, [darkMode]);

  return (
    <HeroUIProvider className={`h-dvh w-dvw overflow-hidden `}>
      <Provider store={store}>
        <AppContent
          Component={Component}
          pageProps={pageProps}
          darkMode={darkMode}
          incrementLoading={incrementLoading}
          decrementLoading={decrementLoading}
          saveDarkMode={saveDarkMode}
          loading={loading !== 0}
        />
      </Provider>
    </HeroUIProvider>
  );
}
