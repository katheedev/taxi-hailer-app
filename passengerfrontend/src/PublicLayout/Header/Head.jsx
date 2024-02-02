import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { InboxOutlined, LogoutOutlined, RetweetOutlined } from "@ant-design/icons";
import { Button, Tag } from "antd";
import { Link, useHistory } from "react-router-dom";
import { logOutUser } from "../../redux/reducers/userSlice";
import { LOCAL_STORAGE_KEY_TOKEN,  LOCAL_STORAGE_KEY_USER } from "../../const/const";
const Head = () => {
  const { loading, success, error, user, token } = useSelector((state) => state.userReducer.getUser);

  let history = useHistory();
  const dispatch = useDispatch();

  const handelLogOut = () => {
    localStorage.removeItem(LOCAL_STORAGE_KEY_USER);
    localStorage.removeItem(LOCAL_STORAGE_KEY_TOKEN);
    dispatch(logOutUser());
    history.push("/log_in");
  };
  return (
    <>
      <section className="head">
        <div className="container ">
          <div className="row">
            <div className="col-md-12 d-flex justify-content-between">
              <div className="">
                <p>{user ? user.email : null}</p>
              </div>
              <div className="">
                {!token ? (
                  <Link to="log_in">
                    {" "}
                    <Tag icon={<RetweetOutlined />} color="#2db7f5">
                      {" "}
                      Log In/Register{" "}
                    </Tag>
                  </Link>
                ) : (
                  <Button onClick={handelLogOut}>
                    {" "}
                    <LogoutOutlined /> LogOut
                  </Button>
                )}
              </div>
            </div>
          </div>
        </div>
      </section>
    </>
  );
};

export default Head;
