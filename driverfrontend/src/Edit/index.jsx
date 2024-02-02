
    import React, { useState, useEffect } from "react";
    import { InboxOutlined, UploadOutlined } from "@ant-design/icons";
    import {signUpUser, resetRegistrationState, editUser} from "../redux/reducers/userSlice"
    import {
    Button, Checkbox, Form, Input, Select, message, Tag,

    Switch,
    TreeSelect, Typography,
} from "antd";
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
            }
        }, [user]);

        useEffect(() => {
            if (user) {
                form.setFieldsValue({
                    firstName: user.firstName,
                    lastName: user.lastName,
                    phone:user.phone,
                    currentLocationName:user.currentLocationName,

                    // Populate other fields based on your user data
                });
            }
        }, [user, form]);
        const carTypes = [{id:1,description:"Sedan"},{id:2,description:"SUV"},{id:3,description:"Van"},{id:4,description:"Other"}
        ]
        useEffect(()=>{


            dispatch(fetchLocations());
            console.log("REGISTER USER "+JSON.stringify(success))
        },[])

        const onSubmit = (values) => {
            const data = { ...values };
            dispatch(editUser(data));


        };
        // useEffect(() => {
        //     if (success) {
        //         console.log('SUCCESS REGISTERED');
        //         dispatch(resetRegistrationState());
        //         history.push('/log_in');
        //
        //     }
        // }, [success, history]); // Include 'success' and 'history' in the dependency array


        const {locations} = useSelector((state)=>state.locationReducer);




        const handleUpload = (v) => {
            setImg(v.response[0]);
        };

        return (
            <section className="signup">
                <div style={{ marginRight: 120 }} className="container mt-5">
                    <div className="row">
                        <div className="col-md-8 offset-md-3 card p-5">
                            <Typography.Title level={3} style={{textAlign:"center" ,margin:"0px 0px 20px 0px"}}> Edit User Details</Typography.Title>
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
                                    name="currentLocationName"
                                    rules={[
                                        {
                                            type: "text",
                                            message: "Please enter a valid location ID",
                                        },
                                    ]}
                                >
                                    <Select placeholder="Select current location">
                                        {locations.map((location) => (
                                            <Option key={location.name} value={location.name}>
                                                {location.description}
                                            </Option>
                                        ))}
                                    </Select>
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
                                    <Tag color="#2db7f5">Back to Home </Tag>
                                </Link>
                            </Form>
                        </div>
                    </div>
                </div>
            </section>
        );
    };
    export default Signup;
