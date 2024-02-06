import api_request from "./api_config";

class AuthServices {
  logInUser(data) {
    return api_request.post("/api/auth/login", data);
  }
  registerUser(data) {
    return api_request.post("/api/v1/passenger/registration", data);

  }

  editUser(data){
    return api_request.post("/api/v1/passenger/edit", data);
  }

}

export default new AuthServices();