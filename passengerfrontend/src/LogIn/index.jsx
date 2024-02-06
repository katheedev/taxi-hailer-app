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
  let history = useHistory();

  const { loading, success, error, user, token } = useSelector((state) => state.userReducer.getUser);


  useEffect(() => {
    if (user) {
      console.log("USER DETAIL"+ JSON.stringify(user))
      console.log("TOKEN "+token)
      localStorage.setItem(LOCAL_STORAGE_KEY_USER, JSON.stringify(user));
      localStorage.setItem(LOCAL_STORAGE_KEY_TOKEN, token);

        history.push("/trip_request");
    }
  }, [user]);


  const onFinish = (values) => {
    const data = { email: values.email, password: values.password };
    dispatch(userLogIn(data));


  };

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
                <Link style={{ paddingLeft:20,paddingTop: 100}} to="sign_up">
                  <div style={{color: "red"}}>I you haven't account Go to Sign Up</div>
                </Link>
              </Form.Item>
            </Form>
          </div>
        </div>
      </div>
    </>
  );
};
export default LogIn;
