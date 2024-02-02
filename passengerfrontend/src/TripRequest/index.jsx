
import {useDispatch, useSelector} from "react-redux";
import {fetchLocations} from "../redux/reducers/locationSlice";
import React, {useEffect} from "react";
import {useHistory} from "react-router-dom";
import {Button, Form, Select} from "antd";
import {createTripRequest} from "../redux/reducers/tripRequestSlice";

const { Option } = Select;

const TripRequest = () => {

    const dispatch = useDispatch();
    const history = useHistory();
    const [form] = Form.useForm();

    const {
        request,success
    } = useSelector((state) => state.tripRequestReducer.getRequest);
    console.log("success:", useSelector((state) => state.tripRequestReducer.getRequest));

    const {locations} = useSelector((state)=>state.locationReducer);


    useEffect(()=>{

        dispatch(fetchLocations());
        // console.log("LOGIN USER "+JSON.stringify(user))
        console.log("LOCATIONS"+JSON.stringify(locations));

    },[]);


    console.log("success:", success);
    useEffect(() => {
       // console.log("request:", request);
        //console.log("success:", success);
        if (request && success) {
            console.log("Request:",JSON.stringify(request) );
           history.push("/trip_detail");
        }
    }, [ success]);

    const onFinish = (values) => {
        const data = { ...values };
        dispatch(createTripRequest(data));
        //console.log("LOCATIONS"+JSON.stringify(request));
    };

    return (
        <div>
            <h1>Trip Request</h1>
            <Form form={form} onFinish={onFinish} autoComplete="off" layout="vertical">
                <Form.Item
                    name="pickUpLocationId"
                    label="Pickup Location"
                    rules={[{ required: true, message: "Please select pickup location" }]}
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
                        { required: true, message: "Please select destination location" },
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
                    <Button type="primary" htmlType="submit">
                        Create Trip Request
                    </Button>
                </Form.Item>
            </Form>
        </div>
    );


};

export default TripRequest;