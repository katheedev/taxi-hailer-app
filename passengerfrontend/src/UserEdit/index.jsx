import React, { useEffect } from "react";
import {editUser} from "../redux/reducers/userSlice"
import {Button, Form, Input, Select, Space} from "antd";
import { useDispatch, useSelector } from "react-redux";
import {Link, useHistory} from "react-router-dom";
//import { LOCAL_STORAGE_KEY_TOKEN, LOCAL_STORAGE_KEY_USER  } from "../const/const";


const { Option } = Select;

const UserEdit = () => {

    const dispatch = useDispatch();
    const history = useHistory();

    const [form] = Form.useForm();
    const {

        getUser: { error, user, token },
    } = useSelector((state) => state.userReducer);


    // useEffect(() => {
    //     if (user) {
    //         localStorage.setItem(LOCAL_STORAGE_KEY_USER, JSON.stringify(user));
    //         localStorage.setItem(LOCAL_STORAGE_KEY_TOKEN, token);
    //     }
    // }, [user, history]);


    useEffect(() => {
        if (user) {
            form.setFieldsValue({
                first_name: user.firstName,
                last_name: user.lastName,
            });
        }
    }, [user, form]);

    const onSubmit = (values) => {

        const data = { ...values };
        dispatch(editUser(data));

    };


    return (
        <section className="user-edit">
        <div style={{ marginRight: 120 }} className="container mt-5">
            <div className="row">
                <div className="col-md-8 offset-md-3 card p-5">
            <h2>Edit User Details</h2>
            <Form form={form} onFinish={onSubmit} layout="vertical">
                <Form.Item
                    label="First Name"
                    name="first_name"
                    rules={[{required: true, message: "Please enter your first name"}]}
                >
                    <Input/>
                </Form.Item>
                <Form.Item
                    label="Last Name"
                    name="last_name"
                    rules={[{required: true, message: "Please enter your last name"}]}
                >
                    <Input/>
                </Form.Item>
                <Form.Item>
                    <Button type="primary" htmlType="submit" >
                        Save Changes
                    </Button>
                </Form.Item>
            </Form>
        </div>
            </div>
        </div>
        </section>
    );
};
export default UserEdit;
