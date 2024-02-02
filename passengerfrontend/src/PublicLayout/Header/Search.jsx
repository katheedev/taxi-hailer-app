import React, { useEffect } from "react";
// import logo from "../../components/assets/images/logo.svg";
import { Link } from "react-router-dom";
import { useDispatch } from "react-redux";
import { getProducts } from "../../../../redux/reducers/productSlice";
import { useLocation } from "react-router-dom";

const Search = () => {
  const dispatch = useDispatch();

  let location = useLocation();
  window.addEventListener("scroll", function () {
    const search = document.querySelector(".search");
    search.classList.toggle("active", window.scrollY > 100);
  });

  const handelSearch = (e) => {
    setTimeout(() => {
      dispatch(
        getProducts({
          sold: false,
          pd_uploaded_by: location?.pathname.includes("/home") ? "seller" : "buyer",
          searchQuery: e.target.value,
        }),
      );
    }, 500);
  };

  return (
    <>
      <section className="conatiner py-2 search">
        <div className="row d-flex justify-content-center">
          <div className="col-md-6">
            <input onChange={handelSearch} type="text" className="form-control" placeholder="what are you looking " />
          </div>
        </div>
      </section>
    </>
  );
};

export default Search;
