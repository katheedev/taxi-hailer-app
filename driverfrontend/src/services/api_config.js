
import axios from "axios";
import { LOCAL_STORAGE_KEY_TOKEN, LOCAL_STORAGE_KEY_USER } from "../const/const";



let api_url = process.env.REACT_APP_DEV_API_URL;

if (process.env.NODE_ENV === "production") {
  api_url = process.env.REACT_APP_PROD_API_URL;
}

const axiosConfig = {
  baseURL: api_url,
  headers: { "Access-Control-Allow-Origin": "*" },
  timeout: 10000,
};

// Check if the token exists in localStorage
const token = localStorage.getItem(LOCAL_STORAGE_KEY_TOKEN);

// Set the Authorization header if the token exists
if (token) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
} else {
  // Clear the Authorization header if there is no token
  delete axios.defaults.headers.common['Authorization'];
  // You can also set it to an empty string if needed
  // axios.defaults.headers.common['Authorization'] = '';
}

const instance = axios.create(axiosConfig);

// Add a response interceptor
instance.interceptors.response.use(
  function (response) {
    return response;
  },
  function (error) {
    if (error.response && error.response.status === 401) {
      window.localStorage.removeItem(LOCAL_STORAGE_KEY_TOKEN);
      window.localStorage.removeItem(LOCAL_STORAGE_KEY_USER);
     // window.location.reload();
    }
    return Promise.reject(error);
  },
);

// instance.CancelToken = axios.CancelToken;
// instance.isCancel = axios.isCancel;

const responseBody = (response) => response.data;

export async function get(url,) {
  return await instance.get(url).then(responseBody);
}

async function post(url, data) {
  return instance.post(url, { ...data }).then(responseBody);
}

async function put(url, data) {
  return instance.put(url, { ...data }).then(responseBody);
}

async function del(url) {
  return await instance.delete(url).then(responseBody);
}

const api_request = { get, post, put, del };

export default api_request;
