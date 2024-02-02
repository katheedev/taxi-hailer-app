import React, { useState, useEffect } from "react";
import { InboxOutlined, UploadOutlined } from "@ant-design/icons";
import { signUpUser,resetRegistrationState} from "../redux/reducers/userSlice"
import { Button, Checkbox, Form, Input, Select, message ,Tag,
  
  Switch,
  TreeSelect,} from "antd";
import { useDispatch, useSelector } from "react-redux";
import { useHistory } from "react-router-dom";
import { LOCAL_STORAGE_KEY_TOKEN, LOCAL_STORAGE_KEY_USER  } from "../const/const";
import ImageUpload from "../common/ImageUpload";
import './signup.css';

import { Link } from "react-router-dom";
import {fetchLocations} from "../redux/reducers/locationSlice";
const { Option } = Select;

let api_url = process.env.REACT_APP_DEV_API_URL;
if (process.env.NODE_ENV === "production") {
  api_url = process.env.REACT_APP_PROD_API_URL;
}
const Signup = () => {
  const dispatch = useDispatch();
  const history = useHistory();
  const [img, setImg] = useState("");

  const [form] = Form.useForm();
  const {
    registerUser:{loading,success},
    getUser: {  error, user, token },
  } = useSelector((state) => state.userReducer);

    useEffect(() => {
        if (user) {
            localStorage.setItem(LOCAL_STORAGE_KEY_USER, JSON.stringify(user));
            localStorage.setItem(LOCAL_STORAGE_KEY_TOKEN, token);

            history.push("/home");
        }
    }, [user]);


    const carTypes = [{id:1,description:"Sedan"},{id:2,description:"SUV"},{id:3,description:"Van"},{id:4,description:"Other"}
    ]
    useEffect(()=>{

        dispatch(fetchLocations());
        console.log("REGISTER USER "+JSON.stringify(success))
    },[])

  const onSubmit = (values) => {
    const data = { ...values, img };
    dispatch(signUpUser(data));


  };
    useEffect(() => {
        if (success) {
            console.log('SUCCESS REGISTERED');
            dispatch(resetRegistrationState());
            history.push('/log_in');

        }
    }, [success, history]); // Include 'success' and 'history' in the dependency array


    const {locations} = useSelector((state)=>state.locationReducer);




  const handleUpload = (v) => {
    setImg(v.response[0]);
  };

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
                    name="firstName"
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
                    name="lastName"
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

                {/* Matching Password */}
                <Form.Item
                    label="Confirm Password"
                    name="matchingPassword"
                    dependencies={["password"]}
                    hasFeedback
                    rules={[
                      {
                        required: true,
                        message: "Please confirm your password",
                      },
                      ({ getFieldValue }) => ({
                        validator(_, value) {
                          if (!value || getFieldValue("password") === value) {
                            return Promise.resolve();
                          }
                          return Promise.reject(
                              new Error("The two passwords do not match")
                          );
                        },
                      }),
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

                {/* Phone */}
                <Form.Item
                    label="Phone"
                    name="phone"
                    rules={[
                      {
                        required: true,
                        message: "Please input your phone number",
                      },
                    ]}
                >
                  <Input />
                </Form.Item>

                {/* Current Location ID */}
                  <Form.Item
                      label="Current Location"
                      name="currentLocationId"
                      rules={[
                          {
                              type: "number",
                              min: 1,
                              message: "Please enter a valid location ID",
                          },
                      ]}
                  >
                      <Select placeholder="Select current location">
                          {locations.map((location) => (
                              <Option key={location.id} value={location.id}>
                                  {location.description}
                              </Option>
                          ))}
                      </Select>
                  </Form.Item>

                {/* License Plate Number */}
                <Form.Item
                    label="License Plate Number"
                    name="licPlateNo"
                    rules={[
                      {
                        required: true,
                        message: "Please input your license plate number",
                      },
                    ]}
                >
                  <Input />
                </Form.Item>

                {/* Car Type */}
                <Form.Item
                    label="Car Type"
                    name="carType"
                    rules={[
                      {
                        type: "number",
                        min: 1,
                        message: "Please enter a valid car type",
                      },
                    ]}
                >
                    <Select placeholder="Select Car Type">
                        {carTypes.map((carType) => (
                            <Option key={carType.id} value={carType.id}>
                                {carType.description}
                            </Option>
                        ))}
                    </Select>
                </Form.Item>

                {/* Other Fields... */}

                {/* Car Description */}
                <Form.Item label="Car Description" name="carDescription">
                  <Input.TextArea />
                </Form.Item>

                {/* Longitude */}
                <Form.Item label="Longitude" name="longitude">
                  <Input type="number" step="any" />
                </Form.Item>

                {/* Latitude */}
                <Form.Item label="Latitude" name="latitude">
                  <Input type="number" step="any" />
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
                  <Tag color="#2db7f5">Back to Log In </Tag>
                </Link>
              </Form>
            </div>
          </div>
        </div>
      </section>
  );
};
export default Signup;
