import {useDispatch, useSelector} from "react-redux";
import {useHistory} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {fetchTripDetails} from "../redux/reducers/tripDetailSlice";
import {resetRequestState } from "../redux/reducers/tripRequestSlice";
import { Table} from "antd";



const TripDetail = () => {

    const dispatch = useDispatch();
    const history = useHistory();

    const {
        current_trip,error, success
    } = useSelector((state) => state.tripDetailReducer);

    const [loading, setLoading] = useState(true);


    useEffect(() => {
        // Redirect to trip_request page on error
        if (error) {
            dispatch(resetRequestState())
            history.push("/trip_request");
        }
    }, [error, history]);



    useEffect(() => {

        const fetchTripDetailInterval = setInterval(() => {
            dispatch(fetchTripDetails());        }, 5000);

        return () => clearInterval(fetchTripDetailInterval);
    }, [dispatch, history]);



    const columns = [
        {
            title: "Driver ID",
            dataIndex: "driverId",
            key: "driverId",
        },
        {
            title: "Pickup Location",
            dataIndex: ["pickUpLocationName","description"],
            key: "pickUpLocationName",
        },
        {
            title: "Destination Location",
            dataIndex: ["destination", "description"],
            key: "destination",
        },
        {
            title: "Fair",
            dataIndex: "totalFair",
            key: "totalFare",
        },
        {
            title: "Start Time",
            dataIndex: "startTime",
            key: "startTime",
        },
        {
            title: "End Time",
            dataIndex: "endTime",
            key: "endTime",
        },

    ];

    return (
        <div>
            <h1>Current Trip Detail</h1>
            <Table
                dataSource={loading ? [] : [current_trip]}
                columns={columns}
                bordered
            />

        </div>
    );
};


export default TripDetail;