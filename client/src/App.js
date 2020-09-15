import React, { Fragment, useState, useEffect } from "react";
import styled from "@emotion/styled";

import Swal from "sweetalert2";
import ImageUploader from "./components/ImageUploader";
import Uploaded from "./components/Uploaded";
import UploadingBar from "./components/UploadingBar";

function App() {
  const [uploading, setUploading] = useState(false);
  const [uploaded, setUploaded] = useState(false);
  const [error, setError] = useState(false);
  const [file, setFile] = useState(null);

  useEffect(() => {
    if (error) {
      setUploaded(false);
      setUploading(false);
      setError(false);
      Swal.fire({
        icon: "error",
        title: "Error.",
        text: "Ocurrio un error al subir la foto",
        heightAuto: false,
      });
    }
  }, [error]);
  return (
    <Fragment>
      {!uploaded && !uploading && (
        <ImageUploader
          setUploading={setUploading}
          setUploaded={setUploaded}
          setFile={setFile}
          setError={setError}
        />
      )}
      {uploading && <UploadingBar />}
      {uploaded && (
        <Uploaded
          file={file}
          setUploading={setUploading}
          setUploaded={setUploaded}
        />
      )}
      <Footer>
        <span>by: JAEV CODE</span>
        <span>@DevChallenges.io</span>
      </Footer>
    </Fragment>
  );
}

const Footer = styled.footer`
  display: flex;
  justify-content: space-between;
`;
export default App;
