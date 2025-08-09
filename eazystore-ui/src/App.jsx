import Header from "./components/Header";
import Footer from "./components/footer/Footer";
import React from "react";
import { Outlet, useNavigation } from "react-router-dom";

function App() {
  const navigation = useNavigation();

  return (
    <>
      <Header />
      {navigation.state === "loading" ? (
        <div className="flex items-center justify-center min-h-[852px]">
          <span className="text-2xl font-semibold text-primary">
            Loading...
          </span>
        </div>
      ) : (
        <main style={{ paddingTop: "80px" }}>
          <Outlet />
        </main>
      )}
      <Footer />
    </>
  );
}

export default App;
