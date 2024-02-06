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

      </Nav>
    </>
  );
};

export default Navbar;
