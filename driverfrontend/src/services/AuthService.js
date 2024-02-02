
import api_request from "./api_config";

class AuthServices {
  logInUser(data) {
    return api_request.post("api/auth/login", data);
  }
  registerUser(data) {
    return api_request.post("/api/user/registration", data);

  }
  editUser(data) {
    return api_request.post("/api/user/edit", data);
  }
  statusChange(data) {
    return api_request.post(`/api/user/status?availability=${data}`, data);
  }

}

export default new AuthServices();