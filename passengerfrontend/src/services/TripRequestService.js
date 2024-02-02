import api_request from "./api_config";

class TripRequestService{

    tripRequest(data) {
        return api_request.post("/api/v1/trip/create", data);
    }
}

export default new TripRequestService();