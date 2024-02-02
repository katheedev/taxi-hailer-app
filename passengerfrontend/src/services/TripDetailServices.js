import api_request from "./api_config";

class TripDetailServices {
    getTripDetails() {
        return api_request.get("/api/v1/trip/getcurrentrip");
    }
}

export default new TripDetailServices();