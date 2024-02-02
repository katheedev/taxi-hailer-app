import React, { useState } from "react";
import { useLocation } from "react-router-dom";
import { Link } from "react-router-dom";
import { Nav, NavItem, NavLink } from "reactstrap";
import { useDispatch, useSelector } from "react-redux";
const Navbar = () => {
  const { loading, success, error, user, token } = useSelector((state) => state.userReducer.getUser);
  let location = useLocation();

  return (
    <>
      <Nav className="d-flex justify-content-center" tabs>
        <NavItem>
          <NavLink active={location.pathname.includes("/home") ? true : false}>
            <Link to="/home"> Home </Link>
          </NavLink>
     
        </NavItem>
        <NavItem>
          {user && (user?.role === "seller" || user?.role === "admin") ? (
            <NavLink active={location.pathname.includes("/buyer_requested_product") ? true : false}>
              {" "}
              <Link to="buyer_requested_product"> Buyer requested products </Link>
            </NavLink>
          ) : null}
        </NavItem>

        {token ? (
          <NavItem active={location.pathname.includes("/deshboard") ? true : false}>
            <NavLink>
              {" "}
              <Link to="/deshboard"> Dashboard </Link>{" "}
            </NavLink>
          </NavItem>
        ) : null}
      </Nav>
    </>
  );
};

export default Navbar;
