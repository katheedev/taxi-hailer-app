
import {useDispatch, useSelector} from "react-redux";
import {fetchLocations} from "../redux/reducers/locationSlice";
import React, {useEffect} from "react";
import {useHistory} from "react-router-dom";
import {Button, Form, Image, Select} from "antd";
import {createTripRequest, resetRequestState} from "../redux/reducers/tripRequestSlice";
import './triprequest.css';

const { Option } = Select;

const TripRequest = () => {

    const dispatch = useDispatch();
    const history = useHistory();
    const [form] = Form.useForm();

    const {
        request,success
    } = useSelector((state) => state.tripRequestReducer.getRequest);

    const {locations} = useSelector((state)=>state.locationReducer);


    useEffect(()=>{
        dispatch(fetchLocations());
    },[]);



    useEffect(() => {
        if (request && success) {
            console.log("Request:",JSON.stringify(request));
            dispatch(resetRequestState());
            history.push("/trip_detail");
        }
    }, [success]);

    const onFinish = (values) => {
        const data = { ...values };
        dispatch(createTripRequest(data));
    };

    return (
        <section className="triprequest">
            <div style={{ marginRight: 120 }} className="container mt-5">
                <div className="row">
                    <div className="col-md-8 offset-md-3 card p-5">

                        <div className="form-container">
                        <h1>Trip Request</h1>
                        <Form form={form} onFinish={onFinish} autoComplete="off" layout="vertical" className="form-container">
                            <Form.Item
                                name="pickUpLocationId"
                                label="Pickup Location"
                                rules={[{required: true, message: "Please select pickup location"}]}
                            >
                                <Select placeholder="Select pickup location">
                                    {locations.map((location) => (
                                        <Option key={location.id} value={location.id}>
                                            {location.description}
                                        </Option>
                                    ))}
                                </Select>
                            </Form.Item>

                            <Form.Item
                                name="destinationId"
                                label="Destination Location"
                                rules={[
                                    {required: true, message: "Please select destination location"},
                                ]}
                            >
                                <Select placeholder="Select destination location">
                                    {locations.map((location) => (
                                        <Option key={location.id} value={location.id}>
                                            {location.description}
                                        </Option>
                                    ))}
                                </Select>
                            </Form.Item>

                            <Form.Item>
                                <Button type="primary" htmlType="submit" style={{backgroundColor: 'lightseagreen'}}>
                                    Create Trip Request
                                </Button>
                            </Form.Item>
                        </Form>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    );


};

export default TripRequest;