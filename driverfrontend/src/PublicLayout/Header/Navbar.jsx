import React, { useState } from "react";
import { useLocation } from "react-router-dom";
import { Link } from "react-router-dom";
import { Nav, NavItem, NavLink } from "reactstrap";
import { useDispatch, useSelector } from "react-redux";
const Navbar = () => {
  const { loading, success, error, user, token } = useSelector((state) => state.userReducer.getUser);
  const { acceptedTrip  } = useSelector((state) => state.tripReducer);

  let location = useLocation();

  return (
    <>
      <Nav className="d-flex justify-content-center" tabs>
        <NavItem>
          {token && acceptedTrip.trip ? (
          <NavLink active={location.pathname.includes("/home") ? true : false}>
            <Link to="/home"> Home </Link>
          </NavLink>
              ): null}
     
        </NavItem>

        {token && acceptedTrip.trip ? (
          <NavItem active={location.pathname.includes("/trip") ? true : false}>
            <NavLink>
              {" "}
              <Link to="/trip"> On Trip </Link>{" "}
            </NavLink>
          </NavItem>
        ) : null}
      </Nav>
    </>
  );
};

export default Navbar;
