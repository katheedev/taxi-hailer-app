import React, { useEffect, useState } from "react";
import { Button, Checkbox, Form, Input, Tag } from "antd";
import { useDispatch, useSelector } from "react-redux";
import { userLogIn } from "../redux/reducers/userSlice";
import { LOCAL_STORAGE_KEY_TOKEN, LOCAL_STORAGE_KEY_USER } from "../const/const";
import { useLocation, Link } from "react-router-dom";
import { useHistory } from "react-router-dom";
import "./login.css";

const LogIn = () => {
  const dispatch = useDispatch();
  const location = useLocation();
  const history = useHistory();

  const { loading, success, error, user, token } = useSelector((state) => state.userReducer.getUser);
  const onFinish = (values) => {
    console.log("VALUES "+JSON.stringify(values));
    const data = { email: values.email, password: values.password };
    dispatch(userLogIn(data));
  };

  useEffect(() => {
    if (user) {
      localStorage.setItem(LOCAL_STORAGE_KEY_USER, JSON.stringify(user));
      localStorage.setItem(LOCAL_STORAGE_KEY_TOKEN, token);
      console.log("USER "+JSON.stringify(user));
      console.log("TOKEN"+JSON.stringify(token));
      history.push("/home");
    }
  }, [user]);

  return (
    <>
      <div class="container  mt-5 ">
        <div class="row ">
          <div class="col-md-6 offset-md-3 card  p-5">
            <Form
              name="basic"
              labelCol={{
                span: 8,
              }}
              wrapperCol={{
                span: 16,
              }}
              initialValues={{
                remember: true,
              }}
              onFinish={onFinish}
              autoComplete="off"
            >
              <Form.Item
                label="Email"
                name="email"
                rules={[
                  {
                    type: "email",
                    required: true,
                    message: "Please input your valid email!",
                  },
                ]}
              >
                <Input />
              </Form.Item>

              <Form.Item
                label="Password"
                name="password"
                rules={[
                  {
                    required: true,
                    message: "Please input your password!",
                  },
                ]}
              >
                <Input.Password />
               
              </Form.Item>
              <Form.Item
                wrapperCol={{
                  offset: 8,
                  span: 16,
                }}
              >
                <Button loading={loading} disabled={loading} type="primary" htmlType="submit">
                  Submit
                </Button>
              </Form.Item>
              <Link style={{ paddingLeft:280,paddingTop: 20}} to="sign_up">
                <Tag color="#2db7f5">Go to Sign Up </Tag>
              </Link>
            </Form>
          </div>
        </div>
      </div>
    </>
  );
};
export default LogIn;
