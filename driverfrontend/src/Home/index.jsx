import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useHistory } from "react-router-dom";
import { fetchLocations, locationChange } from "../redux/reducers/locationSlice";
import {  fetchTripRequests, acceptTrip ,rejectTrip} from "../redux/reducers/tripSlice";
import { Button, Radio, Select, Tag, Table, Space,Spin } from "antd";
import { Typography } from 'antd';
import './home.css';
import {statusChange} from "../redux/reducers/tripSlice";

const { Option } = Select;

const {Title } = Typography
const optionsWithDisabled = [
    { label: 'Online', value: 'online' },
    { label: 'Offline', value: 'offline' }
   // { label: 'Orange', value: 'Orange', disabled: true },
];


const Home = () => {
    const dispatch = useDispatch();
    const history = useHistory();

    const { user, token } = useSelector((state) => state.userReducer.getUser);




    const { tripRequests } = useSelector((state) => state.tripReducer);
    const {loading,trip,error,success } = useSelector((state) => state.tripReducer.acceptedTrip);

    const [availability, setAvailability] = useState("online");
    const [selectedLocation, setSelectedLocation] = useState(user!=null ? user.currentLocationName:"");



    useEffect(() => {
        console.log("Dispatch Fetch locations");
        dispatch(fetchLocations());
    },  [dispatch]);
    const { locations } = useSelector((state) => state.locationReducer);


    useEffect(() => {
        if(availability==="online") {
            const fetchTripsInterval = setInterval(() => {
                if (availability === "online") {
                    console.log("AVALI ONLINE")
                    dispatch(fetchTripRequests());
                }
            }, 1000);
            // Clean up the interval when the component unmounts
            return () => clearInterval(fetchTripsInterval);
        }
    }, [dispatch,availability]);

    useEffect(() => {
        if(success && trip){
            history.push("/trip");
        }
    }, [success,trip]);

    useEffect(()=>{
        console.log("AVAILABILITY IN USE EFFECT " + availability)
        dispatch( statusChange(availability));
    },[availability])

    const handleAvailabilityChange = (e) => {
        setAvailability(e.target.value);

    };

    const handleLocationChange = (value) => {
        setSelectedLocation(value);
        dispatch(locationChange(value))
    };

    const columns = [
        {
            title: "Trip ID",
            dataIndex: "id",
            key: "id",
        },
        {
            title: "Passenger Name",
            dataIndex: "passengerName",
            key: "passengerName",
        },
        {
            title: "Destination",
            dataIndex: "destinationName",
            key: "destinationName",
        },
        {
            title: "Actions",
            key: "actions",
            render: (text, record) => (
                <Space size="middle">
                    <Button type="primary" onClick={() => handleAcceptTrip(record)}>
                        Accept
                    </Button>
                    <Button type="danger" onClick={() => handleRejectTrip(record)}>
                        Reject
                    </Button>
                </Space>
            ),
        },
    ];

    const handleAcceptTrip = (trip) => {

        dispatch(acceptTrip({ id: trip.id }));

    };

    const handleRejectTrip = (trip) => {
        dispatch(rejectTrip({ id: trip.id }));
    };
    if(user==null){
        history.push("/log_in");
        return null;
    }

    return (
        <section className="home">
            <div className="container mt-5">
                <div className="row">
                    <div className="col-md-8 offset-md-2 card p-5">
                        <div>
                            <Title level={2}>Welcome, {user && user.firstName}!</Title>
                            <div className="availability">
                                <Title level={5}>Set Availability:</Title>
                                <Radio.Group

                                    defaultValue={availability}
                                    options={optionsWithDisabled}
                                    onChange={handleAvailabilityChange}
                                    value={availability}
                                    optionType="button"
                                    buttonStyle="solid"
                                />
                            </div>
                            <div className="currentLocation">
                                <Title level={5}>Select Current Location:</Title>
                                <Select

                                    placeholder="Select current location"
                                    onChange={handleLocationChange}
                                    value={selectedLocation}
                                    defaultValue={selectedLocation}
                                >
                                    {locations.map((location) => (
                                        <Option key={location.name} value={location.name}>
                                            {location.description}
                                        </Option>
                                    ))}
                                </Select>
                            </div>
                        </div>
                        <div className="tripRequests">
                             <Title level={4}>Trip Requests</Title>
                            { availability==="online"? (
                                <Typography.Text> fetching...        <Spin /></Typography.Text>
                                )
                                :<></>
                            }

                        </div>
                        <Table dataSource={tripRequests.list} columns={columns} />
                    </div>
                </div>
            </div>
        </section>
    );
};

export default Home;
