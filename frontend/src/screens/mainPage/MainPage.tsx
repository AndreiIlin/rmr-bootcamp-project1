import React, { FC, useState } from 'react';
import AppsContainer from '../../componenst/appsContainer';
import { useGetAppsQuery } from '../../store/api/appsApiSlice/appsApiSlice';

const MainPage: FC = () => {
  const [filter, setFilter] = useState('');
  const { data } = useGetAppsQuery({
    page: 0,
    size: 10,
    filter,
  });

  return <AppsContainer data={data} setFilter={setFilter} />;
};

export default MainPage;
