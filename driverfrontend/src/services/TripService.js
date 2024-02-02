
import api_request from "./api_config";

class AuthServices {
  getTripRequests() {
    return api_request.get("/api/trip/getTrips");
  }
  rejectTrip(data) {
    console.log(data.id)
    return api_request.post(`/api/trip/reject?id=${data.id}`, data);
  }
  acceptTrip(data) {
    return api_request.post(`/api/trip/accept?id=${data.id}`, data);
  }
  startTrip(data) {
    return api_request.post(`/api/trip/start`, data);
  }
  endTrip(data) {
    return api_request.post(`/api/trip/end`, data);
  }
  completeTrip(data) {
    return api_request.post(`/api/trip/complete`, data);
  }
  getAllUser() {
    return api_request.get("/users");
  }

}

export default new AuthServices();