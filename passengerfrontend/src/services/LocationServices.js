
import api_request from "./api_config.js";

class LocationServices {
  getLocations() {
    return api_request.get("/api/v1/locations");
  }

}

export default new LocationServices();