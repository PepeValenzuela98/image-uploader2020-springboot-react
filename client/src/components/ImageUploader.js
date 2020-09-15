import React, { useRef } from "react";
import styled from "@emotion/styled";
import Image from "../image.svg";
import Swal from "sweetalert2";
import axios from "axios";

const ImageUploader = ({ setUploading, setUploaded, setFile, setError }) => {
  const inputFile = useRef(null);

  const preventDefaults = (e) => {
    e.preventDefault();
    e.stopPropagation();
  };

  const uploadFile = async (files) => {
    const file = files[0];
    if (file.type !== "image/jpeg" && file.type !== "image/png") {
      Swal.fire({
        icon: "error",
        title: "Error.",
        text: "Formato no valido",
        heightAuto: false,
      });
    } else {
      setUploading(true);
      try {
        let formData = new FormData();
        formData.append("file", file);
        const response = await axios.post(
          `https://image-uploader-spring-boot2020.herokuapp.com/`,
          formData,
          {
            headers: { "Content-Type": "multipart/form-data" },
          }
        );
        setFile(
          `https://image-uploader-spring-boot2020.herokuapp.com/${response.data}`
        );
        setUploading(false);
        setUploaded(true);
      } catch (error) {
        console.log(error);
        setError(true);
      }
    }
  };
  const handleDrop = (e) => {
    const {
      dataTransfer: { files },
    } = e;
    uploadFile(files);
  };

  const handleChange = (e) => {
    uploadFile(e.target.files);
  };

  const handleClick = () => {
    inputFile.current.click();
  };
  return (
    <ImageUploaderContainer>
      <Title>
        <h1>Upload your image</h1>
        <span>File should be Jpeg, Png,...</span>
      </Title>

      <DragDropFile
        onDragEnter={(e) => {
          preventDefaults(e);
        }}
        onDragOver={(e) => {
          preventDefaults(e);
        }}
        onDragLeave={(e) => {
          preventDefaults(e);
        }}
        onDrop={(e) => {
          preventDefaults(e);
          handleDrop(e);
        }}
        onClick={handleClick}
      >
        <img src={Image} alt="imgupload" />
        <span>Drag & Drop your image here</span>
      </DragDropFile>
      <ButtonFile>
        <span>Or</span>
        <label htmlFor="fileButton">Choose a file</label>
        <input
          type="file"
          id="fileButton"
          accept=".jpg,.png"
          onChange={handleChange}
          ref={inputFile}
        />
      </ButtonFile>
    </ImageUploaderContainer>
  );
};

const ImageUploaderContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  height: 500px;
  background-color: #fafafb;
  box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
  border-radius: 12px;
  padding: 70px;
`;

const Title = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  h1 {
    font-size: 28px;
    color: #4f4f4f;
    font-weight: 500;
    margin-bottom: 10px;
  }
  span {
    font-size: 16px;
    color: #828282;
    font-weight: 500;
  }
`;

const DragDropFile = styled.div`
  flex: 1;
  margin: 20px 0;
  border: 1px dashed #97bef4;
  border-radius: 12px;
  background-color: #f6f8fb;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px 100px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-evenly;
  cursor: pointer;
  &:hover {
    background-color: #f2f4f9;
  }

  img {
    width: 150px;
    -webkit-user-drag: none;
  }

  span {
    font-weight: 500;
    font-size: 16px;
    color: #bdbdbd;
  }
`;

const ButtonFile = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 10px;
  span {
    font-size: 18px;
    color: #bdbdbd;
    font-weight: 500;
    margin-bottom: 10px;
  }
  label {
    padding: 12px;
    background-color: #2f80ed;
    border-radius: 8px;
    color: white;
    font-family: "Noto Sans", sans-serif;
    cursor: pointer;
    &:hover {
      background-color: #3c87ee;
    }
  }
  input {
    display: none;
  }
`;

export default ImageUploader;
