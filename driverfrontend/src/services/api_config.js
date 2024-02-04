
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

const getInstance =()=>{
  const token = localStorage.getItem(LOCAL_STORAGE_KEY_TOKEN);
  const instance = axios.create(axiosConfig);
  if (token) {
    axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
  }
  else {
    delete axios.defaults.headers.common['Authorization'];
  }
  return instance;
}



// Add a response interceptor
getInstance().interceptors.response.use(
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
  return await getInstance().get(url).then(responseBody);
}

async function post(url, data) {
  return getInstance().post(url, { ...data }).then(responseBody);
}

async function put(url, data) {
  return getInstance().put(url, { ...data }).then(responseBody);
}

async function del(url) {
  return await getInstance().delete(url).then(responseBody);
}

const api_request = { get, post, put, del };

export default api_request;
