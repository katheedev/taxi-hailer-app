import React from "react";
import Footer from "./Footer";
import Header from "./Header";
import "./Header/Header.css";
const PublicLayout = ({ children }) => {
  console.log("public ");

  return (
    <>
      <Header />
      {children}
      <Footer />
    </>
  );
};

export default PublicLayout;
