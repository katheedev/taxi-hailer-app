import React, { useState } from "react";
// import client from 'utils/api-client';
import { Upload, message } from "antd";
import { PlusOutlined, LoadingOutlined } from "@ant-design/icons";
import { LOCAL_STORAGE_KEY_TOKEN } from "../../const/const";


// const { Option } = Select;

let api_url = process.env.REACT_APP_DEV_API_URL;
if (process.env.NODE_ENV === "production") {
  api_url = process.env.REACT_APP_PROD_API_URL;
}

const ImageUpload = (Props) => {
  const [isLoading, setIsLoading] = useState(false);
  const [imageUrl, setImageUrl] = useState(null);
  const getBase64 = (img, callback) => {
    const reader = new FileReader();
    reader.addEventListener("load", () => callback(reader.result));
    reader.readAsDataURL(img);
  };
  const beforeUpload = (file) => {
    const isJpgOrPng = file.type === "image/jpeg" || file.type === "image/png";
    if (!isJpgOrPng) {
      message.error("You can only upload JPG/PNG file!");
    }
    const isLt2M = file.size / 1024 / 1024 < 3;
    if (!isLt2M) {
      message.error("Image must smaller than 2MB!");
    }
    return isJpgOrPng && isLt2M;
  };
  const handleChange = (info) => {
    if (info.file.status === "uploading") {
      setIsLoading(true);
      return;
    }
    if (info.file.status === "done") {
      getBase64(info.file.originFileObj, (imageUrl) => {
        setIsLoading(false);
        setImageUrl(imageUrl);
      });
      Props.handleUpload(info.file);
    }
  };
  return (
    <Upload
      name="photos"
      listType="picture-card"
      showUploadList={false}
      action={`${api_url}/file`}
      beforeUpload={beforeUpload}
      onChange={handleChange}
      headers={{
        authorization: "Bearer " + localStorage.getItem(LOCAL_STORAGE_KEY_TOKEN),
      }}
    >
      {imageUrl ? (
        <img src={imageUrl} alt="avatar" style={{ width: "100%" }} />
      ) : (
        <div>
          {isLoading ? <LoadingOutlined /> : <PlusOutlined />}
          <div className="ant-upload-text">Upload</div>
        </div>
      )}
    </Upload>
  );
};
export default ImageUpload;
