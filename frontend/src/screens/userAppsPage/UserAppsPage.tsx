import React, { FC, useState } from 'react';
import AppsContainer from '../../componenst/appsContainer';
import { useGetUserAppsQuery } from '../../store/api/appsApiSlice/appsApiSlice';

const UserAppsPage: FC = () => {
  const [filter, setFilter] = useState('');
  const [page, setPage] = useState<number>(0);
  const { data } = useGetUserAppsQuery({
    page,
    size: 10,
    filter,
  });
  return <AppsContainer data={data} setFilter={setFilter} page={page} setPage={setPage} />;
};

export default UserAppsPage;
