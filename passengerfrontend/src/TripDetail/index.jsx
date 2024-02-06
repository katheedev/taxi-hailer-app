import {useDispatch, useSelector} from "react-redux";
import {useHistory} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {fetchTripDetails, resetTripDetails} from "../redux/reducers/tripDetailSlice";
import {resetRequestState } from "../redux/reducers/tripRequestSlice";
import { Table, Modal,Spin} from "antd";
import './tripdetail.css';

const TripDetail = () => {

    const dispatch = useDispatch();
    const history = useHistory();
    const [isModalOpen, setIsModalOpen] = useState([true, true]);

    const {
        current_trip,error, success
    } = useSelector((state) => state.tripDetailReducer);

    const [loading, setLoading] = useState(true);


    useEffect(() => {
        // Redirect to trip_request page on error
        if (error) {
            console.log("Error:",JSON.stringify(error) );
            //dispatch(resetRequestState())
            dispatch(resetTripDetails());
            history.push("/trip_request");
        }
    }, [error, history]);

    useEffect(()=>{

        if(success && current_trip.id!=null){
            setLoading(false);
        }
    },[current_trip])


    useEffect(() => {
        const fetchTripDetailsData = async () => {
            try {
                await dispatch(fetchTripDetails());
            } catch (error) {
                console.error("Error fetching trip details:", error);
            }
        };

        const fetchTripDetailInterval = setInterval(() => {
            fetchTripDetailsData();
        }, 5000);

        //fetchTripDetailsData(); // Fetch data immediately when the component mounts

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
            dataIndex: "totalFare",
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
            <Modal
                className="my-modal"
                title="WAITING FOR DRIVER"
                open={loading}
                style={{textAlign:"center"}}
                cancelText=""
                footer={null}
                closable={false}
            >
                <Spin  size="large">    </Spin>
            </Modal>
        </div>
    );
};


export default TripDetail;