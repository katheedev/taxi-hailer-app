import React, { useEffect } from "react";
import {resetRegistrationState, signUpUser} from "../redux/reducers/userSlice"
import { Button, Form, Input, Select} from "antd";
import { useDispatch, useSelector } from "react-redux";
import { useHistory } from "react-router-dom";
import { LOCAL_STORAGE_KEY_TOKEN, LOCAL_STORAGE_KEY_USER  } from "../const/const";
import './signup.css';

import { Link } from "react-router-dom";
const { Option } = Select;

let api_url = process.env.REACT_APP_DEV_API_URL;
if (process.env.NODE_ENV === "production") {
  api_url = process.env.REACT_APP_PROD_API_URL;
}
const Signup = () => {

  const dispatch = useDispatch();
  const history = useHistory();
  //const [img, setImg] = useState("");

  const [form] = Form.useForm();
  const {
      registerUser:{loading,success},
    getUser: { error, user, token },
  } = useSelector((state) => state.userReducer);


    useEffect(() => {
        if (user) {
            localStorage.setItem(LOCAL_STORAGE_KEY_USER, JSON.stringify(user));
            localStorage.setItem(LOCAL_STORAGE_KEY_TOKEN, token);
            history.push("/home");
        }
    }, [user, history]);


    const onSubmit = (values) => {

        const data = { ...values };
        dispatch(signUpUser(data));

    };

    useEffect(() => {
        if (success) {
            console.log('SUCCESS REGISTERED');
            dispatch(resetRegistrationState());
            history.push('/log_in');

        }
    }, [success, history]);


  return (
      <section className="signup">
        <div style={{ marginRight: 120 }} className="container mt-5">
          <div className="row">
            <div className="col-md-8 offset-md-3 card p-5">
              <Form
                  form={form}
                  name="userRegister"
                  labelCol={{ span: 8 }}
                  wrapperCol={{ span: 16 }}
                  initialValues={{ remember: true }}
                  autoComplete="on"
                  onFinish={onSubmit}
              >
                {/* First Name */}
                <Form.Item
                    label="First Name"
                    name="first_name"
                    rules={[
                      {
                        required: true,
                        message: "Please input your first name",
                      },
                    ]}
                >
                  <Input />
                </Form.Item>

                {/* Last Name */}
                <Form.Item
                    label="Last Name"
                    name="last_name"
                    rules={[
                      {
                        required: true,
                        message: "Please input your last name",
                      },
                    ]}
                >
                  <Input />
                </Form.Item>

                {/* Password */}
                <Form.Item
                    label="Password"
                    name="password"
                    rules={[
                      {
                        required: true,
                        message: "Please input your password",
                      },
                    ]}
                >
                  <Input.Password />
                </Form.Item>

                {/* Email */}
                <Form.Item
                    label="Email"
                    name="email"
                    rules={[
                      {
                        type: "email",
                        required: true,
                        message: "Please input your valid email",
                      },
                    ]}
                >
                  <Input />
                </Form.Item>

                {/* Submit Button */}
                <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
                  <Button
                      loading={loading}
                      disabled={loading}
                      type="primary"
                      htmlType="submit"
                  >
                    Submit
                  </Button>
                </Form.Item>

                {/* Back to Log In Link */}
                <Link to="log_in">
                    <div style={{color:"red"}}>If you already have an account Go to Login </div>
                </Link>
              </Form>
            </div>
          </div>
        </div>
      </section>
  );
};
export default Signup;
