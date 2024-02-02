
import api_request from "./api_config.js";

class LocationServices {
  getProducts(page = 1, searchQuery = "", district = "", pd_uploaded_by = 'admin', sold = false) {
    return api_request.get(`/product?page=${page}&searchQuery=${searchQuery}&district=${district}&pd_uploaded_by=${pd_uploaded_by}&sold=${sold}`);
  }
  getLocations() {
    return api_request.get("/api/public/location");
  }

  locationChange(data) {
    return api_request.post(`/api/user/location?name=${data}`, data);
  }

}

export default new LocationServices();