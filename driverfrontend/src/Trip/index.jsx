import React, {useEffect, useState} from "react";
import { useDispatch, useSelector } from "react-redux";
import { useHistory } from "react-router-dom";
import { startTrip, endTrip, completeTrip ,resetTrip} from "../redux/reducers/tripSlice";
import {Button, Descriptions, Space, Typography} from "antd";
import "./trip.css"; // Create a CSS file for styling
import moment from "moment";
import Lottie from "lottie-react";
import mercedesAnim from "./mercedes.json"
import startTripAnim from "./startTrip.json"
import waitingAnim from "./waitingAnim.json"
import endTripAnim from "./endTripAnim.json"
const Trip = () => {
    const dispatch = useDispatch();
    const history = useHistory();
    const { loading, trip, error, success } = useSelector((state) => state.tripReducer.acceptedTrip);

const [tripState,setTripState]=useState("waiting");
const [transition,setTransition]=useState(false);
    useEffect(() => {
        // Redirect to home if there is no active trip or if the trip is completed
        if (!trip) {
            history.push("/home");
        }
    }, [trip, history]);

    const handleStartTrip = () => {
        setTripState("started")
        dispatch(startTrip({ id: trip.tripId }));
    };

    const handleEndTrip = () => {
        setTripState("ended")
        dispatch(endTrip({ id: trip.tripId }));
    };

    const handleCompleteTrip = () => {
        setTripState("completed");
        dispatch(completeTrip({ id: trip.tripId }));
     //   history.push("/home"); // Redirect to home after completing the trip
    };
    const handleRequestAnotherTrip=()=>{
     dispatch(resetTrip());
     history.push("/home"); // Redirect to home after completing the trip
    };
    const handleTransition=()=>{
      setTransition(true)
    };


    return (
        <section className="trip">
            <div className="container mt-5">
                <div className="row">
                    <div className="col-md-9 offset-md-1 card p-5">
                        <Typography.Title  level={2} style={{ display: 'inline', textAlign: 'center' }}>Trip Details
                        </Typography.Title>
                        <Space id="lottie" >
                            {
                            tripState ==="waiting" ?
                            ( <Lottie className="trans" animationData={waitingAnim}/>)
                            : tripState==="started" ?
                            (<>
                                <Lottie className="trans" animationData={transition ? startTripAnim :startTripAnim} loop={true} onComplete={handleTransition} />
                                {/*{transition ? <Lottie animationData={startTripAnim} on /> :<></>}*/}
                            </> )
                            :tripState==="ended" ?
                            <Lottie className="trans" animationData={endTripAnim }/>
                            :   ( <Lottie animationData={waitingAnim}/>)

                        }

                        </Space>


                        <Typography.Title level={5}  style={ {color:"rgb(14 27 72)" ,textAlign: 'center',margin:'0px 0px 20px 0px' }}>
                            {tripState === "waiting" ? "Passenger is waiting | Go to Pickup location" : tripState === "started" ? "Trip started | Go to Destination" : tripState === "ended" ? "Destination reached | Collect Cash" : tripState === "completed" ? "Trip is completed" : ""}
                        </Typography.Title>
                        <Descriptions bordered column={{ xs: 1, sm: 2, md: 3, lg: 3, xl: 4, xxl: 4 }}>
                            <Descriptions.Item label="Passenger Name" span={3}>
                                {trip.passengerName}
                            </Descriptions.Item>
                            <Descriptions.Item label="Pickup Location" span={3}>
                                {trip.pickUpLocation.description}
                            </Descriptions.Item>
                            <Descriptions.Item label="Destination" span={3}>
                                {trip.destination.description}
                            </Descriptions.Item>
                            <Descriptions.Item label="Total Fare" span={3}>
                                {trip.totalFare}
                            </Descriptions.Item>
                            <Descriptions.Item label="Accepted Time" span={3}>
                                {moment(trip.acceptedTime).format('DD-MM YYYY, HH:mm:ss ')}
                            </Descriptions.Item>
                            {trip.startTime && (
                                <Descriptions.Item label="Start Time" span={{ xl: 2, xxl: 2 }}>
                                    {moment(trip.startTime).format('DD-MM YYYY, HH:mm:ss ')}
                                </Descriptions.Item>
                            )}
                            {trip.endTime && (
                                <Descriptions.Item label="End Time" span={{ xl: 2, xxl: 2 }}>
                                    {moment(trip.endTime).format('DD-MM YYYY, HH:mm:ss ')}
                                </Descriptions.Item>
                            )}
                            {trip.paidTime && (
                                <Descriptions.Item label="Paid Time" span={{ xl: 2, xxl: 2 }}>
                                    {moment(trip.paidTime).format('DD-MM YYYY, HH:mm:ss ')}
                                </Descriptions.Item>
                            )}
                        </Descriptions>

                        <div className="trip-actions">
                            <Button type="primary" onClick={handleStartTrip} disabled={loading || trip.status !== 0}>
                                Start Trip
                            </Button>
                            <Button type="danger" onClick={handleEndTrip} disabled={loading || trip.status !== 1}>
                                End Trip
                            </Button>
                            <Button type="success" onClick={handleCompleteTrip} disabled={loading || trip.status !== 2}>
                                Complete Trip
                            </Button>
                            <Button type="success" onClick={handleRequestAnotherTrip} >
                                Request Another Trip
                            </Button>

                        </div>
                    </div>
                </div>
            </div>
        </section>
    );
};

export default Trip;
