import React from 'react';
import { useParams } from 'react-router-dom';

const AppPage = () => {
  const {id} = useParams()
  console.log(id);
  return <div>AppPage</div>;
};

export default AppPage;
