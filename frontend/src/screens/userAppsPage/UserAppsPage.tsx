import React, { FC, useState } from 'react';
import AppsContainer from '../../componenst/appsContainer';
import { useGetUserAppsQuery } from '../../store/api/appsApiSlice/appsApiSlice';

const UserAppsPage: FC = () => {
  const [filter, setFilter] = useState('');
  const { data } = useGetUserAppsQuery({
    page: 0,
    size: 10,
    filter,
  });
  return <AppsContainer data={data} setFilter={setFilter} />;
};

export default UserAppsPage;
